package br.com.amz.peekpay.usecase.payment

import java.math.BigDecimal
import java.util.UUID

data class ApplicablePayment(
    val amount: BigDecimal,
    val orderId: UUID,
    val idempotencyKey: UUID
)
