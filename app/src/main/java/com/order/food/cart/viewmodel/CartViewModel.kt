package com.order.food.cart.viewmodel

import androidx.lifecycle.ViewModel
import com.order.food.cart.model.CartItem
import com.order.food.cart.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Add Product
    fun addToCart(product: Product) {

        val currentList = _cartItems.value.toMutableList()

        val index = currentList.indexOfFirst {
            it.product.id == product.id
        }

        if (index == -1) {

            currentList.add(
                CartItem(
                    product = product,
                    quantity = 1
                )
            )

        } else {

            val item = currentList[index]

            currentList[index] = item.copy(
                quantity = item.quantity + 1
            )
        }

        _cartItems.value = currentList
    }

    // Increase Quantity
    fun increaseQuantity(productId: Int) {

        _cartItems.value = _cartItems.value.map {

            if (it.product.id == productId) {

                it.copy(quantity = it.quantity + 1)

            } else {

                it
            }
        }
    }

    // Decrease Quantity
    fun decreaseQuantity(productId: Int) {

        val currentList = _cartItems.value.toMutableList()

        val index = currentList.indexOfFirst {
            it.product.id == productId
        }

        if (index == -1) return

        val item = currentList[index]

        if (item.quantity == 1) {

            currentList.removeAt(index)

        } else {

            currentList[index] = item.copy(
                quantity = item.quantity - 1
            )
        }

        _cartItems.value = currentList
    }

    // Remove Item
    fun removeItem(productId: Int) {

        _cartItems.value = _cartItems.value.filter {
            it.product.id != productId
        }
    }

    // Quantity of Particular Product
    fun getQuantity(productId: Int): Int {

        return _cartItems.value
            .find {
                it.product.id == productId
            }
            ?.quantity ?: 0
    }

    // Total Cart Items
    fun getTotalItems(): Int {

        return _cartItems.value.sumOf {
            it.quantity
        }
    }

    // Total Cart Price
    fun getTotalPrice(): Double {

        return _cartItems.value.sumOf {

            it.product.price * it.quantity
        }
    }

    // Clear Cart
    fun clearCart() {

        _cartItems.value = emptyList()
    }
}