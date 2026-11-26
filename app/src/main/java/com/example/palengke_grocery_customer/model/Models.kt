package com.example.palengke_grocery_customer.model

data class Category(val id: String, val name: String, val emoji: String, val colorHex: Long)
data class Product(val id: String, val name: String, val price: Double, val unit: String, val imageUrl: String, val rating: Double, val storeName: String, val category: String, val inStock: Boolean = true)
data class Store(val id: String, val name: String, val distance: String, val rating: Double, val imageUrl: String, val tags: List<String>)
data class CartItem(val product: Product, val qty: Int)
data class Address(val id: String, val label: String, val full: String, val isDefault: Boolean = false)
data class Order(val id: String, val date: String, val status: String, val total: Double, val items: Int, val storeName: String)
data class Promo(val id: String, val title: String, val subtitle: String, val code: String, val colorHex: Long)
data class NotificationItem(val id: String, val title: String, val body: String, val time: String, val unread: Boolean)
data class ChatMessage(val id: String, val text: String, val fromMe: Boolean, val time: String)
data class FaqItem(val q: String, val a: String)