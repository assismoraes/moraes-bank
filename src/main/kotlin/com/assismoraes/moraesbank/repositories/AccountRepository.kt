package com.assismoraes.moraesbank.repositories

import com.assismoraes.moraesbank.models.Account
import com.assismoraes.moraesbank.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, Long> {

    fun findByBranchAndNumber(branch: String, number: String): Optional<Account>

    fun findByOwner(user: User): Optional<Account>

}