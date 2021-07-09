package com.assismoraes.moraesbank.dto

import com.assismoraes.moraesbank.enums.TransactionType
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.util.*

data class TransactionFromBankRabbitMessageDto(
    @JsonProperty("bankCode") var bankCode: String,
    @JsonProperty("accountNumber") var accountNumber: String,
    @JsonProperty("accountBranch") var accountBranch: String,
    @JsonProperty("type") var type: TransactionType,
    @JsonProperty("value") var value: Long,

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var date: Date = Date()

) : Serializable {

}