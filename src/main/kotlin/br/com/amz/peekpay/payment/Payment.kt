package br.com.amz.peekpay.payment

import br.com.amz.peekpay.order.Order
import java.math.BigDecimal
import java.util.UUID

data class Payment(
    val paymentId: UUID,
    val amount: BigDecimal,
    val order: Order
)

data class PaymentOrder(
    val paymentId: UUID,
    val amount: BigDecimal,
)
