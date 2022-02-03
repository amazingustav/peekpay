package br.com.amz.peekpay

import br.com.amz.peekpay.order.Customer
import br.com.amz.peekpay.order.Order
import br.com.amz.peekpay.payment.Payment
import java.math.BigDecimal
import java.util.*

fun createDefaultOrder() = Order(
    orderId = UUID.randomUUID(),
    customer = createDefaultCustomer(),
    originalValue = BigDecimal(1000),
    balance = BigDecimal(200),
    payments = listOf(
        createDefaultPayment(500.toBigDecimal()),
        createDefaultPayment(200.toBigDecimal()),
        createDefaultPayment(100.toBigDecimal())
    )
)

fun createDefaultCustomer() =  Customer(
    customerId = UUID.randomUUID(),
    email = "john@doe.co"
)

fun createDefaultPayment(value: BigDecimal = 100.toBigDecimal()) = Payment(
    paymentId = UUID.randomUUID(),
    amount = value
)
