package br.com.amz.peekpay.persistence.customer

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired private lateinit var repository: CustomerRepository

    @Test
    fun `should create a new customer and find it by its email`() {
        val customer = CustomerDBO(email = "test1@test.co")

        repository.save(customer)
        val createdCustomer = repository.findByEmail(customer.email)!!

        assert(createdCustomer.email == "test1@test.co")
    }
}
