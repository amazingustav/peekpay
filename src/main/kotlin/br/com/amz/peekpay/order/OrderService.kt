package br.com.amz.peekpay.order

import br.com.amz.peekpay.createDefaultOrder
import br.com.amz.peekpay.payment.Payment
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderService {

    /**
     * create_order
     * */
    fun createOrder(order: Order) {
        //TODO: create an order without making an initial payment
    }

    /**
     * get_order
     * */
    fun getOrder(orderId: UUID): Order {
        //TODO: it should return any payments applied to it as well

        return createDefaultOrder()
    }

    /**
     * get_orders_for_customer
     * */
    fun getOrderByCustomer(customerEmail: String): Order {
        //TODO: return all customer's order

        return createDefaultOrder()
    }

    /**
     * create_order_and_pay
     * */
    fun createOrderAndPay(order: Order, payment: Payment) {
        //TODO: it should be transactional. One depends on another
    }
}
