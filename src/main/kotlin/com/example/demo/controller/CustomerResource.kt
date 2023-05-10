package com.example.demo.controller

import com.example.demo.dto.CustomerDto
import com.example.demo.service.impl.CustomerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerResource(private val customerService: CustomerService) {
    @PostMapping
    fun saveCustomer(@RequestBody customerDto: CustomerDto): String{

    }
}