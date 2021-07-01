package com.assismoraes.moraesbank.controllers

import com.assismoraes.moraesbank.dto.MessageDto
import com.assismoraes.moraesbank.exceptions.InvalidAccountException
import com.assismoraes.moraesbank.form.DepositForm
import com.assismoraes.moraesbank.form.TransferForm
import com.assismoraes.moraesbank.form.WithdrawForm
import com.assismoraes.moraesbank.services.AccountService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("transactions")
class TransactionController(
    private val accountService: AccountService
) {

    @PostMapping("deposit")
    fun deposit(@RequestHeader headers: HttpHeaders, @RequestBody @Validated form: DepositForm): ResponseEntity<Any> {
        accountService.deposit(form)
        return ResponseEntity.ok(MessageDto("Successful deposit"));
    }

    @PostMapping("withdraw")
    fun withdraw(@RequestHeader headers: HttpHeaders, @RequestBody @Validated form: WithdrawForm): ResponseEntity<Any> {
        var userId = headers.getValuesAsList("user_id").first().toString().toLong()
        accountService.withdraw(userId, form)
        return ResponseEntity.ok(MessageDto("Successful withdraw"));
    }

    @PostMapping("transfer")
    fun transfer(@RequestHeader headers: HttpHeaders, @RequestBody @Validated form: TransferForm): ResponseEntity<Any> {
        var userId = headers.getValuesAsList("user_id").first().toString().toLong()
        accountService.transfer(userId, form)
        return ResponseEntity.ok(MessageDto("Successful transfer"));
    }

}