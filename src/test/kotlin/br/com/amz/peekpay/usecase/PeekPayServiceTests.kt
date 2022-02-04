package br.com.amz.peekpay.usecase

import br.com.amz.peekpay.persistence.customer.CustomerDBO
import br.com.amz.peekpay.persistence.order.OrderDBO
import br.com.amz.peekpay.persistence.payment.PaymentDBO
import br.com.amz.peekpay.usecase.customer.CustomerService
import br.com.amz.peekpay.usecase.order.OrderService
import br.com.amz.peekpay.usecase.payment.PaymentService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@ExtendWith(MockKExtension::class)
@SpringBootTest
class PeekPayServiceTests {

	@InjectMockKs private lateinit var service: PeekPayService
	@MockK private lateinit var orderService: OrderService
	@MockK private lateinit var paymentService: PaymentService
	@MockK private lateinit var customerService: CustomerService

	@Test
	fun `should create an order then a payment for this order`() {
		val order = OrderDBO(
			id = UUID.randomUUID(),
			customer = CustomerDBO(
				id = UUID.randomUUID(),
				email = "john@doe.co"
			),
			originalValue = 1000.toBigDecimal(),
			balance = 200.toBigDecimal(),
			payments = emptyList()
		)

		val payment = PaymentDBO(amount = 100.toBigDecimal(), order = order)

		every { orderService.createOrder(any()) } returns order
		every { paymentService.createPayment(any()) } returns payment
		every { orderService.getOrder(any()) } returns order.copy(payments = listOf(payment))

		service.createOrderAndPay(order, 100.toBigDecimal())

		val paymentSlot = slot<PaymentDBO>()

		verify(exactly = 1) { orderService.createOrder(any()) }
		verify(exactly = 1) { paymentService.createPayment(capture(paymentSlot)) }

		with(paymentSlot.captured) {
			assert(this.order.originalValue == order.originalValue)
			assert(this.amount == payment.amount)
		}
	}
}
