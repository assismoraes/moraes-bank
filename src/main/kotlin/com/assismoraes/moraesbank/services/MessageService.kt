package com.assismoraes.moraesbank.services

import com.assismoraes.moraesbank.form.DataForm
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val rabbitTemplate: RabbitTemplate
) {

    fun send(data: DataForm) {
        rabbitTemplate.convertAndSend("mb.messages-exchange", "mb-messages-routing-key", data)
    }
}