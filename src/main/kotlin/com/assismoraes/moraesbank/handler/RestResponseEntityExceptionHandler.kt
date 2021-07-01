package com.assismoraes.moraesbank.handler

import com.assismoraes.moraesbank.dto.MessageDto
import com.assismoraes.moraesbank.dto.ValidationErrorMessageDto
import com.assismoraes.moraesbank.exceptions.InvalidAccountException
import com.assismoraes.moraesbank.exceptions.InsufficientFundsException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        var errorMessages = ex.fieldErrors.map {
            ValidationErrorMessageDto(it.field, it.defaultMessage)
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessages)
    }

    @ExceptionHandler(InvalidAccountException::class)
    fun handleInvalidAccountException(ex: InvalidAccountException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageDto(ex.message))
    }

    @ExceptionHandler(InsufficientFundsException::class)
    fun handleInvalidAccountException(ex: InsufficientFundsException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MessageDto(ex.message))
    }

}