package com.example.palengke_grocery_customer.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.palengke_grocery_customer.data.MockData
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

// ─────────────────────────── D01 Order tracking ───────────────────────────

@Composable
fun OrderTrackingScreen(navController: NavController, orderId: String) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Track order", onBack = { navController.popBackStack() }, actionText = "Help", onAction = {
            navController.navigate(Screen.HelpFaq.route)
        })
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            MapPreview(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(210.dp)
            )
            Spacer(Modifier.height(12.dp))
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text("Arriving in 18 min", fontWeight = FontWeight.Black, fontSize = 18.sp, color = Ink)
                    Text("Today, 2:00–4:00 PM · Order #$orderId", fontSize = 10.5.sp, color = Muted)
                    Spacer(Modifier.height(10.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(GreenTintStrong)
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth(0.62f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Leaf)
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Row {
                        Text("Packed", fontSize = 9.5.sp, color = Muted)
                        Spacer(Modifier.weight(1f))
                        Text("On the way", fontSize = 9.5.sp, fontWeight = FontWeight.Bold, color = Leaf)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    AvatarCircle("RS", TileAmber, Grain, 44.dp, 13)
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text(MockData.riderName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                        Text(MockData.riderVehicle, fontSize = 10.5.sp, color = Muted)
                        RatingRow(MockData.riderRating, MockData.riderTrips)
                    }
                    Surface(
                        shape = CircleShape, color = GreenTint,
                        onClick = { },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.Call, contentDescription = "Call", tint = Leaf, modifier = Modifier.size(17.dp))
                        }
                    }
                    Spacer(Modifier.width(8.dp))
                    Surface(
                        shape = CircleShape, color = Leaf,
                        onClick = { navController.navigate(Screen.Chat.route) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Chat", tint = Surface, modifier = Modifier.size(17.dp))
                        }
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            SectionLabel("Order progress")
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(Modifier.padding(14.dp)) {
                    TimelineStep("Order confirmed", "1:42 PM", done = true)
                    TimelineStep("Packed at the market", "1:58 PM", done = true)
                    TimelineStep("Out for delivery", "2:06 PM", done = false, current = true)
                    TimelineStep("Delivered to your door", "Est. 2:24 PM", done = false, last = true)
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "Need help with this order?",
                fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = Leaf,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(Screen.Chat.route) },
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun TimelineStep(title: String, time: String, done: Boolean, current: Boolean = false, last: Boolean = false) {
    Row(verticalAlignment = Alignment.Top) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            done -> GreenTint
                            current -> Leaf
                            else -> Surface
                        }
                    )
                    .border(
                        width = if (current) 2.dp else 1.4.dp,
                        color = when {
                            done || current -> Leaf
                            else -> Muted.copy(alpha = 0.5f)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (done) Icon(Icons.Filled.Check, contentDescription = null, tint = Leaf, modifier = Modifier.size(13.dp))
            }
            if (!last) {
                Box(
                    Modifier
                        .width(2.dp)
                        .height(18.dp)
                        .background(if (done) Leaf.copy(alpha = 0.5f) else Border)
                )
            }
        }
        Spacer(Modifier.width(12.dp))
        Row(Modifier.weight(1f).offset(y = 2.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                title,
                fontSize = 12.sp,
                fontWeight = if (current || done) FontWeight.Bold else FontWeight.Medium,
                color = if (current || done) Ink else Muted,
                modifier = Modifier.weight(1f)
            )
            Text(time, fontSize = 10.sp, color = Muted)
        }
    }
}

// ─────────────────────────── D02 Order history ───────────────────────────

@Composable
fun OrderHistoryScreen(navController: NavController) {
    var filter by remember { mutableStateOf("All") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("My orders", onBack = { navController.popBackStack() }, actionText = "Filter", onAction = {})
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("All", "Active", "Delivered").forEach { f ->
                    PillChip(f, filter == f) { filter = f }
                    Spacer(Modifier.width(8.dp))
                }
                Spacer(Modifier.weight(1f))
                Text("4 orders", fontSize = 10.5.sp, color = Muted)
            }
            Spacer(Modifier.height(12.dp))
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val shown = when (filter) {
                    "Active" -> MockData.orders.filter { it.status == "Out for delivery" }
                    "Delivered" -> MockData.orders.filter { it.status == "Delivered" }
                    else -> MockData.orders
                }
                shown.forEach { order ->
                    Surface(
                        color = Surface, shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                        onClick = { navController.navigate(Screen.OrderDetails.createRoute(order.id)) }
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Order #${order.id}", fontWeight = FontWeight.ExtraBold, fontSize = 13.5.sp, color = Ink, modifier = Modifier.weight(1f))
                                StatusChip(order.status, GreenTint, Leaf)
                            }
                            Spacer(Modifier.height(3.dp))
                            Text("${order.date} · ${order.itemCount} items · ₱${order.total}", fontSize = 10.5.sp, color = Muted)
                            Spacer(Modifier.height(10.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    order.items.take(3).forEach { entry ->
                                        ProductThumb(entry.product, 38.dp, corner = 10.dp, padding = 5.dp)
                                    }
                                }
                                Spacer(Modifier.weight(1f))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        "View details",
                                        color = Leaf, fontWeight = FontWeight.Bold, fontSize = 11.5.sp
                                    )
                                    Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Leaf, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
        BottomNavBar("orders") { navController.navigateBottom(it) }
    }
}

// ─────────────────────────── D03 Order details ───────────────────────────

@Composable
fun OrderDetailsScreen(navController: NavController, orderId: String) {
    val order = MockData.orders.first { it.id == orderId }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Order details", onBack = { navController.popBackStack() })
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Order #${order.id}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Ink, modifier = Modifier.weight(1f))
                        StatusChip("Delivered", GreenTint, Leaf)
                    }
                    Spacer(Modifier.height(3.dp))
                    Text("Delivered today · 2:18 PM", fontSize = 10.5.sp, color = Muted)
                    Text("${MockData.primaryStore.name} · Stall 12", fontSize = 10.5.sp, color = Muted)
                }
            }
            Spacer(Modifier.height(8.dp))
            SectionLabel("Items")
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                    order.items.forEach { entry ->
                        Row(
                            Modifier.padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProductThumb(entry.product, 42.dp, corner = 11.dp, padding = 5.dp)
                            Spacer(Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Text(entry.product.name, fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Ink)
                                Text("${entry.qty} × ₱${entry.product.price}", fontSize = 10.5.sp, color = Muted)
                            }
                            Text("₱${entry.qty * entry.product.price}", fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Ink)
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(Modifier.padding(14.dp)) {
                    SummaryRow("Subtotal", "₱141")
                    Spacer(Modifier.height(5.dp))
                    SummaryRow("Delivery fee", "₱20")
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Border)
                    Spacer(Modifier.height(10.dp))
                    Row {
                        Text("Total paid", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink, modifier = Modifier.weight(1f))
                        Text("₱161", fontWeight = FontWeight.Black, fontSize = 15.sp, color = Leaf)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            SectionLabel("Delivered to")
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = Leaf, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("Juan Dela Cruz · Poblacion, Irosin", fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Ink)
                        Text("Purok 3, near the plaza · 0917 123 4567", fontSize = 10.5.sp, color = Muted)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Paid via Cash on delivery", fontSize = 12.sp, color = OnSurfaceVariant, modifier = Modifier.weight(1f))
                Text("₱161", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink)
            }
            Spacer(Modifier.height(12.dp))
            PrimaryButton(
                "Reorder these items",
                { navController.navigate(Screen.Cart.route) },
                Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth().clickable { }.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Description, contentDescription = null, tint = Leaf, modifier = Modifier.size(15.dp))
                Spacer(Modifier.width(6.dp))
                Text("Download receipt (PDF)", color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

// ─────────────────────────── D04 Rate order ───────────────────────────

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun RateOrderScreen(navController: NavController, orderId: String) {
    var stars by remember { mutableStateOf(5) }
    val selectedTags = remember { mutableStateListOf("On time", "Friendly", "Careful") }
    var tip by remember { mutableStateOf("₱20") }
    val tags = listOf("On time", "Friendly", "Careful", "Well packed", "Great comms")
    val tips = listOf("₱10", "₱20", "₱50", "No tip")

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Rate order", onBack = { navController.popBackStack() }, actionText = "Skip", onAction = {
            navController.navigate(Screen.OrderHistory.route) { popUpTo(Screen.OrderHistory.route) { inclusive = true } }
        })
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "How was your delivery?",
                style = MaterialTheme.typography.headlineLarge, color = Ink,
                modifier = Modifier.padding(horizontal = 18.dp)
            )
            Text(
                "Order #$orderId · delivered today at 2:18 PM",
                fontSize = 11.sp, color = Muted,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 4.dp)
            )
            Spacer(Modifier.height(12.dp))
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    AvatarCircle("RS", TileAmber, Grain, 44.dp, 13)
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text(MockData.riderName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                        Text("Your rider · Honda Click", fontSize = 10.5.sp, color = Muted)
                        RatingRow(MockData.riderRating, MockData.riderTrips)
                    }
                    StatusChip("On time", GreenTint, Leaf)
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "Tap a star to rate",
                fontSize = 10.5.sp, color = Muted,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                (1..5).forEach { i ->
                    IconButton(onClick = { stars = i }, modifier = Modifier.size(44.dp)) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = if (i <= stars) Mango else Border,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
            Text(
                when (stars) {
                    5 -> "Excellent! Salamat sa feedback."
                    4 -> "Maganda! Salamat sa feedback."
                    3 -> "Okay lang. Salamat sa feedback."
                    2 -> "Pwede pa naman. Salamat sa feedback."
                    else -> "Salamat sa feedback."
                },
                fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Leaf,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            SectionLabel("What went well?")
            FlowRow(
                Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.forEach { tag ->
                    PillChip(tag, selectedTags.contains(tag)) {
                        if (selectedTags.contains(tag)) selectedTags.remove(tag) else selectedTags.add(tag)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Add a tip", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink, modifier = Modifier.weight(1f))
                Text("100% goes to ${MockData.riderName.removeSuffix(".")} ", fontSize = 10.sp, color = Muted)
            }
            FlowRow(
                Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tips.forEach { t ->
                    PillChip(t, tip == t) { tip = t }
                }
            }
            Spacer(Modifier.height(10.dp))
            Surface(
                color = Surface, shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                androidx.compose.foundation.text.BasicTextField(
                    value = "Salamat, mabilis at maayos ang delivery!",
                    onValueChange = { },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = Nunito, fontSize = 12.sp, color = OnSurfaceVariant
                    ),
                    modifier = Modifier.padding(14.dp),
                    cursorBrush = androidx.compose.ui.graphics.Brush.verticalGradient(listOf(Leaf, Leaf))
                )
            }
            Spacer(Modifier.height(16.dp))
            PrimaryButton("Submit rating", {
                navController.navigate(Screen.OrderHistory.route) { popUpTo(Screen.OrderHistory.route) { inclusive = true } }
            }, Modifier.fillMaxWidth().padding(horizontal = 16.dp))
            Spacer(Modifier.height(8.dp))
            Text(
                "Your feedback helps keep deliveries great",
                fontSize = 10.sp, color = Muted,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}
