package br.com.amz.peekpay

import br.com.amz.peekpay.order.Customer
import br.com.amz.peekpay.order.Order
import br.com.amz.peekpay.order.OrderPayments
import br.com.amz.peekpay.payment.Payment
import br.com.amz.peekpay.payment.PaymentOrder
import java.math.BigDecimal
import java.util.*

fun createDefaultOrder() = Order(
    orderId = UUID.randomUUID(),
    customer = createDefaultCustomer(),
    originalValue = BigDecimal(1000),
    balance = BigDecimal(200)
)

fun createDefaultOrderAndPayments() = OrderPayments(
    orderId = UUID.randomUUID(),
    customer = createDefaultCustomer(),
    originalValue = BigDecimal(1000),
    balance = BigDecimal(200),
    payments = listOf(
        createDefaultPaymentOrder(500.toBigDecimal()),
        createDefaultPaymentOrder(200.toBigDecimal()),
        createDefaultPaymentOrder(100.toBigDecimal())
    )
)

fun createDefaultCustomer() =  Customer(
    customerId = UUID.randomUUID(),
    email = "john@doe.co"
)

fun createDefaultPayment(
    value: BigDecimal = 100.toBigDecimal(),
    order: Order
) = Payment(
    paymentId = UUID.randomUUID(),
    amount = value,
    order = order
)

fun createDefaultPaymentOrder(value: BigDecimal = 100.toBigDecimal()) = PaymentOrder(
    paymentId = UUID.randomUUID(),
    amount = value
)
