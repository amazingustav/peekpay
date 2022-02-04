package br.com.amz.peekpay.payment

import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class PaymentService(private val repository: PaymentRepository) {

    /** apply_payment_to_order */
    fun applyPayment(payment: Payment, orderId: UUID) {
        //TODO: it should be idempotent, avoiding duplicated payment for e.g.
    }

    fun createPayment(value: BigDecimal, orderId: UUID): UUID = repository.create(value, orderId)
}
