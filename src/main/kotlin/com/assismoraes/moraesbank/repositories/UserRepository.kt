package com.assismoraes.moraesbank.repositories

import com.assismoraes.moraesbank.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
}