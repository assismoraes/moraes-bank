package com.assismoraes.moraesbank.form

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class WithdrawForm {

    @field:Min(1)
    var value: Long = 0L

}