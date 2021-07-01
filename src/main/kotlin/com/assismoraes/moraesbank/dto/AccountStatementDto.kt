package com.assismoraes.moraesbank.dto

import com.assismoraes.moraesbank.models.Account

class AccountStatementDto{

    var transactions: MutableList<TransactionDto> = mutableListOf()

    fun convert(account: Account): AccountStatementDto {
        account.transactions.forEach { it ->
            this.transactions.add(TransactionDto.convert(it))
        }

        return this
    }

}