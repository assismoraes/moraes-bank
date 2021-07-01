package com.assismoraes.moraesbank.repositories

import com.assismoraes.moraesbank.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long> {
}