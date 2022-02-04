package br.com.amz.peekpay.usecase.payment

import br.com.amz.peekpay.persistence.payment.PaymentDBO
import br.com.amz.peekpay.persistence.payment.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(private val repository: PaymentRepository) {

    fun createPayment(payment: PaymentDBO): PaymentDBO = repository.save(payment)
}
