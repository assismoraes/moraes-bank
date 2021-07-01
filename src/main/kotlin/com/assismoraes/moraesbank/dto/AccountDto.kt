package com.assismoraes.moraesbank.dto

import com.assismoraes.moraesbank.models.Account
import com.assismoraes.moraesbank.models.User

class AccountDto(
    var id: Long?,
    var branch: String,
    var number: String,
) {
    companion object {
        fun convert(account: Account?): AccountDto? {
            return if(account != null) {
                AccountDto(account.id, account.branch, account.number)
            } else {
                null
            }
        }
    }
}