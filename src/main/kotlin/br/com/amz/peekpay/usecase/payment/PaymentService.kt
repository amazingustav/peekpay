package br.com.amz.peekpay.usecase.payment

import br.com.amz.peekpay.persistence.payment.Payment
import br.com.amz.peekpay.persistence.payment.PaymentRepository
import br.com.amz.peekpay.usecase.order.OrderService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class PaymentService(
    private val repository: PaymentRepository,
    private val orderService: OrderService
) {

    @Transactional
    fun createPayment(payment: Payment): Payment = repository.save(payment).let {
        orderService.decreaseBalanceWithPayment(it.amount, it.order.id)
        it
    }

    fun isPaymentLocked(idempotencyKey: UUID): Boolean {
        val payment = repository.findByIdempotencyKey(idempotencyKey)
        return payment != null
    }
}
