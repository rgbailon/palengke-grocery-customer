package com.example.palengke_grocery_customer.screens.profile

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
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = { PalengkeTopBar("Profile") },
        bottomBar = { BottomNavBar("profile") { route -> handleBottomNav3(navController, route) } }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            item {
                Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Surface)) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(64.dp).background(Leaf, RoundedCornerShape(32.dp)), contentAlignment = Alignment.Center) {
                            Text("JD", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Juan Dela Cruz", fontWeight = FontWeight.Bold, color = OnSurface, fontSize = 16.sp)
                            Text("juan@email.com", fontSize = 12.sp, color = OnSurfaceVariant)
                            Text("+63 912 345 6789", fontSize = 12.sp, color = Muted)
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                val menu = listOf(
                    Triple(Icons.Default.LocationOn, "Address book", Screen.AddressBook.route),
                    Triple(Icons.Default.LocalOffer, "Promos & vouchers", Screen.Promos.route),
                    Triple(Icons.Default.Notifications, "Notifications", Screen.Notifications.route),
                    Triple(Icons.Default.Favorite, "Favorites", Screen.Favorites.route),
                    Triple(Icons.Default.Chat, "Chat with support", Screen.Chat.route),
                    Triple(Icons.Default.Help, "Help & FAQ", Screen.HelpFaq.route),
                    Triple(Icons.Default.Settings, "Settings", Screen.Settings.route)
                )
                Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Surface)) {
                    menu.forEach { (icon, label, route) ->
                        Row(Modifier.fillMaxWidth().clickable { navController.navigate(route) }.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(icon, contentDescription = null, tint = Leaf)
                            Spacer(Modifier.width(12.dp))
                            Text(label, color = OnSurface, modifier = Modifier.weight(1f))
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted)
                        }
                        HorizontalDivider(color = Border)
                    }
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item { SecondaryButton("Log out", { navController.navigate(Screen.Splash.route) { popUpTo(0) } }, Modifier.fillMaxWidth()) }
        }
    }
}

@Composable
fun AddressBookScreen(navController: NavController) {
    Scaffold(topBar = { PalengkeTopBar("Address book", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            items(MockData.addresses) { addr ->
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Surface), modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Leaf)
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(addr.label, fontWeight = FontWeight.SemiBold, color = OnSurface)
                                if (addr.isDefault) { Spacer(Modifier.width(6.dp)); StatusChip("Default", Fresh) }
                            }
                            Text(addr.full, fontSize = 11.sp, color = OnSurfaceVariant)
                        }
                        IconButton(onClick = { }) { Icon(Icons.Default.Edit, contentDescription = null, tint = Muted) }
                    }
                }
            }
            item { Spacer(Modifier.height(8.dp)) }
            item { SecondaryButton("Add new address", { }, Modifier.fillMaxWidth()) }
        }
    }
}

@Composable
fun PromosScreen(navController: NavController) {
    Scaffold(topBar = { PalengkeTopBar("Promos & vouchers", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            items(MockData.promos) { promo ->
                Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color(promo.colorHex)), modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(promo.title, fontWeight = FontWeight.Bold, color = OnSurface)
                            Text(promo.subtitle, fontSize = 11.sp, color = OnSurfaceVariant)
                            Spacer(Modifier.height(8.dp))
                            Surface(color = Surface, shape = RoundedCornerShape(6.dp)) {
                                Text(promo.code, color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }
                        Icon(Icons.Default.LocalOffer, contentDescription = null, tint = Leaf, modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen(navController: NavController) {
    Scaffold(topBar = { PalengkeTopBar("Notifications", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            items(MockData.notifications) { n ->
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = if (n.unread) Color(0xFFEAF5EE) else Surface), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = Leaf)
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(n.title, fontWeight = FontWeight.SemiBold, color = OnSurface)
                            Text(n.body, fontSize = 12.sp, color = OnSurfaceVariant)
                            Text(n.time, fontSize = 10.sp, color = Muted)
                        }
                        if (n.unread) Box(Modifier.size(8.dp).background(Leaf, RoundedCornerShape(4.dp)))
                    }
                }
            }
        }
    }
}

fun handleBottomNav3(navController: NavController, route: String) {
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