package com.example.palengke_grocery_customer.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Otp : Screen("otp")
    object Home : Screen("home")
    object BrowseCategory : Screen("browse/{categoryId}") {
        fun createRoute(categoryId: String) = "browse/${Uri.encode(categoryId)}"
    }
    object SearchResults : Screen("search/{query}") {
        fun createRoute(query: String) = "search/${Uri.encode(query)}"
    }
    object SearchFilters : Screen("filters")
    object AllCategories : Screen("all_categories")
    object NearbyStalls : Screen("nearby_stalls")
    object Store : Screen("store/{storeId}") {
        fun createRoute(storeId: String) = "store/$storeId"
    }
    object ProductDetail : Screen("product/{productId}") {
        fun createRoute(productId: String) = "product/$productId"
    }
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object OrderPlaced : Screen("order_placed")
    object PaymentMethods : Screen("payment_methods")
    object OrderTracking : Screen("tracking/{orderId}") {
        fun createRoute(orderId: String) = "tracking/$orderId"
    }
    object OrderHistory : Screen("order_history")
    object OrderDetails : Screen("order_details/{orderId}") {
        fun createRoute(orderId: String) = "order_details/$orderId"
    }
    object RateOrder : Screen("rate/{orderId}") {
        fun createRoute(orderId: String) = "rate/$orderId"
    }
    object Profile : Screen("profile")
    object AddressBook : Screen("address_book")
    object Promos : Screen("promos")
    object Notifications : Screen("notifications")
    object Favorites : Screen("favorites")
    object Chat : Screen("chat")
    object HelpFaq : Screen("help_faq")
    object Settings : Screen("settings")
}
