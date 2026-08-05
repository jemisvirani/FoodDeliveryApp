package com.order.food.cart.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val rating : String,
    val image: String,
    val description: String
)