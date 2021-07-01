package com.assismoraes.moraesbank.controllers

import com.assismoraes.moraesbank.services.AccountService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("accounts")
class AccountController(
    private val service: AccountService
) {

    @GetMapping("mine/current-balance")
    fun currentBalance(@RequestHeader headers: HttpHeaders): ResponseEntity<Any> {
        var userId = headers.getValuesAsList("user_id").first().toString().toLong()
        var currentBalanceDto = service.currentBalance(userId)
        return ResponseEntity.ok(currentBalanceDto)
    }

}