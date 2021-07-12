package com.assismoraes.moraesbank.config

import com.assismoraes.moraesbank.dto.DataDto
import com.assismoraes.moraesbank.dto.TransactionFromBankRabbitMessageDto
import com.assismoraes.moraesbank.dto.TransactionFromCBRabbitMessageDto
import com.assismoraes.moraesbank.services.AccountService
import org.slf4j.LoggerFactory
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitConsumer(
    private var accountService: AccountService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["transactions-from-cb"])
    fun receiveTransactionMessage(message: TransactionFromCBRabbitMessageDto) {
        accountService.updateCbCode(message.bankCode, message.centralBankCode)
    }

    @RabbitListener(queues = ["mb-messages-queue"])
    fun receiveDataMessage(data: DataDto) {

        log.info("LOG :: " + data)

        if(data.message == null) {
            throw AmqpRejectAndDontRequeueException("Erro!")
        }
    }

}