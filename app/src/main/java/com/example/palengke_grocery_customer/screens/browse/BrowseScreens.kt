package com.example.palengke_grocery_customer.screens.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.palengke_grocery_customer.data.MockData
import com.example.palengke_grocery_customer.model.Product
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

private val browseChips = listOf("All", "Noodles", "Canned", "Rice", "Coffee")

@Composable
fun BrowseCategoryScreen(navController: NavController, categoryId: String) {
    var chip by remember { mutableStateOf(categoryId) }
    val items = if (chip == "All") MockData.products else MockData.byCategory(chip)
    val qty = remember { mutableStateMapOf<String, Int>() }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar(
            title = categoryId,
            onBack = { navController.popBackStack() },
            actionText = "Cart",
            onAction = { navController.navigate(Screen.Cart.route) }
        )
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                browseChips.forEach { c ->
                    PillChip(c, selected = chip == c, onClick = { chip = c })
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (chip == "All") "124 items" else "${MockData.categories.firstOrNull { it.name == chip }?.itemCount ?: items.size} items",
                    fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink
                )
                Text(" · Popular", fontSize = 11.sp, color = Muted)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Outlined.Tune, contentDescription = null, tint = Ink, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text("Sort", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Ink)
            }
            Spacer(Modifier.height(10.dp))
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items.forEach { p ->
                    key(p.id) {
                        ProductRow(
                            product = p,
                            qtyInCart = qty[p.id] ?: 0,
                            onOpen = { navController.navigate(Screen.ProductDetail.createRoute(p.id)) },
                            onAdd = { qty[p.id] = (qty[p.id] ?: 0) + 1 },
                            onMinus = {
                                val cur = qty[p.id] ?: 0
                                if (cur > 1) qty[p.id] = cur - 1 else qty.remove(p.id)
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            OutlineButton(
                "Load more items",
                {},
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
        }
        BottomNavBar("search") { navController.navigateBottom(it) }
    }
}

// ─────────────────────────── B03 Search results ───────────────────────────

@Composable
fun SearchResultsScreen(navController: NavController, query: String) {
    var text by remember { mutableStateOf(query) }
    val qty = remember { mutableStateMapOf<String, Int>() }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Ink)
            }
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.weight(1f).height(44.dp)
            ) {
                Row(Modifier.padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Search, contentDescription = null, tint = Ink, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    androidx.compose.foundation.text.BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = Nunito, fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp, color = Ink
                        ),
                        cursorBrush = androidx.compose.ui.graphics.Brush.verticalGradient(listOf(Leaf, Leaf)),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Leaf,
                onClick = { navController.navigate(Screen.SearchFilters.route) },
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.Tune, contentDescription = "Filters", tint = Surface, modifier = Modifier.size(17.dp))
                }
            }
        }
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Row(Modifier.padding(horizontal = 16.dp)) {
                Text("8 results for ", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink)
                Text("\u201C$text\u201D", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Muted)
            }
            Spacer(Modifier.height(10.dp))
            Row(
                Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PillChip("Filters · 2", selected = true, onClick = { navController.navigate(Screen.SearchFilters.route) })
                PillChip("Sort: Popular", selected = false)
            }
            Spacer(Modifier.height(12.dp))
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MockData.searchResults.forEach { p ->
                    key(p.id) {
                        ProductRow(
                            product = p,
                            qtyInCart = qty[p.id] ?: 0,
                            onOpen = { navController.navigate(Screen.ProductDetail.createRoute(p.id)) },
                            onAdd = { qty[p.id] = (qty[p.id] ?: 0) + 1 },
                            onMinus = {
                                val cur = qty[p.id] ?: 0
                                if (cur > 1) qty[p.id] = cur - 1 else qty.remove(p.id)
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "Showing 4 of 8 results",
                fontSize = 10.5.sp, color = Muted,
                modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
        }
        BottomNavBar("search") { navController.navigateBottom(it) }
    }
}

// ─────────────────────────── B04 Search filters ───────────────────────────

@Composable
fun SearchFiltersScreen(navController: NavController) {
    var sortBy by remember { mutableStateOf("Popular") }
    var inStock by remember { mutableStateOf(true) }
    var onPromo by remember { mutableStateOf(false) }
    var range by remember { mutableStateOf(50f..380f) }
    val cats = listOf("Rice", "Canned", "Noodles", "Coffee", "Snacks", "Dairy")
    val selected = remember { mutableStateMapOf("Noodles" to true, "Canned" to true) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Filters", onBack = { navController.popBackStack() }, actionText = "Reset", onAction = {
            sortBy = "Popular"; inStock = true; onPromo = false; range = 50f..380f; selected.clear()
        })
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            SectionLabel("Sort by")
            Row(
                Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Popular", "Price ↑", "Price ↓", "Newest").forEach { s ->
                    PillChip(s, sortBy == s) { sortBy = s }
                }
            }
            Spacer(Modifier.height(8.dp))
            SectionLabel("Category")
            Column(Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                cats.chunked(2).forEach { row ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        row.forEach { c ->
                            Box(Modifier.weight(1f)) {
                                CheckRow(c, selected[c] == true, {
                                    selected[c] = !(selected[c] ?: false)
                                }, labelSp = 13)
                            }
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Price range", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink, modifier = Modifier.weight(1f))
                Text("₱${range.start.toInt()} – ₱${range.endInclusive.toInt()}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Leaf)
            }
            androidx.compose.material3.RangeSlider(
                value = range,
                onValueChange = { range = it },
                valueRange = 0f..500f,
                colors = SliderDefaults.colors(
                    thumbColor = Leaf,
                    activeTrackColor = Leaf,
                    inactiveTrackColor = Border,
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = Color.Transparent
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Text("₱0", fontSize = 10.sp, color = Muted)
                Spacer(Modifier.weight(1f))
                Text("₱500", fontSize = 10.sp, color = Muted)
            }
            Spacer(Modifier.height(14.dp))
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column {
                    Column(Modifier.padding(horizontal = 14.dp, vertical = 6.dp)) {
                        ToggleRow("In stock only", inStock, { inStock = it })
                        Text(
                            "Hide items sold out today",
                            fontSize = 10.sp, color = Muted,
                            modifier = Modifier.padding(start = 14.dp).offset(y = (-4).dp)
                        )
                        HorizontalDivider(color = Border)
                        ToggleRow("On promo", onPromo, { onPromo = it })
                        Text(
                            "Show discounted items first",
                            fontSize = 10.sp, color = Muted,
                            modifier = Modifier.padding(start = 14.dp).offset(y = (-4).dp)
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(Surface)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlineButton("Reset", {
                sortBy = "Popular"; inStock = true; onPromo = false; range = 50f..380f; selected.clear()
            }, Modifier.weight(1f))
            PrimaryButton("Show 24 results", { navController.popBackStack() }, Modifier.weight(1f))
        }
    }
}

// ─────────────────────────── B05 All categories ───────────────────────────

@Composable
fun AllCategoriesScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("All categories", onBack = { navController.popBackStack() })
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(46.dp)
            ) {
                Row(Modifier.padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Search, contentDescription = null, tint = Muted, modifier = Modifier.size(17.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("Search categories", fontSize = 12.sp, color = Muted)
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(Modifier.padding(horizontal = 16.dp)) {
                Text("8 categories", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink)
                Text("  ·  124 items", fontSize = 11.sp, color = Muted)
            }
            Spacer(Modifier.height(10.dp))
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MockData.categories.chunked(2).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        row.forEach { cat ->
                            Surface(
                                color = Surface, shape = RoundedCornerShape(14.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                                onClick = { navController.navigate(Screen.BrowseCategory.createRoute(cat.name)) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconTileCircle(categoryIcon(cat.iconKey), tileColorOf(cat.tileColor), Leaf, 42.dp, 20.dp)
                                    Spacer(Modifier.width(10.dp))
                                    Column(Modifier.weight(1f)) {
                                        Text(cat.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                                        Text("${cat.itemCount} items", fontSize = 10.5.sp, color = Muted)
                                    }
                                    Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
        BottomNavBar("home") { navController.navigateBottom(it) }
    }
}

// ─────────────────────────── B06 Nearby stalls ───────────────────────────

@Composable
fun NearbyStallsScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Nearby stalls", onBack = { navController.popBackStack() }, actionText = "Map", onAction = {})
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Row(Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("3 stalls open now", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink)
                Text("  · inside Irosin market", fontSize = 11.sp, color = Muted)
            }
            Spacer(Modifier.height(10.dp))
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MockData.stores.forEach { store ->
                    Surface(
                        color = Surface, shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Border)
                    ) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            IconTileCircle(
                                if (store.verified) Icons.Outlined.Storefront
                                else if (store.id == "s2") Icons.Filled.ShoppingBasket
                                else Icons.Outlined.Inventory2,
                                tileColorOf(store.tileColor), Leaf
                            )
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(store.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                                    Spacer(Modifier.width(8.dp))
                                    StatusChip("Open", GreenTint, Leaf)
                                }
                                Text(store.sub, fontSize = 10.5.sp, color = Muted)
                                Spacer(Modifier.height(3.dp))
                                RatingRow(store.rating, store.ratingCount)
                                Spacer(Modifier.height(3.dp))
                                Text("${store.distance} · ${store.eta}", fontSize = 10.5.sp, color = Muted)
                            }
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    "View",
                                    color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp,
                                    modifier = Modifier.clickable {
                                        navController.navigate(Screen.Store.createRoute(store.id))
                                    }
                                )
                                Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Leaf, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            InfoCard(
                icon = Icons.Outlined.LocationOn,
                iconTint = Grain,
                bg = Cream,
                title = "All stalls are inside Irosin market",
                lines = listOf(
                    "Open daily 6 AM – 6 PM · ₱20 delivery",
                    "Order from one stall per checkout."
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(16.dp))
        }
        BottomNavBar("home") { navController.navigateBottom(it) }
    }
}

// ─────────────────────────── B07 Store page ───────────────────────────

@Composable
fun StoreScreen(navController: NavController, storeId: String) {
    val store = MockData.stores.first { it.id == storeId }
    var chip by remember { mutableStateOf("All") }
    val storeProducts = listOf(MockData.byId("p_pancit"), MockData.byId("p_rice"), MockData.byId("p_tuna"))
    val qty = remember { mutableStateMapOf<String, Int>() }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Green header
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(158.dp)
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            listOf(Color(0xFF0E7A46), Leaf)
                        )
                    )
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(shape = CircleShape, color = Surface.copy(alpha = 0.16f), onClick = { navController.popBackStack() }, modifier = Modifier.size(36.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Surface, modifier = Modifier.size(18.dp))
                        }
                    }
                    Surface(shape = CircleShape, color = Surface.copy(alpha = 0.16f), onClick = {}, modifier = Modifier.size(36.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Filled.FavoriteBorder, contentDescription = null, tint = Surface, modifier = Modifier.size(18.dp))
                        }
                    }
                }
                Column(Modifier.padding(horizontal = 18.dp).offset(y = 30.dp)) {
                    Surface(color = Surface, shape = RoundedCornerShape(999.dp)) {
                        Text(
                            "VERIFIED STALL",
                            color = Leaf, fontSize = 8.5.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.8.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(store.name, color = Surface, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    Text("${store.sub.split("·")[0].trim()} · Poblacion, Irosin", color = Surface.copy(alpha = 0.72f), fontSize = 11.sp)
                }
            }
            // Stats card overlapping
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-18).dp)
            ) {
                Row(Modifier.padding(vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    StatCell(store.rating.toString() + " ", "★", "312 ratings", Modifier.weight(1f), isRating = true)
                    VerticalDivider()
                    StatCell("24", "", "items", Modifier.weight(1f))
                    VerticalDivider()
                    StatCell("25 min", "", "delivery", Modifier.weight(1f))
                }
            }
            Column(Modifier.offset(y = (-10).dp)) {
                Row(
                    Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Rice", "Canned", "Noodles").forEach { c ->
                        PillChip(c, chip == c) { chip = c }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("24 items", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink)
                    Text(" · Popular", fontSize = 11.sp, color = Muted)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Outlined.Tune, contentDescription = null, tint = Ink, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Sort", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Ink)
                }
                Spacer(Modifier.height(10.dp))
                Column(
                    Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    storeProducts.forEach { p ->
                        key(p.id) {
                            ProductRow(
                                product = p,
                                qtyInCart = qty[p.id] ?: 0,
                                onOpen = { navController.navigate(Screen.ProductDetail.createRoute(p.id)) },
                                onAdd = { qty[p.id] = (qty[p.id] ?: 0) + 1 },
                                onMinus = {
                                    val cur = qty[p.id] ?: 0
                                    if (cur > 1) qty[p.id] = cur - 1 else qty.remove(p.id)
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
        Surface(color = Surface) {
            PrimaryButton(
                "Chat with seller",
                { navController.navigate(Screen.Chat.route) },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
private fun VerticalDivider() {
    Box(Modifier.width(1.dp).height(34.dp).background(Border))
}

@Composable
private fun StatCell(value: String, suffix: String, label: String, modifier: Modifier, isRating: Boolean = false) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isRating) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Ink)
                Icon(Icons.Filled.Star, contentDescription = null, tint = Mango, modifier = Modifier.size(13.dp))
            }
        } else {
            Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Ink)
        }
        Text(label, fontSize = 9.5.sp, color = Muted)
    }
}

// ─────────────────────────── B08 Product detail ───────────────────────────

@Composable
fun ProductDetailScreen(navController: NavController, productId: String) {
    val p = MockData.byId(productId)
    var qty by remember { mutableStateOf(3) }
    var fav by remember { mutableStateOf(false) }
    val displayTitle = if (p.brand != null) "${p.brand} ${p.name}" else p.name

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(tileColorOf(p.tileColor))
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(shape = CircleShape, color = Surface, onClick = { navController.popBackStack() }, modifier = Modifier.size(38.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Ink, modifier = Modifier.size(18.dp))
                        }
                    }
                    Surface(shape = CircleShape, color = Surface, onClick = { fav = !fav }, modifier = Modifier.size(38.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                if (fav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = null,
                                tint = if (fav) Tomato else Ink, modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
                androidx.compose.foundation.Image(
                    painterResource(p.imageRes),
                    contentDescription = p.name,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(180.dp)
                        .padding(10.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )
                Box(Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                    if (p.oldPrice != null) PromoPill("-${((1 - p.price.toFloat() / p.oldPrice) * 100).toInt()}%")
                }
            }
            Column(Modifier.padding(horizontal = 18.dp)) {
                Spacer(Modifier.height(14.dp))
                Text(displayTitle, style = MaterialTheme.typography.headlineLarge, color = Ink)
                Spacer(Modifier.height(4.dp))
                Text(
                    "${p.sizeLine}${if (p.brand != null) " · ${p.brand}" else ""}",
                    fontSize = 11.sp, color = Muted
                )
                Spacer(Modifier.height(8.dp))
                RatingRow(p.rating, p.ratingCount, big = true)
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("₱${p.price}", fontSize = 22.sp, fontWeight = FontWeight.Black, color = Ink)
                    if (p.oldPrice != null) {
                        Text(
                            "₱${p.oldPrice}", fontSize = 12.sp, color = Muted,
                            style = androidx.compose.ui.text.TextStyle(
                                textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                            )
                        )
                        StatusChip("Save ₱${p.oldPrice - p.price}", GreenTint, Leaf)
                    }
                }
                Spacer(Modifier.height(6.dp))
                Text("In stock · ${p.stock} available", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Leaf)
                Spacer(Modifier.height(14.dp))
                HorizontalDivider(color = Border)
                Spacer(Modifier.height(14.dp))
                Surface(
                    color = Surface, shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                    onClick = { navController.navigate(Screen.Store.createRoute("s1")) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconTileCircle(Icons.Outlined.Storefront, TileGreen, Leaf, 40.dp, 19.dp)
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(MockData.primaryStore.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                            Text("Stall 12 · Verified seller", fontSize = 10.5.sp, color = Muted)
                        }
                        Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text("Product details", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Ink)
                Spacer(Modifier.height(6.dp))
                Text(p.details, fontSize = 11.5.sp, color = OnSurfaceVariant, lineHeight = 17.sp)
                Spacer(Modifier.height(20.dp))
            }
        }
        Surface(color = Surface, shadowElevation = 12.dp) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                QtyStepper(qty, { if (qty > 1) qty-- }, { qty++ })
                Spacer(Modifier.width(12.dp))
                PrimaryButton("Add to cart", { navController.navigate(Screen.Cart.route) }, Modifier.weight(1f))
            }
        }
    }
}
