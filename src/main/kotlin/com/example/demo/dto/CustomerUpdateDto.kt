package com.example.demo.dto

import com.example.demo.entity.Customer
import java.math.BigDecimal

data class CustomerUpdateDto(
    val firstName : String,
    val lastName: String,
    val income: BigDecimal,
    val zipCode: String,
    val street: String
) {
    fun toEntity(customer: Customer):Customer{
        customer.firstName = this.firstName
        customer.lastName = this.lastName
        customer.income = this.income
        customer.address.street = this.street
        customer.address.zipCode = this.zipCode
        return customer
    }
}