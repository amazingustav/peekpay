package br.com.amz.peekpay.persistence.payment

import br.com.amz.peekpay.persistence.order.Order
import java.math.BigDecimal
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "payments")
data class Payment (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val amount: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    val order: Order,

    @Column(name = "idempotency_key", updatable = false, unique = true)
    val idempotencyKey: UUID? = null
)
