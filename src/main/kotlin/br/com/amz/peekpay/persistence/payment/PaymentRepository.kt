package br.com.amz.peekpay.persistence.payment

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentRepository : CrudRepository<PaymentDBO, UUID> {

    @Query("select p from PaymentDBO p where p.order.id = :orderId")
    fun findByOrderId(orderId: UUID): List<PaymentDBO>

    @Query("select p from PaymentDBO p where p.idempotencyKey = :idempotencyKey")
    fun findByIdempotencyKey(idempotencyKey: UUID): PaymentDBO?
}
