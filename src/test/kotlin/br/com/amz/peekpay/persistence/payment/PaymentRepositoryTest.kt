package br.com.amz.peekpay.persistence.payment

import br.com.amz.peekpay.persistence.customer.Customer
import br.com.amz.peekpay.persistence.customer.CustomerRepository
import br.com.amz.peekpay.persistence.order.Order
import br.com.amz.peekpay.persistence.order.OrderRepository
import equalsTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class PaymentRepositoryTest {

    @Autowired private lateinit var repository: PaymentRepository
    @Autowired private lateinit var orderRepository: OrderRepository
    @Autowired private lateinit var customerRepository: CustomerRepository

    @Test
    fun `should create a payment and find it by its order`() {
        val customer = Customer(email = "test@test.co").let {
            customerRepository.save(it)
        }

        val order = Order(
            customer = customer,
            originalValue = 100.toBigDecimal()
        ).let {
            orderRepository.save(it)
        }

        PaymentDBO(
            amount = 10.toBigDecimal(),
            order = order
        ).apply {
            repository.save(this)
        }

        val createdPayment = repository.findByOrderId(order.id)[0]

        assert(createdPayment.order.originalValue equalsTo order.originalValue)
    }

    @Test
    fun `should create a payment and find it by its idempotency key`() {
        val customer = Customer(email = "test@test.co").let {
            customerRepository.save(it)
        }

        val order = Order(
            customer = customer,
            originalValue = 100.toBigDecimal()
        ).let {
            orderRepository.save(it)
        }

        val idempotencyKey = UUID.randomUUID()

        PaymentDBO(
            amount = 10.toBigDecimal(),
            order = order,
            idempotencyKey = idempotencyKey
        ).apply {
            repository.save(this)
        }

        val createdPayment = repository.findByIdempotencyKey(idempotencyKey)!!

        assert(createdPayment.amount equalsTo 10.toBigDecimal())
    }
}
