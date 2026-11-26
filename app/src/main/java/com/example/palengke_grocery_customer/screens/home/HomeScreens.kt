package com.example.palengke_grocery_customer.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.palengke_grocery_customer.data.MockData
import com.example.palengke_grocery_customer.model.Product
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavBar("home") { route -> handleBottomNav(navController, route) } }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding)) {
            item {
                // Header
                Box(Modifier.fillMaxWidth().background(Leaf).padding(16.dp)) {
                    Column {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("Good morning,", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                                Text("Juan Dela Cruz", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                            IconButton(onClick = { navController.navigate(Screen.Notifications.route) }) {
                                Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White)
                            }
                            IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        // Search bar
                        Surface(color = Color.White, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().clickable { navController.navigate(Screen.SearchResults.createRoute("")) }) {
                            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Search, contentDescription = null, tint = Muted)
                                Spacer(Modifier.width(8.dp))
                                Text("Search products, stores...", color = Muted, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item { SectionHeader("Categories", "See all") { navController.navigate(Screen.AllCategories.route) } }
            item {
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(MockData.categories) { cat ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { navController.navigate(Screen.BrowseCategory.createRoute(cat.id)) }) {
                            Box(Modifier.size(56.dp).background(Color(cat.colorHex), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                                Text(cat.emoji, fontSize = 24.sp)
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(cat.name, fontSize = 10.sp, color = OnSurfaceVariant)
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(8.dp)) }
            item { SectionHeader("Featured products", "See all") { } }
            item {
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(MockData.products.take(6)) { p -> ProductCard(p, navController) }
                }
            }
            item { Spacer(Modifier.height(8.dp)) }
            item { SectionHeader("Nearby stalls", "See all") { navController.navigate(Screen.NearbyStalls.route) } }
            items(MockData.stores) { store ->
                StoreCard(store, navController, Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun ProductCard(product: Product, navController: NavController, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.width(150.dp).clickable { navController.navigate(Screen.ProductDetail.createRoute(product.id)) },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(model = product.imageUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(110.dp).clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
            Column(Modifier.padding(10.dp)) {
                Text(product.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 2, overflow = TextOverflow.Ellipsis, color = OnSurface)
                Spacer(Modifier.height(4.dp))
                Text("₱${product.price.toInt()}", color = Leaf, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(product.unit, fontSize = 10.sp, color = Muted)
            }
        }
    }
}

@Composable
fun StoreCard(store: com.example.palengke_grocery_customer.model.Store, navController: NavController, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().clickable { navController.navigate(Screen.Store.createRoute(store.id)) },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = store.imageUrl, contentDescription = null, modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(store.name, fontWeight = FontWeight.SemiBold, color = OnSurface)
                Text("${store.distance} away", fontSize = 11.sp, color = OnSurfaceVariant)
                Text("⭐ ${store.rating}", fontSize = 11.sp, color = Warning)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    store.tags.forEach { tag ->
                        Surface(color = Color(0xFFEAF5EE), shape = RoundedCornerShape(6.dp)) {
                            Text(tag, fontSize = 9.sp, color = Leaf, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}

fun handleBottomNav(navController: NavController, route: String) {
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