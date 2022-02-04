package br.com.amz.peekpay.usecase.order

import br.com.amz.peekpay.persistence.order.OrderDBO
import br.com.amz.peekpay.persistence.order.OrderRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class OrderService(private val repository: OrderRepository) {

    /** create_order */
    fun createOrder(order: OrderDBO): OrderDBO = repository.save(order)

    /** get_order */
    fun getOrder(orderId: UUID): OrderDBO? = repository.findById(orderId).orElse(null)

    fun getOrderByCustomer(customerId: UUID): OrderDBO? = repository.findByCustomer(customerId)

    fun decreaseBalanceWithPayment(paymentAmount: BigDecimal, orderId: UUID) {
        repository.findById(orderId).apply {
            if (this.isPresent) {
                val order = this.get()

                order.balance = if (order.balance == null) {
                    order.originalValue.minus(paymentAmount)
                } else {
                    order.balance?.minus(paymentAmount)
                }

                repository.save(order)
            }
        }
    }
}
