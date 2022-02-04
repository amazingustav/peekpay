package br.com.amz.peekpay.persistence.order

import br.com.amz.peekpay.persistence.customer.CustomerDBO
import br.com.amz.peekpay.persistence.payment.PaymentDBO
import java.math.BigDecimal
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "orders")
data class OrderDBO(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    val id: UUID,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    val customer: CustomerDBO,

    @Column(name = "original_value", nullable = false)
    val originalValue: BigDecimal,

    val balance: BigDecimal,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "order")
    val payments: List<PaymentDBO>
)