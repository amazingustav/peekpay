package br.com.amz.peekpay.usecase.customer

import br.com.amz.peekpay.persistence.customer.Customer
import br.com.amz.peekpay.persistence.customer.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(private val repository: CustomerRepository) {

    fun getCustomerByEmail(email: String): Customer? = repository.findByEmail(email)
}
