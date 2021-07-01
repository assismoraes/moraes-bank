package com.assismoraes.moraesbank.services

import com.assismoraes.moraesbank.dto.CurrentBalanceDto
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
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AccountService(
    private val repository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun deposit(form: DepositForm) {
        var optAccount = repository.findByBranchAndNumber(form.accountBranch, form.accountNumber)
        if(!optAccount.isPresent) throw InvalidAccountException()

        var account = optAccount.get()
        account.currentBalance += form.value
        repository.save(account)

        var transaction = Transaction(value = form.value, creditAccount = account, type = TransactionType.DEPOSIT, debitAccount = null)
        transactionRepository.save(transaction)

    }

    @Transactional
    fun withdraw(userId: Long, form: WithdrawForm) {
        var optUser = userRepository.findById(userId)
        // TODO: 30/06/2021 validate if user really exists

        var account: Account = optUser.get().account!!

        if(account.currentBalance < form.value) throw InsufficientFundsException()

        account.currentBalance -= form.value
        repository.save(account)

        var transaction = Transaction(value = form.value*(-1), creditAccount = null, type = TransactionType.WITHDRAW, debitAccount = account)
        transactionRepository.save(transaction)

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
        var debitTransaction = Transaction(value = form.value*(-1), creditAccount = creditAccount, type = TransactionType.TRANSFER, debitAccount = debitAccount)
        transactionRepository.save(debitTransaction)

        var creditTransaction = Transaction(value = form.value, creditAccount = creditAccount, type = TransactionType.TRANSFER, debitAccount = debitAccount)
        transactionRepository.save(creditTransaction)

    }

    fun currentBalance(userId: Long): CurrentBalanceDto {
        var optUser = userRepository.findById(userId)
        var account: Account = optUser.get().account!!

        return CurrentBalanceDto(account.currentBalance)
    }

}