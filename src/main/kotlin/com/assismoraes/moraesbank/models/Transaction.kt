package com.assismoraes.moraesbank.models

import com.assismoraes.moraesbank.enums.TransactionType
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

    @Enumerated(EnumType.STRING)
    var type: TransactionType


) {
}