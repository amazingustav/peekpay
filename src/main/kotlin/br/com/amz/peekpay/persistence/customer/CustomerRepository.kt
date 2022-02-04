package br.com.amz.peekpay.persistence.customer

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerRepository : CrudRepository<CustomerDBO, UUID> {

    @Query("select c from CustomerDBO c where c.email = :email")
    fun findByEmail(email: String): CustomerDBO?
}
