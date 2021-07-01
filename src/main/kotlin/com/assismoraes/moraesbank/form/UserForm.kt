package com.assismoraes.moraesbank.form

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotBlank

class UserForm {

    @field:NotBlank
    val name: String = ""

}