package br.com.amz.peekpay.persistence.payment

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentRepository : CrudRepository<Payment, UUID> {

    @Query("select p from Payment p where p.order.id = :orderId")
    fun findByOrderId(orderId: UUID): List<Payment>

    @Query("select p from Payment p where p.idempotencyKey = :idempotencyKey")
    fun findByIdempotencyKey(idempotencyKey: UUID): Payment?
}
