package br.com.amz.peekpay.persistence.order

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderRepository : CrudRepository<OrderDBO, UUID> {

    @Query("select o from OrderDBO o where o.customer.id = :customerId")
    fun findByCustomer(customerId: UUID): OrderDBO?
}
