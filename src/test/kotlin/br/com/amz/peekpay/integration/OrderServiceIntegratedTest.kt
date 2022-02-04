package br.com.amz.peekpay.integration

import br.com.amz.peekpay.persistence.customer.Customer
import br.com.amz.peekpay.persistence.customer.CustomerRepository
import br.com.amz.peekpay.persistence.order.Order
import br.com.amz.peekpay.usecase.order.OrderService
import equalsTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderServiceIntegratedTest {

    @Autowired private lateinit var service: OrderService
    @Autowired private lateinit var customerRepository: CustomerRepository

    @Test
    fun `should create an order`() {
        val customer = Customer(email = "test@test.co").let {
            customerRepository.save(it)
        }

        val order = Order(
            customer = customer,
            originalValue = 100.toBigDecimal()
        ).let {
            service.createOrder(it)
        }

        assert(order.customer.email == "test@test.co")
    }

    @Test
    fun `should get an order when pass an orderId`(){
        val customer = Customer(email = "test@test.co").let {
            customerRepository.save(it)
        }

        val order = Order(
            customer = customer,
            originalValue = 100.toBigDecimal()
        ).let {
            service.createOrder(it)
        }

        val createdOrder = service.getOrder(order.id)!!

        assert(createdOrder.originalValue equalsTo order.originalValue)
    }
}
