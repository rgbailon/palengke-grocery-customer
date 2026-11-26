package com.example.palengke_grocery_customer.screens.cart

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.palengke_grocery_customer.data.MockData
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

@Composable
fun CartScreen(navController: NavController) {
    val items = MockData.cart
    val subtotal = items.sumOf { it.product.price * it.qty }
    val delivery = 40.0
    val total = subtotal + delivery
    Scaffold(
        topBar = { PalengkeTopBar("My cart", onBack = { navController.popBackStack() }) },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total", fontWeight = FontWeight.Bold, color = OnSurface)
                        Text("₱${total.toInt()}", fontWeight = FontWeight.ExtraBold, color = Leaf, fontSize = 18.sp)
                    }
                    Spacer(Modifier.height(8.dp))
                    PrimaryButton("Proceed to checkout", { navController.navigate(Screen.Checkout.route) }, Modifier.fillMaxWidth())
                }
            }
        }
    ) { padding ->
        if (items.isEmpty()) {
            EmptyState(Icons.Default.ShoppingCart, "Your cart is empty", "Add products to get started", "Shop now") { navController.navigate(Screen.Home.route) }
        } else {
            LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
                items(items) { item ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(model = item.product.imageUrl, contentDescription = null, modifier = Modifier.size(64.dp).clip(RoundedCornerShape(10.dp)), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(item.product.name, fontWeight = FontWeight.SemiBold, color = OnSurface, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text("₱${item.product.price.toInt()} / ${item.product.unit}", color = Leaf, fontSize = 12.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { }) { Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(18.dp)) }
                                Text(item.qty.toString(), fontWeight = FontWeight.Bold)
                                IconButton(onClick = { }) { Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp)) }
                            }
                        }
                        Text("₱${(item.product.price * item.qty).toInt()}", fontWeight = FontWeight.Bold, color = OnSurface)
                    }
                    HorizontalDivider(color = Border)
                }
                item {
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Subtotal", color = OnSurfaceVariant)
                        Text("₱${subtotal.toInt()}", color = OnSurface)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Delivery fee", color = OnSurfaceVariant)
                        Text("₱${delivery.toInt()}", color = OnSurface)
                    }
                }
            }
        }
    }
}

@Composable
fun CheckoutScreen(navController: NavController) {
    var selectedPayment by remember { mutableStateOf("COD") }
    var selectedAddress by remember { mutableStateOf(MockData.addresses[0]) }
    val subtotal = MockData.cart.sumOf { it.product.price * it.qty }
    val delivery = 40.0
    val total = subtotal + delivery
    Scaffold(topBar = { PalengkeTopBar("Checkout", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            item {
                Text("Delivery address", fontWeight = FontWeight.Bold, color = OnSurface)
                Spacer(Modifier.height(8.dp))
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Surface)) {
                    Row(Modifier.padding(12.dp).clickable { navController.navigate(Screen.AddressBook.route) }, verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Leaf)
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text(selectedAddress.label, fontWeight = FontWeight.SemiBold, color = OnSurface)
                            Text(selectedAddress.full, fontSize = 11.sp, color = OnSurfaceVariant)
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted)
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                Text("Payment method", fontWeight = FontWeight.Bold, color = OnSurface)
                Spacer(Modifier.height(8.dp))
                listOf("COD" to "Cash on delivery", "GCASH" to "GCash", "MAYA" to "Maya").forEach { (code, label) ->
                    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = if (selectedPayment == code) Color(0xFFEAF5EE) else Surface), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { selectedPayment = code }) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = selectedPayment == code, onClick = { selectedPayment = code })
                            Text(label, color = OnSurface)
                        }
                    }
                }
                Text("Manage payment methods", color = Leaf, fontSize = 12.sp, modifier = Modifier.clickable { navController.navigate(Screen.PaymentMethods.route) }.padding(top = 8.dp))
            }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                Text("Order summary", fontWeight = FontWeight.Bold, color = OnSurface)
                Spacer(Modifier.height(8.dp))
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Surface)) {
                    Column(Modifier.padding(12.dp)) {
                        MockData.cart.forEach { item ->
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("${item.product.name} x${item.qty}", fontSize = 12.sp, color = OnSurfaceVariant)
                                Text("₱${(item.product.price * item.qty).toInt()}", fontSize = 12.sp, color = OnSurface)
                            }
                        }
                        HorizontalDivider(Modifier.padding(vertical = 8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal", color = OnSurfaceVariant)
                            Text("₱${subtotal.toInt()}", color = OnSurface)
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Delivery", color = OnSurfaceVariant)
                            Text("₱${delivery.toInt()}", color = OnSurface)
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", fontWeight = FontWeight.Bold, color = OnSurface)
                            Text("₱${total.toInt()}", fontWeight = FontWeight.ExtraBold, color = Leaf, fontSize = 16.sp)
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(24.dp)) }
            item { PrimaryButton("Place order", { navController.navigate(Screen.OrderPlaced.route) }, Modifier.fillMaxWidth()) }
        }
    }
}

@Composable
fun OrderPlacedScreen(navController: NavController) {
    Column(Modifier.fillMaxSize().background(Background).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Fresh, modifier = Modifier.size(80.dp))
        Spacer(Modifier.height(16.dp))
        Text("Order placed!", style = MaterialTheme.typography.displaySmall, color = OnSurface)
        Spacer(Modifier.height(8.dp))
        Text("Your order has been confirmed and is being prepared.", color = OnSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Text("Order #ORD-1025", color = Muted, fontSize = 12.sp)
        Spacer(Modifier.height(32.dp))
        PrimaryButton("Track order", { navController.navigate(Screen.OrderTracking.createRoute("ORD-1025")) }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        SecondaryButton("Back to home", { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) { inclusive = true } } }, Modifier.fillMaxWidth())
    }
}

@Composable
fun PaymentMethodsScreen(navController: NavController) {
    Scaffold(topBar = { PalengkeTopBar("Payment methods", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            item {
                listOf(Triple("COD", "Cash on delivery", Icons.Default.Money), Triple("GCASH", "GCash", Icons.Default.AccountBalanceWallet), Triple("MAYA", "Maya", Icons.Default.AccountBalanceWallet)).forEach { (code, label, icon) ->
                    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Surface), modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(icon, contentDescription = null, tint = Leaf)
                            Spacer(Modifier.width(12.dp))
                            Text(label, color = OnSurface, modifier = Modifier.weight(1f))
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Fresh)
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
            item { SecondaryButton("Add new card", { }, Modifier.fillMaxWidth()) }
        }
    }
}