package br.com.amz.peekpay.order

import br.com.amz.peekpay.payment.PaymentService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class OrderService(
    private val paymentService: PaymentService,
    private val repository: OrderRepository
) {

    /** create_order */
    fun createOrder(order: Order): UUID = repository.create(order)

    /** get_order */
    fun getOrder(orderId: UUID): OrderPayments = repository.findById(orderId)

    /** get_orders_for_customer */
    fun getOrderByCustomer(customerEmail: String): Order = repository.findByCustomerEmail(customerEmail)

    /** create_order_and_pay */
    fun createOrderAndPay(order: Order, paymentValue: BigDecimal) {
        val balance = order.originalValue.let {
            if (paymentValue <= it) it - paymentValue else it
        }

        //TODO: this block must to be transactional
        val createdOrderId = createOrder(order.copy(balance = balance))
        paymentService.createPayment(paymentValue, createdOrderId)
    }
}
