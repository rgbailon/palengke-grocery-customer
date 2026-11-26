package com.example.palengke_grocery_customer.screens.extras

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.palengke_grocery_customer.data.MockData
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

// ─────────────────────────── F01 Favorites ───────────────────────────

@Composable
fun FavoritesScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Favorites", onBack = { navController.popBackStack() }, actionText = "Edit", onAction = {})
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("6 items", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink)
                Text("  · prices update daily", fontSize = 11.sp, color = Muted)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Filled.Favorite, contentDescription = null, tint = Tomato, modifier = Modifier.size(16.dp))
            }
            Spacer(Modifier.height(10.dp))
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MockData.favorites.chunked(2).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        row.forEach { p ->
                            FavoriteCard(p, Modifier.weight(1f), onAdd = { navController.navigate(Screen.Cart.route) }) {
                                navController.navigate(Screen.ProductDetail.createRoute(p.id))
                            }
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
        BottomNavBar("profile") { navController.navigateBottom(it) }
    }
}

@Composable
private fun FavoriteCard(
    product: com.example.palengke_grocery_customer.model.Product,
    modifier: Modifier,
    onAdd: () -> Unit = {},
    onClick: () -> Unit
) {
    Surface(
        color = Surface, shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
        onClick = onClick,
        modifier = modifier
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(tileColorOf(product.tileColor)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Image(
                    painterResource(product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Remove from favorites",
                    tint = Tomato,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(16.dp)
                )
            }
            Column(Modifier.padding(10.dp)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Ink, maxLines = 1)
                Text(product.sizeLine.split("·").firstOrNull()?.trim() ?: "", fontSize = 10.sp, color = Muted)
                Spacer(Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("₱${product.price}", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink, modifier = Modifier.weight(1f))
                    AddCircle(onAdd, 28.dp)
                }
            }
        }
    }
}

// ─────────────────────────── F02 Chat with support ───────────────────────────

@Composable
fun ChatScreen(navController: NavController) {
    var input by remember { mutableStateOf("") }
    val messages = remember { MockData.chat.toMutableStateList() }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Support", onBack = { navController.popBackStack() }, actionText = "Call", onAction = {})
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Surface(
                color = Surface, shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    AvatarCircle("MC", TileGreen, Leaf, 40.dp, 12)
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Palengke Support", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(6.dp).clip(CircleShape).background(Fresh))
                            Spacer(Modifier.width(5.dp))
                            Text("Online · replies in ~2 min", fontSize = 10.sp, color = Muted)
                        }
                    }
                    Icon(Icons.Outlined.Security, contentDescription = null, tint = Leaf, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(Modifier.weight(1f), color = Border)
                Text("  Today  ", fontSize = 9.5.sp, color = Muted)
                HorizontalDivider(Modifier.weight(1f), color = Border)
            }
            Spacer(Modifier.height(12.dp))
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                messages.forEach { m ->
                    if (m.fromMe) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Surface(color = Leaf, shape = RoundedCornerShape(16.dp, 4.dp, 16.dp, 16.dp)) {
                                Text(
                                    m.text, color = Surface, fontSize = 12.sp, lineHeight = 17.sp,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                                )
                            }
                        }
                    } else {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                            Surface(
                                color = Surface,
                                shape = RoundedCornerShape(4.dp, 16.dp, 16.dp, 16.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Border)
                            ) {
                                Text(
                                    m.text, color = Ink, fontSize = 12.sp, lineHeight = 17.sp,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                                )
                            }
                        }
                    }
                }
                // Rider proximity system card
                Surface(
                    color = GreenTint, shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.LocalShipping, contentDescription = null, tint = Leaf, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Rodel is 1.2 km away", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = LeafDeep)
                            Text("Arriving in about 18 minutes", fontSize = 10.5.sp, color = OnSurfaceVariant)
                        }
                        Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Leaf, modifier = Modifier.size(16.dp))
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                "Quick replies",
                fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Muted,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Track my order", "Refund request", "Talk to a rider").forEach { q ->
                    PillChip(q, selected = false) {
                        messages.add(
                            com.example.palengke_grocery_customer.model.ChatMessage(
                                "q${messages.size}", q, true, "now"
                            )
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
        }
        // input bar
        Surface(color = Surface, shadowElevation = 14.dp) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = Background,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                    modifier = Modifier.weight(1f).height(44.dp)
                ) {
                    Row(Modifier.padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                        androidx.compose.foundation.text.BasicTextField(
                            value = input,
                            onValueChange = { input = it },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontFamily = Nunito, fontSize = 12.sp, color = Ink
                            ),
                            cursorBrush = androidx.compose.ui.graphics.Brush.verticalGradient(listOf(Leaf, Leaf)),
                            modifier = Modifier.weight(1f),
                            decorationBox = { inner ->
                                Box {
                                    if (input.isEmpty()) Text("Type a message…", fontSize = 12.sp, color = Muted)
                                    inner()
                                }
                            }
                        )
                    }
                }
                Spacer(Modifier.width(10.dp))
                Icon(
                    Icons.Filled.Add, contentDescription = null,
                    tint = Muted, modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(10.dp))
                Surface(
                    shape = CircleShape, color = Leaf,
                    onClick = {
                        if (input.isNotBlank()) {
                            messages.add(
                                com.example.palengke_grocery_customer.model.ChatMessage(
                                    "m${messages.size}", input, true, "now"
                                )
                            )
                            input = ""
                        }
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Surface, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}

// ─────────────────────────── F03 Help & FAQ ───────────────────────────

@Composable
fun HelpFaqScreen(navController: NavController) {
    var expanded by remember { mutableStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Help & support", onBack = { navController.popBackStack() })
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
                    Text("Search help articles", fontSize = 12.sp, color = Muted)
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HelpTile(Icons.Outlined.LocalShipping, TileGreen, "Track order", Modifier.weight(1f)) {
                    navController.navigate(Screen.OrderTracking.createRoute("2048"))
                }
                HelpTile(Icons.Outlined.Autorenew, TileAmber, "Refunds", Modifier.weight(1f)) { }
                HelpTile(Icons.Outlined.AccountBalanceWallet, TileBlue, "Payments", Modifier.weight(1f)) {
                    navController.navigate(Screen.PaymentMethods.route)
                }
            }
            Spacer(Modifier.height(8.dp))
            SectionLabel("Frequently asked questions")
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MockData.faqs.forEachIndexed { i, faq ->
                    Surface(
                        color = Surface, shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                        onClick = { expanded = if (expanded == i) -1 else i }
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(faq.q, fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Ink, modifier = Modifier.weight(1f))
                                Icon(
                                    if (expanded == i) Icons.Outlined.Remove else Icons.Outlined.Add,
                                    contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp)
                                )
                            }
                            if (expanded == i) {
                                Spacer(Modifier.height(6.dp))
                                Text(faq.a, fontSize = 11.sp, color = OnSurfaceVariant, lineHeight = 16.sp)
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            SectionLabel("Still need help?")
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text("Our team is online 7 AM – 9 PM daily.", fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Ink)
                    Text("Average reply time: 2 minutes.", fontSize = 10.5.sp, color = Muted)
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlineButton("Call us", { }, Modifier.weight(1f).height(46.dp))
                        PrimaryButton("Chat now", { navController.navigate(Screen.Chat.route) }, Modifier.weight(1f).height(46.dp))
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "support@palengke.ph · 0917 000 1234",
                fontSize = 10.5.sp, color = Muted,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun HelpTile(icon: androidx.compose.ui.graphics.vector.ImageVector, tile: Color, label: String, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        color = Surface, shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            Modifier.padding(vertical = 14.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconTileCircle(icon, tile, Leaf, 40.dp, 19.dp)
            Spacer(Modifier.height(8.dp))
            Text(label, fontSize = 10.5.sp, fontWeight = FontWeight.SemiBold, color = Ink)
        }
    }
}

// ─────────────────────────── F04 Settings ───────────────────────────

@Composable
fun SettingsScreen(navController: NavController) {
    var orderUpdates by remember { mutableStateOf(true) }
    var promoAlerts by remember { mutableStateOf(false) }
    var chatMessages by remember { mutableStateOf(true) }
    var appLock by remember { mutableStateOf(true) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Settings", onBack = { navController.popBackStack() })
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            SectionLabel("Notifications")
            SettingsCard {
                ToggleRow("Order updates", orderUpdates, { orderUpdates = it })
                SettingsDivider()
                ToggleRow("Promo alerts", promoAlerts, { promoAlerts = it })
                SettingsDivider()
                ToggleRow("Chat messages", chatMessages, { chatMessages = it })
            }
            SectionLabel("Preferences")
            SettingsCard {
                SettingsLink("Language", "English") { }
                SettingsDivider()
                SettingsLink("Theme", "Light") { }
            }
            SectionLabel("Security")
            SettingsCard {
                ToggleRow("App lock", appLock, { appLock = it })
                SettingsDivider()
                SettingsLink("Change PIN", null) { }
            }
            SectionLabel("About")
            SettingsCard {
                SettingsLink("Terms & privacy", null) { }
                SettingsDivider()
                SettingsLink("App version", "1.0.0", chevron = false) { }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "PALENGKE · made for Irosin, Sorsogon",
                fontSize = 9.5.sp, color = Muted,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        color = Surface, shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Column(content = content)
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(color = Border, modifier = Modifier.padding(start = 14.dp))
}

@Composable
private fun SettingsLink(label: String, value: String?, chevron: Boolean = true, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Ink, modifier = Modifier.weight(1f))
        if (value != null) {
            Text(value, fontSize = 12.sp, color = Muted)
        }
        if (chevron) {
            Spacer(Modifier.width(6.dp))
            Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
        }
    }
}
