package br.com.amz.peekpay.integration

import br.com.amz.peekpay.persistence.customer.Customer
import br.com.amz.peekpay.persistence.customer.CustomerRepository
import br.com.amz.peekpay.persistence.order.Order
import br.com.amz.peekpay.usecase.PeekPayService
import br.com.amz.peekpay.usecase.order.OrderService
import br.com.amz.peekpay.usecase.payment.ApplicablePayment
import equalsTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class PeekPayServiceIntegratedTest {

    @Autowired private lateinit var service: PeekPayService
    @Autowired private lateinit var orderService: OrderService
    @Autowired private lateinit var customerRepository: CustomerRepository

    @Test
    fun `should apply a payment for an order`() {
        val customer = Customer(email = "").let {
            customerRepository.save(it)
        }

        val order = Order(
            customer = customer,
            originalValue = 50.toBigDecimal()
        ).let {
            orderService.createOrder(it)
        }

        val applicablePayment = ApplicablePayment(
            orderId = order.id,
            amount = 30.toBigDecimal(),
            idempotencyKey = UUID.randomUUID()
        )

        val payment = service.applyPaymentToOrder(applicablePayment)!!
        val updatedOrder = orderService.getOrder(order.id)!!

        assert(updatedOrder.balance!! equalsTo 20.toBigDecimal())
        assert(updatedOrder.payments.size == 1)
        assert(updatedOrder.payments[0].id == payment.id)
    }

    @Test
    fun `should create an order and pay it right after`() {
        val customer = Customer(email = "").let {
            customerRepository.save(it)
        }

        val order = Order(
            customer = customer,
            originalValue = 50.toBigDecimal()
        )

        val paymentValue = 40.toBigDecimal()

        val createdOrder = service.createOrderAndPay(order, paymentValue)

        assert(createdOrder.balance!! equalsTo 10.toBigDecimal())
    }

    @Test
    fun `should get order for a customer when filter by customer's email address`() {
        val customer = Customer(email = "").let {
            customerRepository.save(it)
        }

        Order(
            customer = customer,
            originalValue = 50.toBigDecimal()
        ).let {
            orderService.createOrder(it)
        }


        val order = service.getOrdersForCustomer(customer.email)!!

        assert(order.originalValue equalsTo 50.toBigDecimal())
    }
}
