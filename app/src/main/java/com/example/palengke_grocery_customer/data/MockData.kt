package com.example.palengke_grocery_customer.data

import com.example.palengke_grocery_customer.model.*

object MockData {
    val categories = listOf(
        Category("1", "Rice", "🌾", 0xFFF8EFDC),
        Category("2", "Canned", "🥫", 0xFFFBE8E5),
        Category("3", "Noodles", "🍜", 0xFFE5F4EA),
        Category("4", "Drinks", "🥤", 0xFFE7F0F8),
        Category("5", "Snacks", "🍿", 0xFFF0EAF8),
        Category("6", "Fresh", "🥬", 0xFFEAF5EE),
        Category("7", "Meat", "🥩", 0xFFFBE8E5),
        Category("8", "Personal", "🧴", 0xFFFDF1DC)
    )

    val products = listOf(
        Product("p1", "Jasmine Rice 5kg", 320.0, "sack", "https://picsum.photos/seed/rice1/200/200", 4.8, "Palengke Central", "Rice"),
        Product("p2", "Lucky Me Pancit Canton", 18.0, "pack", "https://picsum.photos/seed/noodle1/200/200", 4.6, "Sari-Sari Ni Aling Rosa", "Noodles"),
        Product("p3", "Coca-Cola 1.5L", 65.0, "bottle", "https://picsum.photos/seed/drink1/200/200", 4.5, "Palengke Central", "Drinks"),
        Product("p4", "Century Tuna Flakes", 45.0, "can", "https://picsum.photos/seed/can1/200/200", 4.7, "Tindahan ni Mang Juan", "Canned"),
        Product("p5", "Chippy BBQ", 22.0, "bag", "https://picsum.photos/seed/snack1/200/200", 4.3, "Sari-Sari Ni Aling Rosa", "Snacks"),
        Product("p6", "Pechay (1 bundle)", 30.0, "bundle", "https://picsum.photos/seed/veg1/200/200", 4.9, "Palengke Central", "Fresh"),
        Product("p7", "Pork Belly (liempo) 1kg", 280.0, "kg", "https://picsum.photos/seed/meat1/200/200", 4.6, "Palengke Central", "Meat"),
        Product("p8", "Safeguard Soap", 42.0, "bar", "https://picsum.photos/seed/soap1/200/200", 4.4, "Tindahan ni Mang Juan", "Personal"),
        Product("p9", "Bear Brand Milk 150g", 38.0, "sachet", "https://picsum.photos/seed/milk1/200/200", 4.5, "Palengke Central", "Drinks"),
        Product("p10", "Ligo Sardines 155g", 32.0, "can", "https://picsum.photos/seed/sard1/200/200", 4.6, "Tindahan ni Mang Juan", "Canned")
    )

    val stores = listOf(
        Store("s1", "Palengke Central", "0.3 km", 4.8, "https://picsum.photos/seed/store1/300/160", listOf("Rice", "Fresh", "Meat")),
        Store("s2", "Sari-Sari Ni Aling Rosa", "0.6 km", 4.6, "https://picsum.photos/seed/store2/300/160", listOf("Snacks", "Drinks")),
        Store("s3", "Tindahan ni Mang Juan", "1.1 km", 4.5, "https://picsum.photos/seed/store3/300/160", listOf("Canned", "Personal"))
    )

    val cart = listOf(CartItem(products[0], 1), CartItem(products[1], 3), CartItem(products[3], 2))

    val addresses = listOf(
        Address("a1", "Home", "Purok 3, Brgy. San Julian, Irosin, Sorsogon", true),
        Address("a2", "Work", "Municipal Hall, Irosin, Sorsogon", false)
    )

    val orders = listOf(
        Order("ORD-1024", "Nov 24, 2026", "Delivered", 416.0, 6, "Palengke Central"),
        Order("ORD-1023", "Nov 22, 2026", "Delivered", 128.0, 4, "Sari-Sari Ni Aling Rosa"),
        Order("ORD-1022", "Nov 20, 2026", "Cancelled", 95.0, 2, "Tindahan ni Mang Juan")
    )

    val promos = listOf(
        Promo("pr1", "Free delivery", "On orders above ₱500", "FREESHIP", 0xFFE5F4EA),
        Promo("pr2", "₱50 off", "First order discount", "WELCOME50", 0xFFF3F9DD),
        Promo("pr3", "Buy 2 Get 1", "On selected canned goods", "B2G1CAN", 0xFFFBE8E5)
    )

    val notifications = listOf(
        NotificationItem("n1", "Order delivered", "Your order ORD-1024 has been delivered.", "2 min ago", true),
        NotificationItem("n2", "Promo alert", "Free delivery on orders above ₱500 today!", "1 hr ago", true),
        NotificationItem("n3", "Price drop", "Jasmine Rice 5kg now ₱320", "Yesterday", false)
    )

    val chat = listOf(
        ChatMessage("c1", "Hi! How can I help you today?", false, "9:40 AM"),
        ChatMessage("c2", "Where is my order ORD-1024?", true, "9:41 AM"),
        ChatMessage("c3", "It's out for delivery and will arrive in 15 mins.", false, "9:41 AM")
    )

    val faqs = listOf(
        FaqItem("How do I place an order?", "Browse products, add to cart, choose payment method and confirm checkout."),
        FaqItem("What payment methods are accepted?", "Cash on delivery, GCash, and Maya."),
        FaqItem("How do I track my order?", "Go to Orders and tap an order to see live tracking."),
        FaqItem("Can I cancel an order?", "Yes, within 5 minutes of placing it from Order details.")
    )
}