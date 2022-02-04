package br.com.amz.peekpay.order

import br.com.amz.peekpay.payment.PaymentOrder
import java.math.BigDecimal
import java.util.UUID

data class Order(
    val orderId: UUID? = null,
    val customer: Customer,
    val originalValue: BigDecimal,
    val balance: BigDecimal?
)

data class Customer(
    val customerId: UUID,
    val email: String
)

data class OrderPayments(
    val orderId: UUID,
    val customer: Customer,
    val originalValue: BigDecimal,
    val balance: BigDecimal,
    val payments: List<PaymentOrder>
)
