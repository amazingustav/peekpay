package br.com.amz.peekpay.persistence.customer

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerRepository : CrudRepository<Customer, UUID> {

    @Query("select c from Customer c where c.email = :email")
    fun findByEmail(email: String): Customer?
}
