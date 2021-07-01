package com.assismoraes.moraesbank.services

import com.assismoraes.moraesbank.dto.UserDto
import com.assismoraes.moraesbank.form.UserForm
import com.assismoraes.moraesbank.models.Account
import com.assismoraes.moraesbank.models.User
import com.assismoraes.moraesbank.repositories.AccountRepository
import com.assismoraes.moraesbank.repositories.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService(
    private var repository: UserRepository,
    private var accountRepository: AccountRepository,
    ) {

    fun list(): List<User> {
        return repository.findAll()
    }

    @Transactional
    fun save(userForm: UserForm): UserDto {
        var user = User(name = userForm.name)
        var account = Account()
        accountRepository.save(account)

        user.account = account
        user = repository.save(user)

        return UserDto.convert(user)
    }

}