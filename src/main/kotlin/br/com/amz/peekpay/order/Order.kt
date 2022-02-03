package br.com.amz.peekpay.order

import br.com.amz.peekpay.payment.Payment
import java.math.BigDecimal
import java.util.UUID

data class Order(
    val orderId: UUID,
    val customer: Customer,
    val originalValue: BigDecimal,
    val balance: BigDecimal,
    val payments: List<Payment>
)

data class Customer(
    val customerId: UUID,
    val email: String,
)
