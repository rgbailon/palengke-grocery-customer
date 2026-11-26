package com.example.palengke_grocery_customer.data

import com.example.palengke_grocery_customer.R
import com.example.palengke_grocery_customer.model.*

object MockData {

    val userName = "Juan Dela Cruz"
    val userPhone = "0917 123 4567"
    val userInitials = "JD"
    val barangay = "Poblacion, Irosin"

    // ── Categories (B05: 8 categories · 124 items) ──
    val categories = listOf(
        Category("rice", "Rice", 24, 0xFFF7F1DC, "rice"),
        Category("canned", "Canned", 18, 0xFFFAE3E0, "canned"),
        Category("noodles", "Noodles", 16, 0xFFDFEFE3, "noodles"),
        Category("coffee", "Coffee", 12, 0xFFFAEED6, "coffee"),
        Category("snacks", "Snacks", 20, 0xFFF7F1DC, "snacks"),
        Category("dairy", "Dairy", 14, 0xFFE4EEF8, "dairy"),
        Category("sauces", "Sauces", 10, 0xFFFAE3E0, "sauces"),
        Category("cleaning", "Cleaning", 10, 0xFFEDEAF7, "cleaning")
    )

    // ── Products with real photos ──
    val products = listOf(
        Product(
            "p_pancit", "Pancit Canton", "60g · Cantonese", "Lucky Me", 12, 15, 4.8, 120,
            R.drawable.prod_pancit, 0xFFDFEFE3, "Noodles",
            "Instant noodles with Cantonese flavor. Good for 1–2 servings. Keep in a cool, dry place.", 250
        ),
        Product(
            "p_supreme", "Lucky Me Supreme", "60g · Beef", "Lucky Me", 14, null, 4.7, 73,
            R.drawable.prod_supreme, 0xFFDFEFE3, "Noodles",
            "Rich beef-flavored instant mami soup. Best enjoyed hot right after cooking.", 180
        ),
        Product(
            "p_cup", "Cup Noodles", "40g · Seafood", "Nissin", 18, null, 4.6, 52,
            R.drawable.prod_cupnoodles, 0xFFDFEFE3, "Noodles",
            "Seafood-flavor cup noodles. Just add hot water and wait 3 minutes.", 90
        ),
        Product(
            "p_payless", "Payless Xtra Big", "65g · Original", "Payless", 15, null, 4.5, 40,
            R.drawable.prod_payless, 0xFFDFEFE3, "Noodles",
            "Xtra Big pancit canton with kalamansi flavor. Doble dami, doble sarap.", 120
        ),
        Product(
            "p_tuna", "Century Tuna", "180g · Hot & spicy", "Century Pacific", 60, null, 4.7, 86,
            R.drawable.prod_tuna, 0xFFFAE3E0, "Canned",
            "Flaked tuna in brine with hot and spicy notes. Rich in Omega-3 and DHA.", 60
        ),
        Product(
            "p_corned", "Argentina Corned Beef", "150g · Original", "Argentina", 42, null, 4.6, 58,
            R.drawable.prod_cornedbeef, 0xFFFAE3E0, "Canned",
            "Canned corned beef fortified with zinc and iron. Perfect with garlic rice.", 75
        ),
        Product(
            "p_sardines", "555 Sardines", "155g · Tomato sauce", "555", 28, null, 4.5, 44,
            R.drawable.prod_sardines, 0xFFFAE3E0, "Canned",
            "Sardines in tomato sauce. Export quality, good source of protein.", 100
        ),
        Product(
            "p_rice", "Dinorado Rice", "5kg · Premium", "Sunnywood", 280, 310, 4.9, 64,
            R.drawable.prod_rice, 0xFFF7F1DC, "Rice",
            "Natural aromatic semi-sticky rice. Harvester's Special Dinorado, 5kg sack.", 40
        ),
        Product(
            "p_kopiko", "Kopiko Brown", "50g · 10 sachets", "Mayora", 15, null, 4.7, 91,
            R.drawable.prod_kopiko, 0xFFFAEED6, "Coffee",
            "Brown coffee mix, just the right blend. 10 sachets per pack.", 200
        ),
        Product(
            "p_nescafe", "Nescafe Original 3in1", "20 sachets · Dark roast", "Nestlé", 89, null, 4.6, 77,
            R.drawable.prod_nescafe, 0xFFFAEED6, "Coffee",
            "Complete coffee mix with creamer and sugar. Timplang ng katawan, gising ng isipan.", 85
        ),
        Product(
            "p_bearbrand", "Bear Brand", "300g · Fortified", "Nestlé", 95, null, 4.8, 156,
            R.drawable.prod_bearbrand, 0xFFE4EEF8, "Dairy",
            "Fortified powdered milk drink with 100% Vitamin D, protein and calcium.", 55
        ),
        Product(
            "p_skyflakes", "Skyflakes", "25g · 3 pack", "M.Y. San", 30, null, 4.7, 203,
            R.drawable.prod_skyflakes, 0xFFF7F1DC, "Snacks",
            "Classic salted crackers. Light, crispy and perfect with any ulam.", 150
        ),
        Product(
            "p_piattos", "Piattos Cheese", "40g · Cheese", "Jack 'n Jill", 25, null, 4.6, 88,
            R.drawable.prod_piattos, 0xFFEDEAF7, "Snacks",
            "Cheese-flavored potato crisps. Party size, dangerously sarap.", 110
        ),
        Product(
            "p_vinegar", "Datu Puti Vinegar", "385ml · Spiced", "NutriAsia", 18, null, 4.7, 66,
            R.drawable.prod_vinegar, 0xFFF7F1DC, "Sauces",
            "Sukang maanghang, 4.5% natural acidity. Ideal for paksiw and sawsawan.", 90
        ),
        Product(
            "p_soy", "Silver Swan Soy Sauce", "200ml · Special", "Silver Swan", 25, null, 4.6, 54,
            R.drawable.prod_soy, 0xFFFAEED6, "Sauces",
            "Special soy sauce brewed the traditional way. A pantry essential.", 80
        ),
        Product(
            "p_joy", "Joy Dishwashing", "475ml · Lemon", "P&G", 79, null, 4.7, 112,
            R.drawable.prod_joy, 0xFFEDEAF7, "Cleaning",
            "Dishwashing liquid with real lemon extract. Cuts grease fast.", 70
        )
    )

    fun byId(id: String) = products.first { it.id == id }
    fun byCategory(name: String) = products.filter { it.category == name }

    val searchResults = listOf(
        byId("p_pancit"), byId("p_supreme"), byId("p_payless"), byId("p_cup")
    )

    // ── Stores (B06 Nearby stalls) ──
    val stores = listOf(
        Store("s1", "Irosin Public Market", "Stall 12 · Poblacion", 4.9, 312, "0.8 km", "25 min delivery", 0xFFDFEFE3, verified = true),
        Store("s2", "Aling Nena's Store", "Stall 04 · San Isidro", 4.7, 128, "1.2 km", "30 min delivery", 0xFFFAEED6),
        Store("s3", "Sari-Sari ni Tatay", "Stall 21 · Poblacion", 4.6, 86, "1.6 km", "35 min delivery", 0xFFE4EEF8)
    )

    val primaryStore = stores[0]

    // ── Cart (C01: 3 line items · Irosin Public Market) ──
    val cart = listOf(
        CartEntry(byId("p_pancit"), 3),
        CartEntry(byId("p_tuna"), 2),
        CartEntry(byId("p_kopiko"), 1)
    )
    val cartSubtotal = 141
    val deliveryFee = 20
    val cartTotal = 161

    // ── Orders (D02) ──
    val orders = listOf(
        OrderEntry("2048", "Out for delivery", "Nov 11", 3, 161, cart),
        OrderEntry("2047", "Delivered", "Nov 8", 3, 120, listOf(CartEntry(byId("p_pancit"), 2), CartEntry(byId("p_tuna"), 1), CartEntry(byId("p_kopiko"), 1))),
        OrderEntry("2046", "Delivered", "Nov 2", 3, 95, listOf(CartEntry(byId("p_supreme"), 3), CartEntry(byId("p_sardines"), 1), CartEntry(byId("p_skyflakes"), 1))),
        OrderEntry("2045", "Delivered", "Oct 28", 12, 846, listOf(CartEntry(byId("p_rice"), 1), CartEntry(byId("p_bearbrand"), 1), CartEntry(byId("p_nescafe"), 2)))
    )

    val riderName = "Rodel S."
    val riderVehicle = "Honda Click · ABC 1234"
    val riderRating = 4.9
    val riderTrips = 312

    // ── Promos (E03) ──
    val promos = listOf(
        Promo("pr1", "FRESH50", "₱50 off your first order", "Min. spend ₱300 · ends Nov 30", 0xFF0C6B3D),
        Promo("pr2", "FREEDEL", "Free delivery this week", "Min. spend ₱150 · ends Nov 16", 0xFF3C7BB0),
        Promo("pr3", "MARKET10", "10% off market picks", "Max ₱80 off · ends Dec 15", 0xFF8A6234)
    )

    // ── Addresses (E02) ──
    val addresses = listOf(
        AddressEntry("a1", "Home", true, "Juan Dela Cruz · 0917 123 4567",
            listOf("Purok 3, Poblacion, Irosin", "Sorsogon · near the plaza")),
        AddressEntry("a2", "Market stall", false, "Juan Dela Cruz · 0917 123 4567",
            listOf("Stall 12, Irosin Public Market", "Irosin, Sorsogon"))
    )

    // ── Notifications (E04) ──
    val notifications = listOf(
        NotificationItem("n1", "Your order is on the way", "Rodel is 1.2 km away · arriving in 18 min", "2:06 PM", true, 0xFFDFEFE3, "truck"),
        NotificationItem("n2", "Order #2047 delivered", "Rate your rider and earn 5 points", "Nov 8", true, 0xFFDFEFE3, "check"),
        NotificationItem("n3", "₱50 off your next order", "Use FRESH50 on orders over ₱300", "Nov 7", false, 0xFFFAEED6, "tag"),
        NotificationItem("n4", "New stalls in Irosin market", "Fresh catch and vegetables are now live", "Nov 5", false, 0xFFDFEFE3, "store"),
        NotificationItem("n5", "Delivery hours updated", "Orders now accepted until 6:00 PM", "Nov 2", false, 0xFFE4EEF8, "clock")
    )

    // ── Support chat (F02) ──
    val chat = listOf(
        ChatMessage("c1", "Hi Juan! How can we help you today?", false, "Today"),
        ChatMessage("c2", "My order #2048 is running late.", true, "9:41 AM"),
        ChatMessage("c3", "Sorry about that! Rodel is 1.2 km away.\nHe should arrive in about 18 minutes.", false, "9:42 AM"),
        ChatMessage("c4", "Salamat!", true, "9:42 AM")
    )

    // ── FAQs (F03) ──
    val faqs = listOf(
        Faq("How do I track my order?", "Open Orders, tap your active order, then Track order to see the rider live on the map."),
        Faq("What payment methods are accepted?", "Cash on delivery, GCash and Maya. COD is paid to the rider on arrival."),
        Faq("Can I cancel my order?", "Yes, within 10 minutes of placing it, from Order details."),
        Faq("How do refunds work?", "Report an issue within 24 hours. Approved refunds return to your GCash, Maya or COD receipt in 3–5 days.")
    )

    // ── Favorites (F01: 6 items) ──
    val favorites = listOf(
        byId("p_pancit"), byId("p_tuna"), byId("p_kopiko"),
        byId("p_rice"), byId("p_bearbrand"), byId("p_skyflakes")
    )
}
