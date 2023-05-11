package com.example.demo.dto

import java.math.BigDecimal
import java.time.LocalDate
import com.example.demo.entity.Credit
import com.example.demo.entity.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class CreditDto(
    @field:NotNull(message = "Invalid Input, null") val creditValue: BigDecimal,
    @field:Future(message = "Date must be a present or future date") val dayFirstInstallment: LocalDate,
    @field:NotNull(message = "Invalid Input, null")
    @field:Min(1) @field:Max(48) val numberOfInstallments: Int,
    @field:NotNull(message = "Invalid Input, null") val customerId: Long
) {

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id= this.customerId)
    )
}
