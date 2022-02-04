package br.com.amz.peekpay.persistence.order

import br.com.amz.peekpay.persistence.customer.Customer
import br.com.amz.peekpay.persistence.customer.CustomerRepository
import equalsTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderRepositoryTest {

    @Autowired private lateinit var repository: OrderRepository
    @Autowired private lateinit var customerRepository: CustomerRepository

    @Test
    fun `should create an order and find it by its customer's email`() {
        val customer = Customer(email = "test@test.co").let {
            customerRepository.save(it)
        }

        OrderDBO(
            customer = customer,
            originalValue = 100.toBigDecimal()
        ).apply {
            repository.save(this)
        }

        val createdOrder = repository.findByCustomer(customer.id)!!

        assert(createdOrder.customer.email == "test@test.co")
        assert(createdOrder.originalValue equalsTo 100.toBigDecimal())
    }
}
