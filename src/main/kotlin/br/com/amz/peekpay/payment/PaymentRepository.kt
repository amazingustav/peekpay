package br.com.amz.peekpay.payment

import br.com.amz.peekpay.createDefaultOrder
import br.com.amz.peekpay.createDefaultPayment
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.UUID

@Repository
class PaymentRepository {
    fun create(value: BigDecimal, orderId: UUID): UUID {
        val order = createDefaultOrder()

        return createDefaultPayment(
            value = value,
            order = order
        ).paymentId
    }
}
