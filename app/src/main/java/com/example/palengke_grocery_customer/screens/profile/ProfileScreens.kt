package com.example.palengke_grocery_customer.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.palengke_grocery_customer.data.MockData
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

// ─────────────────────────── E01 Profile ───────────────────────────

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(208.dp)
                    .background(Brush.verticalGradient(listOf(Color(0xFF0E7A46), Leaf)))
            ) {
                Column(Modifier.statusBarsPadding().fillMaxSize().padding(horizontal = 16.dp)) {
                    Row(
                        Modifier.fillMaxWidth().padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("My profile", color = Surface, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.weight(1f))
                        Icon(Icons.Outlined.Tune, contentDescription = null, tint = Surface, modifier = Modifier.size(18.dp))
                    }
                    Spacer(Modifier.height(14.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(Surface),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("JD", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Leaf)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Juan Dela Cruz", color = Surface, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                            Text("0917 123 4567", color = Surface.copy(alpha = 0.72f), fontSize = 11.sp)
                            Spacer(Modifier.height(5.dp))
                            Surface(shape = RoundedCornerShape(999.dp), color = Surface.copy(alpha = 0.16f)) {
                                Text(
                                    "Edit profile",
                                    color = Surface, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
            // Stats card overlapping the green header
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                shadowElevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-26).dp)
            ) {
                Row(Modifier.padding(vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    StatCell("12", "Orders", Modifier.weight(1f), onClick = { navController.navigate(Screen.OrderHistory.route) })
                    StatDivider()
                    StatCell("8", "Favorites", Modifier.weight(1f), onClick = { navController.navigate(Screen.Favorites.route) })
                    StatDivider()
                    StatCell("3", "Vouchers", Modifier.weight(1f), onClick = { navController.navigate(Screen.Promos.route) })
                }
            }
            Column(Modifier.offset(y = (-14).dp)) {
                SectionLabel("Account")
                Column(
                    Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ListRowCard(Icons.Outlined.Person, TileGreen, "Personal information", "Name, phone, email") { }
                    ListRowCard(Icons.Outlined.LocationOn, TileAmber, "Address book", "2 saved addresses") {
                        navController.navigate(Screen.AddressBook.route)
                    }
                    ListRowCard(Icons.Outlined.Payments, TileBlue, "Payment methods", "Cash on delivery") {
                        navController.navigate(Screen.PaymentMethods.route)
                    }
                }
                SectionLabel("More")
                Column(
                    Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ListRowCard(Icons.Outlined.LocalOffer, TileGreen, "Promos & vouchers", "3 available") {
                        navController.navigate(Screen.Promos.route)
                    }
                    ListRowCard(Icons.Outlined.Notifications, TileAmber, "Notifications", "2 unread") {
                        navController.navigate(Screen.Notifications.route)
                    }
                    ListRowCard(Icons.Outlined.SupportAgent, TileBlue, "Help & support", "Chat with us") {
                        navController.navigate(Screen.HelpFaq.route)
                    }
                }
                Spacer(Modifier.height(14.dp))
                Surface(
                    color = RedTint, shape = RoundedCornerShape(14.dp),
                    onClick = {
                        navController.navigate(Screen.Splash.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Row(
                        Modifier.padding(vertical = 14.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Close, contentDescription = null, tint = Tomato, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Log out", color = Tomato, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
        BottomNavBar("profile") { navController.navigateBottom(it) }
    }
}

@Composable
private fun StatCell(value: String, label: String, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, fontWeight = FontWeight.Black, fontSize = 17.sp, color = Ink)
        Spacer(Modifier.height(2.dp))
        Text(label, fontSize = 10.sp, color = Muted)
    }
}

@Composable
private fun StatDivider() {
    Box(Modifier.width(1.dp).height(36.dp).background(Border))
}

// ─────────────────────────── E02 Address book ───────────────────────────

@Composable
fun AddressBookScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Address book", onBack = { navController.popBackStack() }, actionText = "Add", onAction = {})
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            SectionLabel("Saved addresses")
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MockData.addresses.forEach { addr ->
                    Surface(
                        color = Surface, shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Border)
                    ) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            IconTileCircle(
                                Icons.Outlined.LocationOn,
                                if (addr.isDefault) TileGreen else TileAmber, Leaf
                            )
                            Spacer(Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(addr.label, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                                    if (addr.isDefault) {
                                        Spacer(Modifier.width(8.dp))
                                        StatusChip("Default", GreenTint, Leaf)
                                    }
                                }
                                Text(addr.contact, fontSize = 10.5.sp, color = Muted)
                                addr.lines.forEach { line ->
                                    Text(line, fontSize = 10.5.sp, color = Muted)
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Outlined.Edit, contentDescription = "Edit",
                                    tint = Muted, modifier = Modifier
                                        .size(17.dp)
                                        .clickable { }
                                )
                                Spacer(Modifier.height(10.dp))
                                Icon(
                                    Icons.Outlined.Delete, contentDescription = "Delete",
                                    tint = Muted, modifier = Modifier
                                        .size(17.dp)
                                        .clickable { }
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.Transparent,
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .dashedBorder(Leaf, 14.dp)
            ) {
                Column(
                    Modifier.padding(vertical = 16.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Add, contentDescription = null, tint = Leaf, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Add a new address", color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                    }
                    Spacer(Modifier.height(3.dp))
                    Text("Home, work or a pickup point", fontSize = 10.5.sp, color = Muted)
                }
            }
            Spacer(Modifier.height(16.dp))
            SectionLabel("Delivery area")
            InfoCard(
                icon = Icons.Outlined.LocalShipping,
                iconTint = Grain,
                bg = Cream,
                title = "We deliver within Irosin only",
                lines = listOf(
                    "Barangays Poblacion, San Isidro, San Juan and Buenavista.",
                    "Delivery fee is ₱20 per order."
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlineButton("Set a default address", { }, Modifier.fillMaxWidth().padding(horizontal = 16.dp))
            Spacer(Modifier.height(10.dp))
            Text(
                "Addresses are only shared with your rider",
                fontSize = 10.sp, color = Muted,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

// ─────────────────────────── E03 Promos & vouchers ───────────────────────────

@Composable
fun PromosScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Promos", onBack = { navController.popBackStack() }, actionText = "My vouchers", onAction = {})
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            SectionLabel("Available offers")
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MockData.promos.forEach { promo ->
                    Surface(
                        color = Color(promo.bg), shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Surface(shape = RoundedCornerShape(999.dp), color = Surface.copy(alpha = 0.18f)) {
                                Text(
                                    promo.code,
                                    color = Surface, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.8.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(promo.title, color = Surface, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .height(1.dp)
                                    .dashedBorder(Surface.copy(alpha = 0.55f), 0.dp, dash = 5.dp, gap = 4.dp)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    promo.terms,
                                    color = Surface.copy(alpha = 0.8f), fontSize = 9.5.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Surface(
                                    shape = RoundedCornerShape(999.dp),
                                    color = Surface.copy(alpha = 0.16f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Surface.copy(alpha = 0.6f)),
                                    onClick = { }
                                ) {
                                    Text(
                                        "Copy code",
                                        color = Surface, fontSize = 9.5.sp, fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            SectionLabel("My vouchers")
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    IconTileCircle(Icons.Outlined.LocalOffer, TileGreen, Leaf)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("No vouchers saved yet", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                        Text("Tap Copy code above and use it at checkout.", fontSize = 10.5.sp, color = Muted)
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            InfoCard(
                icon = Icons.Outlined.Schedule,
                iconTint = Grain,
                bg = Cream,
                title = "Codes cannot be combined with other offers.",
                lines = listOf(),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

// ─────────────────────────── E04 Notifications ───────────────────────────

@Composable
fun NotificationsScreen(navController: NavController) {
    var filter by remember { mutableStateOf("All") }
    var readState by remember { mutableStateOf(MockData.notifications.associate { it.id to it.unread }) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Notifications", onBack = { navController.popBackStack() }, actionText = "Mark all read", onAction = {
            readState = readState.mapValues { false }
        })
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("All", "Orders", "Promos").forEach { f ->
                    PillChip(f, filter == f) { filter = f }
                    Spacer(Modifier.width(8.dp))
                }
                Spacer(Modifier.weight(1f))
                Text("${readState.values.count { it }} unread", fontSize = 10.5.sp, color = Muted)
            }
            Spacer(Modifier.height(12.dp))
            val shown = when (filter) {
                "Orders" -> MockData.notifications.filter { it.iconKey == "truck" || it.iconKey == "check" }
                "Promos" -> MockData.notifications.filter { it.iconKey == "tag" }
                else -> MockData.notifications
            }
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                shown.forEach { n ->
                    val unread = readState[n.id] ?: false
                    Surface(
                        color = Surface, shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Border)
                    ) {
                        Row(Modifier.height(IntrinsicSize.Min)) {
                            Box(
                                Modifier
                                    .width(3.dp)
                                    .fillMaxHeight()
                                    .background(if (unread) Leaf else Color.Transparent)
                            )
                            Row(
                                Modifier.padding(12.dp).weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconTileCircle(notificationIcon(n.iconKey), tileColorOf(n.tileColor), Leaf, 40.dp, 19.dp)
                                Spacer(Modifier.width(10.dp))
                                Column(Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(n.title, fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Ink, modifier = Modifier.weight(1f))
                                        if (unread) {
                                            Box(Modifier.size(7.dp).clip(CircleShape).background(Fresh))
                                        }
                                    }
                                    Text(n.body, fontSize = 10.5.sp, color = Muted)
                                    Spacer(Modifier.height(3.dp))
                                    Text(n.time, fontSize = 9.sp, color = Muted)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
            Text(
                "You're all caught up",
                fontSize = 10.5.sp, color = Muted,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

private fun notificationIcon(key: String): ImageVector = when (key) {
    "truck" -> Icons.Outlined.LocalShipping
    "check" -> Icons.Outlined.CheckCircle
    "tag" -> Icons.Outlined.LocalOffer
    "store" -> Icons.Outlined.Storefront
    "clock" -> Icons.Outlined.Schedule
    else -> Icons.Outlined.Notifications
}
