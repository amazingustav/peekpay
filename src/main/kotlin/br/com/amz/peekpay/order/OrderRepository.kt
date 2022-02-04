package br.com.amz.peekpay.order

import br.com.amz.peekpay.createDefaultOrder
import br.com.amz.peekpay.createDefaultOrderAndPayments
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class OrderRepository {
    fun create(order: Order): UUID {
        val createdOrder = Order(
            orderId = UUID.randomUUID(),
            customer = order.customer,
            originalValue = order.originalValue,
            balance = order.balance ?: order.originalValue
        )

        return createdOrder.orderId!!
    }

    fun findById(orderId: UUID): OrderPayments {
        // TODO("it should return any payments applied to it as well")

        return createDefaultOrderAndPayments()
    }

    fun findByCustomerEmail(customerEmail: String): Order {
        return createDefaultOrder()
    }
}
