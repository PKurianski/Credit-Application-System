package com.example.demo.repository

import com.example.demo.entity.Address
import com.example.demo.entity.Credit
import com.example.demo.entity.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
    @Autowired
    lateinit var creditRepository: CreditRepository
    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit01: Credit
    private lateinit var credit02: Credit

    @BeforeEach fun setup(){
        customer = testEntityManager.persist(buildCustomer())
        credit01 = testEntityManager.persist(buildCredit(customer = customer))
        credit02 = testEntityManager.persist(buildCredit(customer = customer))
    }

    @Test
    fun shouldFindCreditByCreditCode(){
        //given
        val creditCode01 = UUID.fromString("aa547c0f-9a6a-451f-8c89-afddce916a29")
        val creditCode02 = UUID.fromString("49f740be-46a7-449b-84e7-ff5b7986d7ef")
        credit01.creditCode = creditCode01
        credit02.creditCode = creditCode02

        //when
        val fakeCredit01:Credit = creditRepository.findByCreditCode(creditCode01)!!
        val fakeCredit02:Credit = creditRepository.findByCreditCode(creditCode02)!!

        //then
        Assertions.assertThat(fakeCredit01).isNotNull
        Assertions.assertThat(fakeCredit02).isNotNull
        Assertions.assertThat(fakeCredit01).isSameAs(credit01)
        Assertions.assertThat(fakeCredit02).isSameAs(credit02)
        Assertions.assertThat(credit01.customer).isSameAs(credit02.customer)
    }

    @Test
    fun shouldFindAllCreditsByCustomerId(){
        //given
        val customerId:Long = 1L

        //when
        val creditList:List<Credit> = creditRepository.findAllByCustomerId(customerId)

        //then
        Assertions.assertThat(creditList).isNotEmpty
        Assertions.assertThat(creditList.size).isEqualTo(2)
        Assertions.assertThat(creditList).contains(credit01,credit02)
        Assertions.assertThat(customer.id).isEqualTo(1)
    }

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.APRIL, 22),
        numberOfInstallments: Int = 5,
        customer: Customer
    ): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )
    private fun buildCustomer(
        firstName: String = "Pedro",
        lastName: String = "Kurianski",
        cpf: String = "28475934625",
        email: String = "kuri@gmail.com",
        password: String = "159564",
        zipCode: String = "123586",
        street: String = "Rua do Pedro",
        income: BigDecimal = BigDecimal.valueOf(1200.0),
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street,
        ),
        income = income,
    )
}