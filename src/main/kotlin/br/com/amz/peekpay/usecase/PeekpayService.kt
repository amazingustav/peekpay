package br.com.amz.peekpay.usecase

import br.com.amz.peekpay.persistence.order.Order
import br.com.amz.peekpay.persistence.payment.Payment
import br.com.amz.peekpay.usecase.customer.CustomerService
import br.com.amz.peekpay.usecase.exception.OrderNotFoundException
import br.com.amz.peekpay.usecase.order.OrderService
import br.com.amz.peekpay.usecase.payment.ApplicablePayment
import br.com.amz.peekpay.usecase.payment.PaymentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class PeekPayService(
    private val customerService: CustomerService,
    private val orderService: OrderService,
    private val paymentService: PaymentService
) {
    /** apply_payment_to_order */
    fun applyPaymentToOrder(applicablePayment: ApplicablePayment): Payment? {
        val isPaymentLocked = paymentService.isPaymentLocked(applicablePayment.idempotencyKey)

        if (isPaymentLocked) return null

        val order = orderService.getOrder(applicablePayment.orderId)
            ?: throw OrderNotFoundException("Order not found for the id ${applicablePayment.orderId}")

        return Payment(
            amount = applicablePayment.amount,
            order = order,
            idempotencyKey = applicablePayment.idempotencyKey
        ).let {
            paymentService.createPayment(it)
        }
    }

    /** create_order_and_pay */
    @Transactional
    fun createOrderAndPay(order: Order, paymentValue: BigDecimal): Order {
        val createdOrder = orderService.createOrder(order)

        return Payment(
            amount = paymentValue,
            order = createdOrder
        ).let {
            paymentService.createPayment(it)
            orderService.getOrder(createdOrder.id)!!
        }
    }

    /** get_orders_for_customer */
    fun getOrdersForCustomer(customerEmail: String): Order? {
        val customer = customerService.getCustomerByEmail(customerEmail) ?: return null

        return orderService.getOrderByCustomer(customer.id)
    }
}
