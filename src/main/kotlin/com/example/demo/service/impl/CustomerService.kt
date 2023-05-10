package com.example.demo.service.impl

import com.example.demo.entity.Customer
import com.example.demo.repository.CustomerRepository
import com.example.demo.service.ICustomerService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) : ICustomerService {
    override fun save(customer: Customer): Customer {
        return this.customerRepository.save(customer)
    }

    override fun findById(id: Long): Customer {
        return this.customerRepository.findById(id).orElseThrow { throw RuntimeException("Id $id not found") }
    }

    override fun delete(id: Long) {
        return this.customerRepository.deleteById(id)
    }
