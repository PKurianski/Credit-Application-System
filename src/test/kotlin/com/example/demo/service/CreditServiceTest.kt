package com.example.demo.service


import com.example.demo.entity.Credit
import com.example.demo.entity.Customer
import com.example.demo.exception.BusinessException
import com.example.demo.repository.CreditRepository
import com.example.demo.service.impl.CreditService
import com.example.demo.service.impl.CustomerService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
class CreditServiceTest {

    @MockK
    lateinit var creditRepository: CreditRepository

    @MockK
    lateinit var customerService: CustomerService

    @InjectMockKs
    lateinit var creditService: CreditService

    @Test
    fun shouldSaveCredit() {
        // given
        val fakeCredit: Credit = buildCredit()
        val fakeCustomerId: Long = 1L
        val fakeCustomer: Customer = buildCustomer(id = fakeCustomerId)
        every { customerService.findById(fakeCustomerId) } returns fakeCustomer
        every { creditRepository.save(any()) } returns fakeCredit

        // when
        val actual: Credit = creditService.save(fakeCredit) //TODO: Solve NullPointerException

        // then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)
        verify(exactly = 1) { customerService.findById(fakeCustomerId) }
        verify(exactly = 1) { creditRepository.save(fakeCredit) }
    }

    @Test
    fun shouldFindAllCreditsByCustomer() {
        // given
        val fakeCustomerId: Long = 1L
        val fakeCredits: List<Credit> = listOf(buildCredit(id = 1L), buildCredit(id = 2L))
        every { creditRepository.findAllByCustomerId(fakeCustomerId) } returns fakeCredits

        // when
        val actual: List<Credit> = creditService.findAllByCustomer(fakeCustomerId)

        // then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).hasSize(fakeCredits.size)
        Assertions.assertThat(actual).containsAll(fakeCredits)
        verify(exactly = 1) { creditRepository.findAllByCustomerId(fakeCustomerId) }
    }

    @Test
    fun shouldFindByCreditCodeAndCustomerId() {
        // given
        val fakeCustomerId: Long = 1L
        val fakeCreditCode: UUID = UUID.randomUUID()
        val fakeCredit: Credit = buildCredit(id = 1L, customer = buildCustomer(id = fakeCustomerId))
        every { creditRepository.findByCreditCode(fakeCreditCode) } returns fakeCredit

        // when
        val actual: Credit = creditService.findByCreditCode(fakeCustomerId, fakeCreditCode)

        // then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)
        verify(exactly = 1) { creditRepository.findByCreditCode(fakeCreditCode) }
    }

    @Test
    fun `should not find credit by invalid customerId and throw IllegalArgumentException`() {
        // given
        val fakeCustomerId: Long = 1L
        val fakeCreditCode: UUID = UUID.randomUUID()
        val fakeCredit: Credit = buildCredit(id = 1L, customer = buildCustomer(id = fakeCustomerId + 1))
        every { creditRepository.findByCreditCode(fakeCreditCode) } returns fakeCredit

        // when
        // then
        Assertions.assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy { creditService.findByCreditCode(fakeCustomerId, fakeCreditCode) }
            .withMessage("Contact admin")

        verify(exactly = 1) { creditRepository.findByCreditCode(fakeCreditCode) }
    }

    @Test
    fun `should not find credit by invalid creditCode and throw BusinessException`() {
        // given
        val fakeCustomerId: Long = 1L
        val fakeCreditCode: UUID = UUID.randomUUID()
        every { creditRepository.findByCreditCode(fakeCreditCode) } returns null

        // when
        // then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { creditService.findByCreditCode(fakeCustomerId, fakeCreditCode) }
            .withMessage("Creditcode $fakeCreditCode not found")

        verify(exactly = 1) { creditRepository.findByCreditCode(fakeCreditCode) }
    }

    private fun buildCredit(
        id: Long = 1L,
        customer: Customer? = null,
        dayFirstInstallment: LocalDate = LocalDate.now().minusMonths(2)
    ) = Credit(
        id = id,
        customer = customer,
        dayFirstInstallment = dayFirstInstallment
    )

    private fun buildCustomer(
        id: Long
    ) = Customer(id = id)
}
