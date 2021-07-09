package com.assismoraes.moraesbank.services

import com.assismoraes.moraesbank.dto.AccountStatementDto
import com.assismoraes.moraesbank.dto.CurrentBalanceDto
import com.assismoraes.moraesbank.dto.TransactionFromBankRabbitMessageDto
import com.assismoraes.moraesbank.enums.TransactionType
import com.assismoraes.moraesbank.exceptions.InvalidAccountException
import com.assismoraes.moraesbank.exceptions.InsufficientFundsException
import com.assismoraes.moraesbank.form.DepositForm
import com.assismoraes.moraesbank.form.TransferForm
import com.assismoraes.moraesbank.form.WithdrawForm
import com.assismoraes.moraesbank.models.Account
import com.assismoraes.moraesbank.models.Transaction
import com.assismoraes.moraesbank.repositories.AccountRepository
import com.assismoraes.moraesbank.repositories.TransactionRepository
import com.assismoraes.moraesbank.repositories.UserRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class AccountService(
    private val repository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val rabbitTemplate: RabbitTemplate
) {
    @Transactional
    fun deposit(form: DepositForm) {
        var optAccount = repository.findByBranchAndNumber(form.accountBranch, form.accountNumber)
        if(!optAccount.isPresent) throw InvalidAccountException()

        var account = optAccount.get()
        account.currentBalance += form.value
        repository.save(account)

        var transaction = Transaction(value = form.value, creditAccount = account, type = TransactionType.DEPOSIT, debitAccount = null, relatedAccount = account, date = Date())
        transactionRepository.save(transaction)

        var message = TransactionFromBankRabbitMessageDto(
            bankCode = transaction.bankCode,
            accountNumber = account.number,
            accountBranch = account.branch,
            type = TransactionType.DEPOSIT,
            value = transaction.value,
            date = Date()
        )

        rabbitTemplate.convertAndSend("bank.transaction-to-cb", "rq-transactions-to-cb", message)
    }

    @Transactional
    fun withdraw(userId: Long, form: WithdrawForm) {
        var optUser = userRepository.findById(userId)
        // TODO: 30/06/2021 validate if user really exists

        var account: Account = optUser.get().account!!

        if(account.currentBalance < form.value) throw InsufficientFundsException()

        account.currentBalance -= form.value
        repository.save(account)

        var transaction = Transaction(value = form.value*(-1), creditAccount = null, type = TransactionType.WITHDRAW, debitAccount = account, relatedAccount = account, date = Date())
        transactionRepository.save(transaction)

        var message = TransactionFromBankRabbitMessageDto(
            bankCode = transaction.bankCode,
            accountNumber = account.number,
            accountBranch = account.branch,
            type = TransactionType.WITHDRAW,
            value = transaction.value,
            date = Date()
        )

        rabbitTemplate.convertAndSend("bank.transaction-to-cb", "rq-transactions-to-cb", message)

    }

    @Transactional(rollbackOn = arrayOf(InvalidAccountException::class))
    fun transfer(userId: Long, form: TransferForm) {
        var optUser = userRepository.findById(userId)
        // TODO: 30/06/2021 validate if user really exists

        // DEBIT ON SOURCE ACCOUNT
        var debitAccount: Account = optUser.get().account!!
        if(debitAccount.currentBalance < form.value) throw InsufficientFundsException()
        debitAccount.currentBalance -= form.value
        repository.save(debitAccount)

        // CREDIT ON DESTINY ACCOUNT
        var optCreditAccount = repository.findByBranchAndNumber(form.accountBranch, form.accountNumber)
        if(!optCreditAccount.isPresent || optCreditAccount.get().id == debitAccount.id) throw InvalidAccountException()

        var creditAccount = optCreditAccount.get()
        creditAccount.currentBalance += form.value
        repository.save(creditAccount)

        // SAVING TRANSACTIONS
        var debitTransaction = Transaction(value = form.value*(-1), creditAccount = creditAccount, type = TransactionType.TRANSFER, debitAccount = debitAccount, relatedAccount = debitAccount, date = Date())
        transactionRepository.save(debitTransaction)

        var creditTransaction = Transaction(value = form.value, creditAccount = creditAccount, type = TransactionType.TRANSFER, debitAccount = debitAccount, relatedAccount = creditAccount, date = Date())
        transactionRepository.save(creditTransaction)

    }

    fun currentBalance(userId: Long): CurrentBalanceDto {
        var optUser = userRepository.findById(userId)
        var account: Account = optUser.get().account!!

        return CurrentBalanceDto(account.currentBalance)
    }

    fun accountStatement(userId: Long): AccountStatementDto {
        var optUser = userRepository.findById(userId)
        var account: Account = optUser.get().account!!

        return AccountStatementDto().convert(account)
    }

    fun updateCbCode(bankCode: String, centralBankCode: String) {
        var optTransaction = transactionRepository.findByBankCode(bankCode)
        if(optTransaction.isPresent) {
            var transaction = optTransaction.get()
            transaction.centralBankCode = centralBankCode
            transactionRepository.save(transaction)
        }
    }

}