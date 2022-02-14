package br.com.amz.peekpay.persistence.customer

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "customers")
data class Customer (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    val email: String
)
