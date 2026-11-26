package com.example.palengke_grocery_customer.model

data class Category(
    val id: String,
    val name: String,
    val itemCount: Int,
    val tileColor: Long,
    val iconKey: String
)

data class Product(
    val id: String,
    val name: String,
    val sizeLine: String,          // e.g. "60g · Cantonese"
    val brand: String? = null,
    val price: Int,
    val oldPrice: Int? = null,
    val rating: Double,
    val ratingCount: Int,
    val imageRes: Int,             // real product photo drawable
    val tileColor: Long,           // pastel tile behind the photo
    val category: String,
    val details: String,
    val stock: Int = 250
)

data class Store(
    val id: String,
    val name: String,
    val sub: String,
    val rating: Double,
    val ratingCount: Int,
    val distance: String,
    val eta: String,
    val tileColor: Long,
    val verified: Boolean = false
)

data class CartEntry(val product: Product, val qty: Int)

data class OrderEntry(
    val id: String,
    val status: String,            // "Out for delivery" | "Delivered"
    val date: String,
    val itemCount: Int,
    val total: Int,
    val items: List<CartEntry>
)

data class Promo(
    val id: String,
    val code: String,
    val title: String,
    val terms: String,
    val bg: Long
)

data class NotificationItem(
    val id: String,
    val title: String,
    val body: String,
    val time: String,
    val unread: Boolean,
    val tileColor: Long,
    val iconKey: String
)

data class ChatMessage(val id: String, val text: String, val fromMe: Boolean, val time: String)

data class Faq(val q: String, val a: String)

data class AddressEntry(
    val id: String,
    val label: String,
    val isDefault: Boolean,
    val contact: String,
    val lines: List<String>
)
