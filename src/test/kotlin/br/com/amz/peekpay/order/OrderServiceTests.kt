package br.com.amz.peekpay.order

import br.com.amz.peekpay.createDefaultOrder
import br.com.amz.peekpay.payment.PaymentService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@ExtendWith(MockKExtension::class)
@SpringBootTest
class OrderServiceTests {

	@InjectMockKs private lateinit var service: OrderService
	@MockK private lateinit var repository: OrderRepository
	@MockK private lateinit var paymentService: PaymentService

	@Test
	fun `should create an order then a payment for this order`() {
		val order = createDefaultOrder()

		every { repository.create(any()) } returns order.orderId!!
		every { paymentService.createPayment(any(), any()) } returns UUID.randomUUID()

		service.createOrderAndPay(order, 10.toBigDecimal())

		verify(exactly = 1) { service.createOrder(any()) }
		verify(exactly = 1) {
			paymentService.createPayment(
				eq(10.toBigDecimal()),
				eq(order.orderId!!)
			)
		}
	}
}
