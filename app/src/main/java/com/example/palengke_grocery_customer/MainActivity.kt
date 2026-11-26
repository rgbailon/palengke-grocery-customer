package com.example.palengke_grocery_customer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.screens.auth.*
import com.example.palengke_grocery_customer.screens.browse.*
import com.example.palengke_grocery_customer.screens.cart.*
import com.example.palengke_grocery_customer.screens.extras.*
import com.example.palengke_grocery_customer.screens.home.HomeScreen
import com.example.palengke_grocery_customer.screens.orders.*
import com.example.palengke_grocery_customer.screens.profile.*
import com.example.palengke_grocery_customer.ui.theme.PalengkeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PalengkeTheme {
                PalengkeApp()
            }
        }
    }
}

@Composable
fun PalengkeApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Otp.route) { OtpScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.BrowseCategory.route, arguments = listOf(navArgument("categoryId") { type = NavType.StringType })) { back ->
            BrowseCategoryScreen(navController, back.arguments?.getString("categoryId") ?: "")
        }
        composable(Screen.SearchResults.route, arguments = listOf(navArgument("query") { type = NavType.StringType })) { back ->
            SearchResultsScreen(navController, back.arguments?.getString("query") ?: "")
        }
        composable(Screen.SearchFilters.route) { SearchFiltersScreen(navController) }
        composable(Screen.AllCategories.route) { AllCategoriesScreen(navController) }
        composable(Screen.NearbyStalls.route) { NearbyStallsScreen(navController) }
        composable(Screen.Store.route, arguments = listOf(navArgument("storeId") { type = NavType.StringType })) { back ->
            StoreScreen(navController, back.arguments?.getString("storeId") ?: "")
        }
        composable(Screen.ProductDetail.route, arguments = listOf(navArgument("productId") { type = NavType.StringType })) { back ->
            ProductDetailScreen(navController, back.arguments?.getString("productId") ?: "")
        }
        composable(Screen.Cart.route) { CartScreen(navController) }
        composable(Screen.Checkout.route) { CheckoutScreen(navController) }
        composable(Screen.OrderPlaced.route) { OrderPlacedScreen(navController) }
        composable(Screen.PaymentMethods.route) { PaymentMethodsScreen(navController) }
        composable(Screen.OrderTracking.route, arguments = listOf(navArgument("orderId") { type = NavType.StringType })) { back ->
            OrderTrackingScreen(navController, back.arguments?.getString("orderId") ?: "")
        }
        composable(Screen.OrderHistory.route) { OrderHistoryScreen(navController) }
        composable(Screen.OrderDetails.route, arguments = listOf(navArgument("orderId") { type = NavType.StringType })) { back ->
            OrderDetailsScreen(navController, back.arguments?.getString("orderId") ?: "")
        }
        composable(Screen.RateOrder.route, arguments = listOf(navArgument("orderId") { type = NavType.StringType })) { back ->
            RateOrderScreen(navController, back.arguments?.getString("orderId") ?: "")
        }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.AddressBook.route) { AddressBookScreen(navController) }
        composable(Screen.Promos.route) { PromosScreen(navController) }
        composable(Screen.Notifications.route) { NotificationsScreen(navController) }
        composable(Screen.Favorites.route) { FavoritesScreen(navController) }
        composable(Screen.Chat.route) { ChatScreen(navController) }
        composable(Screen.HelpFaq.route) { HelpFaqScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
    }
}