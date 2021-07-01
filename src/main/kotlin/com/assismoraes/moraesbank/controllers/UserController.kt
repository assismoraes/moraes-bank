package com.assismoraes.moraesbank.controllers

import com.assismoraes.moraesbank.dto.UserDto
import com.assismoraes.moraesbank.form.UserForm
import com.assismoraes.moraesbank.models.User
import com.assismoraes.moraesbank.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
data class UserController(private var service: UserService) {

    @GetMapping
    fun list(): List<User> {
        return service.list()
    }

    @PostMapping
    fun create(@RequestBody @Validated userForm: UserForm): ResponseEntity<Any> {
        return ResponseEntity.ok(service.save(userForm))
    }

}