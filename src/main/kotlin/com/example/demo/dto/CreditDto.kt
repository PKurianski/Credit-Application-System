package com.example.demo.dto

import java.math.BigDecimal
import java.time.LocalDate
import com.example.demo.entity.Credit
import com.example.demo.entity.Customer

data class CreditDto(
    val creditValue: BigDecimal,
    val dayFirstInstallment: LocalDate,
    val numberOfInstallments: Int,
    val customerId: Long
) {

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id= this.customerId)
    )
}
