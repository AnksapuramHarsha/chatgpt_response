package com.example.contenturl.model

import jakarta.persistence.*

@Entity
@Table(name="customer_urls")
class customer_urls(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val customer_url: String)

{


}