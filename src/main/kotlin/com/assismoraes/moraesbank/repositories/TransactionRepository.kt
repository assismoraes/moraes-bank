package com.assismoraes.moraesbank.repositories

import com.assismoraes.moraesbank.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findByBankCode(code: String): Optional<Transaction>

}