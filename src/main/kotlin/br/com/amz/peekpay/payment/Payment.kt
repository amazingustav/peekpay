package br.com.amz.peekpay.payment

import java.math.BigDecimal
import java.util.UUID

data class Payment(
    val paymentId: UUID,
    val amount: BigDecimal
)
