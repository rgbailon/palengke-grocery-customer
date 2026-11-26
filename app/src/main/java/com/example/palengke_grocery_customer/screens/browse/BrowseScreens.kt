package com.example.palengke_grocery_customer.screens.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.clip
import com.example.palengke_grocery_customer.screens.home.StoreCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
fun BrowseCategoryScreen(navController: NavController, categoryId: String) {
    val cat = MockData.categories.find { it.id == categoryId }
    Scaffold(topBar = { PalengkeTopBar(cat?.name ?: "Category", onBack = { navController.popBackStack() }) }) { padding ->
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(MockData.products.filter { it.category == cat?.name }) { p ->
                ProductGridCard(p, navController)
            }
        }
    }
}

@Composable
fun ProductGridCard(product: Product, navController: NavController) {
    Card(modifier = Modifier.clickable { navController.navigate(Screen.ProductDetail.createRoute(product.id)) }, shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Surface), elevation = CardDefaults.cardElevation(2.dp)) {
        Column {
            AsyncImage(model = product.imageUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
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
fun SearchResultsScreen(navController: NavController, query: String) {
    var q by remember { mutableStateOf(query) }
    Scaffold(topBar = { PalengkeTopBar("Search", onBack = { navController.popBackStack() }) }) { padding ->
        Column(Modifier.fillMaxSize().background(Background).padding(padding)) {
            OutlinedTextField(value = q, onValueChange = { q = it }, modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(12.dp), placeholder = { Text("Search products...") }, leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }, trailingIcon = { IconButton(onClick = { navController.navigate(Screen.SearchFilters.route) }) { Icon(Icons.Default.Tune, contentDescription = null) } })
            LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 16.dp)) {
                items(MockData.products.filter { q.isBlank() || it.name.contains(q, true) }) { p ->
                    Row(Modifier.fillMaxWidth().clickable { navController.navigate(Screen.ProductDetail.createRoute(p.id)) }.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(model = p.imageUrl, contentDescription = null, modifier = Modifier.size(64.dp).clip(RoundedCornerShape(10.dp)), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(p.name, fontWeight = FontWeight.SemiBold, color = OnSurface)
                            Text(p.storeName, fontSize = 11.sp, color = OnSurfaceVariant)
                            Text("₱${p.price.toInt()} / ${p.unit}", color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        IconButton(onClick = { }) { Icon(Icons.Default.AddShoppingCart, contentDescription = null, tint = Leaf) }
                    }
                    HorizontalDivider(color = Border)
                }
            }
        }
    }
}

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun SearchFiltersScreen(navController: NavController) {
    var minPrice by remember { mutableStateOf("") }
    var maxPrice by remember { mutableStateOf("") }
    var selectedCat by remember { mutableStateOf(setOf<String>()) }
    Scaffold(topBar = { PalengkeTopBar("Filters", onBack = { navController.popBackStack() }) }) { padding ->
        Column(Modifier.fillMaxSize().background(Background).padding(padding).padding(16.dp)) {
            Text("Category", fontWeight = FontWeight.Bold, color = OnSurface)
            Spacer(Modifier.height(8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MockData.categories.forEach { cat ->
                    FilterChip(selected = selectedCat.contains(cat.id), onClick = { selectedCat = if (selectedCat.contains(cat.id)) selectedCat - cat.id else selectedCat + cat.id }, label = { Text(cat.name) })
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("Price range", fontWeight = FontWeight.Bold, color = OnSurface)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = minPrice, onValueChange = { minPrice = it }, label = { Text("Min") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp))
                OutlinedTextField(value = maxPrice, onValueChange = { maxPrice = it }, label = { Text("Max") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp))
            }
            Spacer(Modifier.height(16.dp))
            Text("Sort by", fontWeight = FontWeight.Bold, color = OnSurface)
            Spacer(Modifier.height(8.dp))
            listOf("Relevance", "Price: Low to High", "Price: High to Low", "Rating").forEach { opt ->
                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = opt == "Relevance", onClick = { })
                    Text(opt, color = OnSurface)
                }
            }
            Spacer(Modifier.weight(1f))
            PrimaryButton("Apply filters", { navController.popBackStack() }, Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun AllCategoriesScreen(navController: NavController) {
    Scaffold(topBar = { PalengkeTopBar("All categories", onBack = { navController.popBackStack() }) }) { padding ->
        LazyVerticalGrid(columns = GridCells.Fixed(4), modifier = Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(MockData.categories) { cat ->
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { navController.navigate(Screen.BrowseCategory.createRoute(cat.id)) }) {
                    Box(Modifier.size(60.dp).background(Color(cat.colorHex), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                        Text(cat.emoji, fontSize = 26.sp)
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(cat.name, fontSize = 10.sp, color = OnSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun NearbyStallsScreen(navController: NavController) {
    Scaffold(topBar = { PalengkeTopBar("Nearby stalls", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            items(MockData.stores) { store ->
                StoreCard(store, navController, Modifier.fillMaxWidth().padding(vertical = 6.dp))
            }
        }
    }
}

@Composable
fun StoreScreen(navController: NavController, storeId: String) {
    val store = MockData.stores.find { it.id == storeId } ?: MockData.stores[0]
    Scaffold(topBar = { PalengkeTopBar(store.name, onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding)) {
            item {
                AsyncImage(model = store.imageUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(160.dp), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
            }
            item {
                Column(Modifier.padding(16.dp)) {
                    Text(store.name, style = MaterialTheme.typography.headlineSmall, color = OnSurface)
                    Text("${store.distance} away · ⭐ ${store.rating}", color = OnSurfaceVariant, fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        store.tags.forEach { tag ->
                            Surface(color = Color(0xFFEAF5EE), shape = RoundedCornerShape(6.dp)) {
                                Text(tag, fontSize = 10.sp, color = Leaf, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }
                    }
                }
            }
            item { SectionHeader("Products") }
            items(MockData.products.filter { it.storeName == store.name }) { p ->
                Row(Modifier.fillMaxWidth().clickable { navController.navigate(Screen.ProductDetail.createRoute(p.id)) }.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model = p.imageUrl, contentDescription = null, modifier = Modifier.size(56.dp).clip(RoundedCornerShape(10.dp)), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(p.name, fontWeight = FontWeight.SemiBold, color = OnSurface)
                        Text("₱${p.price.toInt()} / ${p.unit}", color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    IconButton(onClick = { }) { Icon(Icons.Default.AddShoppingCart, contentDescription = null, tint = Leaf) }
                }
                HorizontalDivider(color = Border)
            }
        }
    }
}

@Composable
fun ProductDetailScreen(navController: NavController, productId: String) {
    val product = MockData.products.find { it.id == productId } ?: MockData.products[0]
    var qty by remember { mutableStateOf(1) }
    Scaffold(
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (qty > 1) qty-- }) { Icon(Icons.Default.Remove, contentDescription = null) }
                        Text(qty.toString(), fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
                        IconButton(onClick = { qty++ }) { Icon(Icons.Default.Add, contentDescription = null) }
                    }
                    Spacer(Modifier.width(12.dp))
                    PrimaryButton("Add to cart", { navController.navigate(Screen.Cart.route) }, Modifier.weight(1f))
                }
            }
        }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding)) {
            item {
                AsyncImage(model = product.imageUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(260.dp), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
            }
            item {
                Column(Modifier.padding(16.dp)) {
                    Text(product.name, style = MaterialTheme.typography.headlineSmall, color = OnSurface)
                    Spacer(Modifier.height(4.dp))
                    Text("₱${product.price.toInt()} / ${product.unit}", color = Leaf, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⭐ ${product.rating}", color = Warning, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.width(12.dp))
                        Text(product.storeName, color = OnSurfaceVariant, fontSize = 12.sp)
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Description", fontWeight = FontWeight.Bold, color = OnSurface)
                    Text("Fresh and quality ${product.name.lowercase()} sourced from ${product.storeName}. Carefully packed and delivered to your doorstep.", color = OnSurfaceVariant, fontSize = 13.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("In stock", color = Fresh, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                }
            }
        }
    }
}