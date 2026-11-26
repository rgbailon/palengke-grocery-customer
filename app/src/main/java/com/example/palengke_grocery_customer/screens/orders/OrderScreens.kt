package com.example.palengke_grocery_customer.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.palengke_grocery_customer.data.MockData
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

@Composable
fun OrderTrackingScreen(navController: NavController, orderId: String) {
    val steps = listOf("Order placed", "Preparing", "Out for delivery", "Delivered")
    var current by remember { mutableStateOf(2) }
    Scaffold(topBar = { PalengkeTopBar("Track order", onBack = { navController.popBackStack() }) }) { padding ->
        Column(Modifier.fillMaxSize().background(Background).padding(padding).padding(16.dp)) {
            Text("Order #$orderId", fontWeight = FontWeight.Bold, color = OnSurface)
            Text("Estimated delivery: 15 mins", color = OnSurfaceVariant, fontSize = 12.sp)
            Spacer(Modifier.height(24.dp))
            steps.forEachIndexed { i, step ->
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(Modifier.size(28.dp).background(if (i <= current) Leaf else Border, RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                            if (i <= current) Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                        if (i < steps.lastIndex) Box(Modifier.width(2.dp).height(40.dp).background(if (i < current) Leaf else Border))
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(step, fontWeight = if (i == current) FontWeight.Bold else FontWeight.Normal, color = if (i <= current) OnSurface else Muted)
                        if (i == current) Text("In progress...", fontSize = 11.sp, color = Leaf)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Surface)) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DeliveryDining, contentDescription = null, tint = Leaf, modifier = Modifier.size(32.dp))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Rider: Pedro Cruz", fontWeight = FontWeight.SemiBold, color = OnSurface)
                        Text("On the way to your address", fontSize = 11.sp, color = OnSurfaceVariant)
                    }
                    IconButton(onClick = { navController.navigate(Screen.Chat.route) }) { Icon(Icons.Default.Chat, contentDescription = null, tint = Leaf) }
                }
            }
            Spacer(Modifier.height(16.dp))
            SecondaryButton("View order details", { navController.navigate(Screen.OrderDetails.createRoute(orderId)) }, Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun OrderHistoryScreen(navController: NavController) {
    Scaffold(
        topBar = { PalengkeTopBar("Order history") },
        bottomBar = { BottomNavBar("orders") { route -> handleBottomNav2(navController, route) } }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            items(MockData.orders) { order ->
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Surface), modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable { navController.navigate(Screen.OrderDetails.createRoute(order.id)) }) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(order.id, fontWeight = FontWeight.Bold, color = OnSurface)
                            Text(order.date, fontSize = 11.sp, color = OnSurfaceVariant)
                            Text(order.storeName, fontSize = 11.sp, color = Muted)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            StatusChip(order.status, if (order.status == "Delivered") Fresh else if (order.status == "Cancelled") Error else Warning)
                            Spacer(Modifier.height(4.dp))
                            Text("₱${order.total.toInt()}", fontWeight = FontWeight.Bold, color = Leaf)
                            Text("${order.items} items", fontSize = 10.sp, color = Muted)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderDetailsScreen(navController: NavController, orderId: String) {
    val order = MockData.orders.find { it.id == orderId } ?: MockData.orders[0]
    Scaffold(topBar = { PalengkeTopBar("Order details", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            item {
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Surface)) {
                    Column(Modifier.padding(12.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(order.id, fontWeight = FontWeight.Bold, color = OnSurface)
                            StatusChip(order.status, if (order.status == "Delivered") Fresh else Error)
                        }
                        Text(order.date, fontSize = 11.sp, color = OnSurfaceVariant)
                        Text(order.storeName, fontSize = 11.sp, color = Muted)
                        Spacer(Modifier.height(8.dp))
                        HorizontalDivider(color = Border)
                        Spacer(Modifier.height(8.dp))
                        Text("Items", fontWeight = FontWeight.Bold, color = OnSurface)
                        MockData.cart.forEach { item ->
                            Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("${item.product.name} x${item.qty}", fontSize = 12.sp, color = OnSurfaceVariant)
                                Text("₱${(item.product.price * item.qty).toInt()}", fontSize = 12.sp, color = OnSurface)
                            }
                        }
                        HorizontalDivider(Modifier.padding(vertical = 8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", fontWeight = FontWeight.Bold, color = OnSurface)
                            Text("₱${order.total.toInt()}", fontWeight = FontWeight.ExtraBold, color = Leaf)
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SecondaryButton("Track", { navController.navigate(Screen.OrderTracking.createRoute(order.id)) }, Modifier.weight(1f))
                    SecondaryButton("Rate", { navController.navigate(Screen.RateOrder.createRoute(order.id)) }, Modifier.weight(1f))
                }
            }
            item { Spacer(Modifier.height(8.dp)) }
            item { SecondaryButton("Reorder", { }, Modifier.fillMaxWidth()) }
        }
    }
}

@Composable
fun RateOrderScreen(navController: NavController, orderId: String) {
    var rating by remember { mutableStateOf(0) }
    var review by remember { mutableStateOf("") }
    Scaffold(topBar = { PalengkeTopBar("Rate order", onBack = { navController.popBackStack() }) }) { padding ->
        Column(Modifier.fillMaxSize().background(Background).padding(padding).padding(16.dp)) {
            Text("How was your order?", fontWeight = FontWeight.Bold, color = OnSurface)
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                (1..5).forEach { i ->
                    IconButton(onClick = { rating = i }) {
                        Icon(if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder, contentDescription = null, tint = Warning, modifier = Modifier.size(40.dp))
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = review, onValueChange = { review = it }, label = { Text("Write a review (optional)") }, modifier = Modifier.fillMaxWidth().height(120.dp), shape = RoundedCornerShape(12.dp))
            Spacer(Modifier.height(24.dp))
            PrimaryButton("Submit review", { navController.popBackStack() }, Modifier.fillMaxWidth())
        }
    }
}

fun handleBottomNav2(navController: NavController, route: String) {
    val target = when (route) {
        "home" -> Screen.Home.route
        "search" -> Screen.SearchResults.createRoute("")
        "cart" -> Screen.Cart.route
        "orders" -> Screen.OrderHistory.route
        "profile" -> Screen.Profile.route
        else -> Screen.Home.route
    }
    navController.navigate(target) { launchSingleTop = true }
}