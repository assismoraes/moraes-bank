package com.assismoraes.moraesbank.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class RabbitConfiguration(private val connectionFactory: ConnectionFactory) {
    @Bean
    fun simpleRabbitListenerContainerFactory(): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(jacksonConverter())
        factory.setConcurrentConsumers(2)
        factory.setMaxConcurrentConsumers(5)
        return factory
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jacksonConverter()
        return rabbitTemplate
    }

    @Bean
    fun jacksonConverter(): Jackson2JsonMessageConverter {
        val mapper: ObjectMapper = Jackson2ObjectMapperBuilder.json()
            .modules(JavaTimeModule())
            .dateFormat(StdDateFormat())
            .build()
        return Jackson2JsonMessageConverter(mapper)
    }
}