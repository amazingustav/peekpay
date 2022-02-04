package br.com.amz.peekpay.usecase

import br.com.amz.peekpay.persistence.customer.Customer
import br.com.amz.peekpay.persistence.order.Order
import br.com.amz.peekpay.persistence.payment.Payment
import br.com.amz.peekpay.usecase.customer.CustomerService
import br.com.amz.peekpay.usecase.order.OrderService
import br.com.amz.peekpay.usecase.payment.ApplicablePayment
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
class PeekPayServiceTest {

	@InjectMockKs private lateinit var service: PeekPayService
	@MockK private lateinit var orderService: OrderService
	@MockK private lateinit var paymentService: PaymentService
	@MockK private lateinit var customerService: CustomerService

	private val order = Order(
		customer = Customer(
			email = "john@doe.co"
		),
		originalValue = 1000.toBigDecimal(),
		balance = 200.toBigDecimal()
	)

	private val payment = Payment(amount = 100.toBigDecimal(), order = order)

	private val orderWithPayments = order.copy(payments = listOf(payment))

	@Test
	fun `should create an order then a payment for this order`() {
		every { orderService.createOrder(any()) } returns order
		every { paymentService.createPayment(any()) } returns payment
		every { orderService.getOrder(any()) } returns orderWithPayments

		service.createOrderAndPay(order, 100.toBigDecimal())

		val paymentSlot = slot<Payment>()

		verify(exactly = 1) { orderService.createOrder(any()) }
		verify(exactly = 1) { paymentService.createPayment(capture(paymentSlot)) }

		with(paymentSlot.captured) {
			assert(this.order.originalValue == order.originalValue)
			assert(this.amount == payment.amount)
		}
	}

	@Test
	fun `should trigger apply payment to an order twice but processing just once`() {
		val applicablePayment = ApplicablePayment(
			amount = 10.toBigDecimal(),
			orderId = UUID.randomUUID(),
			idempotencyKey = UUID.randomUUID()
		)

		every { paymentService.createPayment(any()) } returns payment
		every { paymentService.isPaymentLocked(any()) } returns false andThen true
		every { orderService.getOrder(any()) } returns orderWithPayments

		val threads = (1..2).map {
			Thread {
				Thread.sleep(200L)
				service.applyPaymentToOrder(applicablePayment)
			}.apply { start() }
		}

		threads.forEach { it.join() }

		verify(exactly = 1) { paymentService.createPayment(any()) }
	}
}
