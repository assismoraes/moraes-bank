package com.assismoraes.moraesbank.dto

import com.assismoraes.moraesbank.enums.TransactionType
import com.assismoraes.moraesbank.models.Transaction
import java.util.*

class TransactionDto(
    var id: Long,

    var value: Long,

    var debitAccount: AccountDto?,

    var creditAccount: AccountDto?,

    var type: TransactionType,

    var date: Date
) {
    companion object {
        fun convert(transaction: Transaction): TransactionDto {
            return TransactionDto(
                id = transaction.id!!,
                value = transaction.value,
                debitAccount = AccountDto.convert(transaction.debitAccount),
                creditAccount = AccountDto.convert(transaction.creditAccount),
                type = transaction.type,
                date = transaction.date
                )
        }
    }
}