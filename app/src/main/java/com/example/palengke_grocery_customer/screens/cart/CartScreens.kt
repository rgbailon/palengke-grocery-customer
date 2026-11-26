package com.example.palengke_grocery_customer.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.palengke_grocery_customer.model.CartEntry
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

// ─────────────────────────── C01 Cart ───────────────────────────

@Composable
fun CartScreen(navController: NavController) {
    val entries = remember { MockData.cart.toMutableStateList() }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("My cart", onBack = { navController.popBackStack() }, actionText = "Clear", onAction = { entries.clear() })
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
                    Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = Leaf, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Deliver to", fontSize = 9.5.sp, color = Muted)
                        Text("Poblacion, Irosin", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                    }
                    Text(
                        "Change",
                        color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp,
                        modifier = Modifier.clickable { navController.navigate(Screen.AddressBook.route) }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("${entries.size} items", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink)
                Text("  · ${MockData.primaryStore.name}", fontSize = 11.sp, color = Muted)
            }
            Spacer(Modifier.height(10.dp))
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                entries.forEach { entry ->
                    CartLine(entry,
                        onMinus = {
                            val i = entries.indexOf(entry)
                            if (entry.qty > 1) entries[i] = entry.copy(qty = entry.qty - 1) else entries.remove(entry)
                        },
                        onPlus = {
                            val i = entries.indexOf(entry)
                            entries[i] = entry.copy(qty = entry.qty + 1)
                        })
                }
            }
            Spacer(Modifier.height(12.dp))
            Surface(
                color = GreenTint, shape = RoundedCornerShape(14.dp),
                onClick = { },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.LocalOffer, contentDescription = null, tint = Leaf, modifier = Modifier.size(17.dp))
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Add a promo code", fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = LeafDeep)
                        Text("2 offers available for you", fontSize = 10.5.sp, color = OnSurfaceVariant)
                    }
                    Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Leaf, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(Modifier.height(14.dp))
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(Modifier.padding(14.dp)) {
                    SummaryRow("Subtotal (${entries.size} items)", "₱${MockData.cartSubtotal}")
                    Spacer(Modifier.height(6.dp))
                    SummaryRow("Delivery fee", "₱${MockData.deliveryFee}")
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Border)
                    Spacer(Modifier.height(10.dp))
                    Row {
                        Text("Total", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Ink, modifier = Modifier.weight(1f))
                        Text("₱${MockData.cartTotal}", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Leaf)
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            PrimaryButton(
                "Go to checkout · ₱${MockData.cartTotal}",
                { navController.navigate(Screen.Checkout.route) },
                Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CartLine(entry: CartEntry, onMinus: () -> Unit, onPlus: () -> Unit) {
    Surface(
        color = Surface, shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Border)
    ) {
        Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            ProductThumb(entry.product, 54.dp)
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(entry.product.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                Text(entry.product.sizeLine, fontSize = 10.5.sp, color = Muted)
                Spacer(Modifier.height(3.dp))
                Text("₱${entry.product.price}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Ink)
            }
            QtyStepper(entry.qty, onMinus, onPlus, compact = true)
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, valueBold: Boolean = true, valueColor: Color = Ink) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, fontSize = 12.sp, color = OnSurfaceVariant, modifier = Modifier.weight(1f))
        Text(value, fontSize = 13.sp, fontWeight = if (valueBold) FontWeight.Bold else FontWeight.SemiBold, color = valueColor)
    }
}

// ─────────────────────────── C02 Checkout ───────────────────────────

@Composable
fun CheckoutScreen(navController: NavController) {
    var deliverySlot by remember { mutableStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Checkout", onBack = { navController.popBackStack() })
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
                    IconTileCircle(Icons.Outlined.LocationOn, TileGreen, Leaf, 38.dp, 17.dp)
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Juan Dela Cruz · 0917 123 4567", fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Ink)
                        Text("Purok 3, Poblacion, Irosin", fontSize = 10.5.sp, color = Muted)
                        Text("Sorsogon · Landmark: near plaza", fontSize = 10.5.sp, color = Muted)
                    }
                    Text(
                        "Edit",
                        color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp,
                        modifier = Modifier.clickable { navController.navigate(Screen.AddressBook.route) }
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            SectionLabel("Delivery time")
            Row(
                Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PillChip("Today · 2–4 PM", deliverySlot == 0) { deliverySlot = 0 }
                PillChip("Tomorrow · 8–10 AM", deliverySlot == 1) { deliverySlot = 1 }
            }
            Spacer(Modifier.height(6.dp))
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Payment method", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink, modifier = Modifier.weight(1f))
                Text(
                    "Change",
                    color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp,
                    modifier = Modifier.clickable { navController.navigate(Screen.PaymentMethods.route) }
                )
            }
            Surface(
                color = Surface, shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column {
                    PayRow(
                        icon = Icons.Outlined.LocalAtm, tile = TileGreen,
                        title = "Cash on delivery", sub = "Pay the rider when it arrives",
                        selected = true, trailing = { StatusChip("Selected", GreenTint, Leaf) }
                    )
                    HorizontalDivider(color = Border, modifier = Modifier.padding(start = 52.dp))
                    PayRow(
                        icon = Icons.Outlined.AccountBalanceWallet, tile = TileBlue,
                        title = "GCash", sub = "0917 •••• 4567", selected = false
                    )
                    HorizontalDivider(color = Border, modifier = Modifier.padding(start = 52.dp))
                    PayRow(
                        icon = Icons.Outlined.CreditCard, tile = TilePurple,
                        title = "Maya", sub = "Add a card to pay online", selected = false
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            Surface(
                color = Surface, shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text("3 items · ${MockData.primaryStore.name}", fontSize = 11.sp, color = Muted)
                    Spacer(Modifier.height(8.dp))
                    SummaryRow("Subtotal", "₱${MockData.cartSubtotal}")
                    Spacer(Modifier.height(5.dp))
                    SummaryRow("Delivery fee", "₱${MockData.deliveryFee}")
                    Spacer(Modifier.height(5.dp))
                    SummaryRow("Promo discount", "₱0")
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Border)
                    Spacer(Modifier.height(10.dp))
                    Row {
                        Text("Total to pay", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Ink, modifier = Modifier.weight(1f))
                        Text("₱${MockData.cartTotal}", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Leaf)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
        }
        Surface(color = Surface, shadowElevation = 14.dp) {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Total", fontSize = 10.sp, color = Muted)
                        Text("₱${MockData.cartTotal}", fontWeight = FontWeight.Black, fontSize = 19.sp, color = Leaf)
                    }
                    Spacer(Modifier.width(16.dp))
                    PrimaryButton(
                        "Place order",
                        { navController.navigate(Screen.OrderPlaced.route) },
                        Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    "You can pay the rider on arrival",
                    fontSize = 9.5.sp, color = Muted,
                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun PayRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tile: Color,
    title: String,
    sub: String,
    selected: Boolean,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioCircle(selected)
        Spacer(Modifier.width(10.dp))
        IconTileCircle(icon, tile, Leaf, 36.dp, 16.dp)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
            Text(sub, fontSize = 10.5.sp, color = Muted)
        }
        trailing?.invoke()
    }
}

// ─────────────────────────── C03 Order placed ───────────────────────────

@Composable
fun OrderPlacedScreen(navController: NavController) {
    GreenScreenBox(Modifier.statusBarsPadding()) {
        // confetti dots
        val dotColors = listOf(Fresh, Mango, Tomato, Grain, Accent)
        val positions = listOf(
            Triple(0.12f, 0.10f, 0), Triple(0.30f, 0.06f, 2), Triple(0.82f, 0.12f, 1),
            Triple(0.90f, 0.30f, 3), Triple(0.08f, 0.42f, 4), Triple(0.95f, 0.52f, 2),
            Triple(0.15f, 0.62f, 1), Triple(0.85f, 0.70f, 0)
        )
        positions.forEach { (fx, fy, ci) ->
            Box(
                Modifier
                    .offset(x = (fx * 360).dp, y = (fy * 700).dp)
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(dotColors[ci].copy(alpha = 0.85f))
            )
        }
        Column(
            Modifier.fillMaxSize().padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(56.dp))
            Box(
                Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(Surface),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = Leaf, modifier = Modifier.size(42.dp))
            }
            Spacer(Modifier.height(16.dp))
            Text("Order placed!", color = Surface, fontSize = 24.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(6.dp))
            Text(
                "Salamat, Juan! Your groceries are on the way.",
                color = Surface.copy(alpha = 0.75f), fontSize = 12.sp, textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Surface(shape = RoundedCornerShape(999.dp), color = Surface.copy(alpha = 0.14f)) {
                Row(
                    Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Schedule, contentDescription = null, tint = Surface, modifier = Modifier.size(13.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Today, 2–4 PM", color = Surface, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(Modifier.height(20.dp))
            Surface(color = Surface, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Order #2048", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Ink, modifier = Modifier.weight(1f))
                        StatusChip("3 items", GreenTint, Leaf)
                    }
                    Spacer(Modifier.height(8.dp))
                    listOf(
                        "Lucky Me Pancit Canton × 3",
                        "Century Tuna Flakes × 2",
                        "Kopiko Brown Coffee × 1"
                    ).forEach {
                        Text("·  $it", fontSize = 11.sp, color = OnSurfaceVariant, lineHeight = 17.sp)
                    }
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Border)
                    Spacer(Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Total paid", fontSize = 11.sp, color = Muted, modifier = Modifier.weight(1f))
                        Text("₱161 · COD", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Leaf)
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth()) {
                Text("What happens next", color = Surface, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
            Spacer(Modifier.height(12.dp))
            listOf(
                Triple(Icons.Outlined.ReceiptLong, "The market confirms your order", "Within 10 minutes"),
                Triple(Icons.Outlined.Inventory2, "Your groceries are packed", "Poblacion · Irosin market"),
                Triple(Icons.AutoMirrored.Outlined.List, "The rider heads to your door", "Track live in the app")
            ).forEach { (icon, title, sub) ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 5.dp)) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Transparent,
                        border = androidx.compose.foundation.BorderStroke(1.2.dp, Surface.copy(alpha = 0.55f)),
                        modifier = Modifier.size(30.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(icon, contentDescription = null, tint = Surface, modifier = Modifier.size(15.dp))
                        }
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(title, color = Surface, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                        Text(sub, color = Surface.copy(alpha = 0.6f), fontSize = 10.sp)
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = { navController.navigate(Screen.OrderTracking.createRoute("2048")) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Leaf, contentColor = Surface)
            ) {
                Text("Track my order", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                "Back to home",
                color = Surface, fontWeight = FontWeight.Bold, fontSize = 12.sp,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Home.route) { popUpTo(Screen.Splash.route) { inclusive = true } }
                }
            )
            Spacer(Modifier.height(20.dp))
        }
    }
}

// ─────────────────────────── C04 Payment methods ───────────────────────────

@Composable
fun PaymentMethodsScreen(navController: NavController) {
    var selected by remember { mutableStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TopBar("Payment methods", onBack = { navController.popBackStack() }, actionText = "Edit", onAction = {})
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            SectionLabel("Saved methods")
            Surface(
                color = Surface, shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column {
                    SavedPayRow(
                        icon = Icons.Outlined.LocalAtm, tile = TileGreen,
                        title = "Cash on delivery", sub = "Pay the rider on arrival",
                        badge = "Default", isSelected = selected == 0,
                        onSelect = { selected = 0 }
                    )
                    HorizontalDivider(color = Border, modifier = Modifier.padding(start = 52.dp))
                    SavedPayRow(
                        icon = Icons.Outlined.AccountBalanceWallet, tile = TileBlue,
                        title = "GCash", sub = "0917 •••• 4567",
                        badge = null, isSelected = selected == 1,
                        onSelect = { selected = 1 }
                    )
                    HorizontalDivider(color = Border, modifier = Modifier.padding(start = 52.dp))
                    SavedPayRow(
                        icon = Icons.Outlined.CreditCard, tile = TilePurple,
                        title = "Maya", sub = "•••• 8842 · expires 08/28",
                        badge = null, isSelected = selected == 2,
                        onSelect = { selected = 2 }
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
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
                    Modifier.padding(vertical = 18.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Add, contentDescription = null, tint = Leaf, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Add a new payment method", color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                    }
                    Spacer(Modifier.height(3.dp))
                    Text("GCash, Maya or a debit card", fontSize = 10.5.sp, color = Muted)
                }
            }
            Spacer(Modifier.height(16.dp))
            SectionLabel("Payment security")
            InfoCard(
                icon = Icons.Outlined.Security,
                iconTint = Grain,
                bg = Cream,
                title = "Your payments are protected",
                lines = listOf(
                    "Palengke never stores your full card number or your e-wallet PIN. COD is paid in person.",
                    "Receipts are emailed after every order."
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                "Need help? Chat with support",
                fontSize = 11.sp, color = Muted, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth().clickable { navController.navigate(Screen.Chat.route) },
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SavedPayRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tile: Color,
    title: String,
    sub: String,
    badge: String?,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        Modifier
            .padding(horizontal = 14.dp, vertical = 12.dp)
            .clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconTileCircle(icon, tile, Leaf, 38.dp, 17.dp)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                if (badge != null) {
                    Spacer(Modifier.width(8.dp))
                    StatusChip(badge, GreenTint, Leaf)
                }
            }
            Text(sub, fontSize = 10.5.sp, color = Muted)
        }
        RadioCircle(isSelected)
    }
}
