package com.example.demo.dto


import com.example.demo.entity.Address
import com.example.demo.entity.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
    @field:NotEmpty(message = "Invalid Input, empty or null") val firstName: String,
    @field:NotEmpty(message = "Invalid Input, empty or null") val lastName: String,
    @field:NotEmpty(message = "Invalid Input, empty or null")
    @field:CPF(message = "This CPF is invalid") val cpf: String,
    @field:NotNull(message = "Invalid Input, null") val income: BigDecimal,
    @field:NotEmpty(message = "Invalid Input, empty or null")
    @field:Email(message = "Please insert a valid email") val email: String,
    @field:NotEmpty(message = "Invalid Input, empty or null") val password: String,
    @field:NotEmpty(message = "Invalid Input, empty or null") val zipCode: String,
    @field:NotEmpty(message = "Invalid Input, empty or null") val street: String
) {
    fun toEntity(): Customer = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        income = this.income,
        email = this.email,
        password = this.password,
        address = Address(
            zipCode = this.zipCode,
            street = this.street
        )
    )
}