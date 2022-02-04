package br.com.amz.peekpay.usecase.order

import br.com.amz.peekpay.persistence.customer.Customer
import br.com.amz.peekpay.persistence.order.Order
import br.com.amz.peekpay.persistence.order.OrderRepository
import equalsTo
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.util.Optional

@ExtendWith(MockKExtension::class)
@SpringBootTest
class OrderServiceTest {

    @InjectMockKs private lateinit var service: OrderService
    @MockK private lateinit var repository: OrderRepository

    @Test
    fun `should compensate order's balance when a payment is created for that`() {
        val paymentAmount = 10.toBigDecimal()

        val order = Order(
            customer = Customer(email = "test@test.co"),
            originalValue = 50.toBigDecimal()
        )

        every { repository.findById(any()) } returns Optional.of(order)
        every { repository.save(any()) } returns order

        service.decreaseBalanceWithPayment(paymentAmount, order.id)

        assert(order.originalValue equalsTo 50.toBigDecimal())
        assert(order.balance!! equalsTo 40.toBigDecimal())

        service.decreaseBalanceWithPayment(paymentAmount, order.id)

        assert(order.balance!! equalsTo 30.toBigDecimal())
    }
}
