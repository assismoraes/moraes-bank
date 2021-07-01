package com.assismoraes.moraesbank.models

import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotEmpty
    var name: String

) {

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @NotNull
    var account: Account? = null

}