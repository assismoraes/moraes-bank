package com.assismoraes.moraesbank.form

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class DepositForm {

    @field:Min(1)
    var value: Long = 0L

    @field:NotBlank
    var accountBranch: String = ""

    @field:NotBlank
    var accountNumber: String = ""

}