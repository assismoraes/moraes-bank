package com.assismoraes.moraesbank.models

import org.hibernate.annotations.ColumnDefault
import java.math.BigDecimal
import javax.persistence.*
import kotlin.random.Random

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {

    var branch: String = "00001"
    var number: String

    @ColumnDefault("0")
    var currentBalance: Long = 0L

    @OneToOne(mappedBy = "account")
    var owner: User? = null

    init {
        number = generateNumber()
    }

    private fun generateNumber(): String {
        return Random.nextLong(1000000).toString()
    }

}