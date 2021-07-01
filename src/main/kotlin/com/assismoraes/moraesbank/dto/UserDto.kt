package com.assismoraes.moraesbank.dto

import com.assismoraes.moraesbank.models.User

class UserDto(
    var id: Long? = null,

    var name: String,

    var account: AccountDto?

) {

    companion object {
        fun convert(user: User): UserDto {
            return UserDto(id = user?.id, name = user?.name, AccountDto.convert(user.account))
        }
    }

}