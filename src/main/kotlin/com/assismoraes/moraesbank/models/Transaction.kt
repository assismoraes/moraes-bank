package com.assismoraes.moraesbank.models

import com.assismoraes.moraesbank.enums.TransactionType
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotNull
    var value: Long,

    @ManyToOne
    var debitAccount: Account?,

    @ManyToOne
    var creditAccount: Account?,

    @ManyToOne
    var relatedAccount: Account?,

    @Enumerated(EnumType.STRING)
    var type: TransactionType,

    var date: Date


) {
    var bankCode: String = ""
    var centralBankCode: String = ""

    init {
        bankCode = UUID.randomUUID().toString()
    }
}