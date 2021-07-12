package com.assismoraes.moraesbank.controllers

import com.assismoraes.moraesbank.form.DataForm
import com.assismoraes.moraesbank.services.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("messages")
class MessageController(
    private val service: MessageService
) {

    @PostMapping
    fun send(@RequestBody data: DataForm): ResponseEntity<Any> {
        service.send(data)
        return ResponseEntity.ok(data)
    }

}