package com.assismoraes.moraesbank.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class TransactionFromCBRabbitMessageDto(
    @JsonProperty("centralBankCode") var centralBankCode: String,
    @JsonProperty("bankCode") var bankCode: String
) : Serializable {

}