package com.example.palengke_grocery_customer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.graphicsLayer
import com.example.palengke_grocery_customer.model.Category
import com.example.palengke_grocery_customer.model.Product
import com.example.palengke_grocery_customer.ui.theme.Nunito
import com.example.palengke_grocery_customer.ui.theme.*

// ────────────────────────── palette helpers ──────────────────────────

fun tileColorOf(long: Long) = Color(long)

fun categoryIcon(key: String): ImageVector = when (key) {
    "rice" -> Icons.Outlined.RiceBowl
    "canned" -> Icons.Outlined.Inventory2
    "noodles" -> Icons.Outlined.RamenDining
    "coffee" -> Icons.Outlined.Coffee
    "snacks" -> Icons.Outlined.Cookie
    "dairy" -> Icons.Outlined.LocalDrink
    "sauces" -> Icons.Outlined.WaterDrop
    "cleaning" -> Icons.Outlined.CleaningServices
    else -> Icons.Outlined.ShoppingBasket
}

// ────────────────────────── dashed border modifier ──────────────────────────

fun Modifier.dashedBorder(color: Color, cornerRadius: Dp, strokeWidth: Dp = 1.4.dp, dash: Dp = 6.dp, gap: Dp = 5.dp) =
    drawBehind {
        val stroke = strokeWidth.toPx()
        val path = Path().apply {
            addRoundRect(
                androidx.compose.ui.geometry.RoundRect(
                    Rect(0f, 0f, size.width, size.height),
                    CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
                )
            )
        }
        drawPath(
            path,
            color = color,
            style = Stroke(
                width = stroke,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(dash.toPx(), gap.toPx()))
            )
        )
    }

// ────────────────────────── scaffold & top bar ──────────────────────────

@Composable
fun AppScaffold(
    title: String,
    onBack: (() -> Unit)? = null,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    container: Color = Background,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(Modifier.fillMaxSize().background(container).statusBarsPadding()) {
        TopBar(title, onBack, actionText, onAction)
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            content()
        }
        bottomBar?.invoke()
    }
}

@Composable
fun TopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    Box(Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 4.dp)) {
        if (onBack != null) {
            IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Ink)
            }
        }
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            color = Ink,
            modifier = Modifier.align(Alignment.Center)
        )
        if (actionText != null && onAction != null) {
            Text(
                actionText,
                color = Leaf,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onAction() }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            )
        }
    }
}

// ────────────────────────── bottom navigation ──────────────────────────

fun androidx.navigation.NavController.navigateBottom(route: String) {
    when (route) {
        "home" -> navigate(com.example.palengke_grocery_customer.navigation.Screen.Home.route) {
            popUpTo(com.example.palengke_grocery_customer.navigation.Screen.Home.route) { inclusive = true }
        }
        "search" -> navigate(com.example.palengke_grocery_customer.navigation.Screen.SearchResults.createRoute("pancit canton"))
        "cart" -> navigate(com.example.palengke_grocery_customer.navigation.Screen.Cart.route)
        "orders" -> navigate(com.example.palengke_grocery_customer.navigation.Screen.OrderHistory.route)
        "profile" -> navigate(com.example.palengke_grocery_customer.navigation.Screen.Profile.route)
    }
}

@Composable
fun BottomNavBar(selected: String, onNavigate: (String) -> Unit) {
    data class Tab(val route: String, val label: String, val icon: ImageVector, val active: ImageVector)
    val tabs = listOf(
        Tab("home", "Home", Icons.Outlined.Home, Icons.Filled.Home),
        Tab("search", "Search", Icons.Outlined.Search, Icons.Filled.Search),
        Tab("cart", "Cart", Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart),
        Tab("orders", "Orders", Icons.Outlined.ReceiptLong, Icons.Filled.ReceiptLong),
        Tab("profile", "Profile", Icons.Outlined.Person, Icons.Filled.Person)
    )
    Surface(color = Surface, shadowElevation = 18.dp) {
        Row(Modifier.fillMaxWidth().navigationBarsPadding().height(66.dp)) {
            tabs.forEach { tab ->
                val isSel = selected == tab.route
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onNavigate(tab.route) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .padding(top = 4.dp)
                            .size(width = 20.dp, height = 3.dp)
                            .background(if (isSel) Leaf else Color.Transparent, RoundedCornerShape(2.dp))
                    )
                    Spacer(Modifier.height(5.dp))
                    Icon(
                        if (isSel) tab.active else tab.icon,
                        contentDescription = tab.label,
                        tint = if (isSel) Leaf else Muted,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(tab.label, fontSize = 9.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSel) Leaf else Muted)
                }
            }
        }
    }
}

// ────────────────────────── buttons & chips ──────────────────────────

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    container: Color = Leaf,
    content: Color = Surface
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = content,
            disabledContainerColor = container.copy(alpha = 0.45f),
            disabledContentColor = content
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

@Composable
fun OutlineButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, border: Color = Border, content: Color = Leaf) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = content)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun PillChip(label: String, selected: Boolean, onClick: (() -> Unit)? = null) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = if (selected) Leaf else Surface,
        border = if (selected) null else BorderStroke(1.dp, Border),
        onClick = onClick ?: {},
        enabled = onClick != null
    ) {
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) Surface else OnSurfaceVariant,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 9.dp)
        )
    }
}

@Composable
fun StatusChip(text: String, bg: Color, fg: Color) {
    Surface(color = bg, shape = RoundedCornerShape(7.dp)) {
        Text(
            text, color = fg, fontSize = 10.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun PromoPill(text: String) {
    Surface(color = Tomato, shape = RoundedCornerShape(7.dp)) {
        Text(
            text, color = Surface, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

// ────────────────────────── text bits ──────────────────────────

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun RatingRow(rating: Double, count: Int, starSize: Dp = 12.dp, big: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(Icons.Filled.Star, contentDescription = null, tint = Mango, modifier = Modifier.size(starSize))
        Text(
            "$rating",
            fontSize = if (big) 13.sp else 11.sp,
            fontWeight = FontWeight.Bold, color = Ink
        )
        if (!big) Text("($count)", fontSize = 10.sp, color = Muted)
        else Text("($count)", fontSize = 11.sp, color = Muted)
    }
}

@Composable
fun PriceRow(price: Int, oldPrice: Int?, priceSize: Int = 15, oldSize: Int = 10) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("₱$price", fontSize = priceSize.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
        if (oldPrice != null) {
            Text("₱$oldPrice", fontSize = oldSize.sp, color = Muted,
                style = androidx.compose.ui.text.TextStyle(
                    textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                )
            )
        }
    }
}

@Composable
fun PageDots(count: Int = 3, active: Int = 0) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(count) { i ->
            Box(
                Modifier
                    .size(width = if (i == active) 16.dp else 6.dp, height = 4.dp)
                    .background(if (i == active) Accent else Surface.copy(alpha = 0.35f), RoundedCornerShape(2.dp))
            )
        }
    }
}

@Composable
fun AvatarCircle(initials: String, bg: Color, fg: Color, size: Dp = 44.dp, fontSize: Int = 14) {
    Box(
        Modifier
            .size(size)
            .clip(CircleShape)
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        Text(initials, color = fg, fontWeight = FontWeight.ExtraBold, fontSize = fontSize.sp)
    }
}

// ────────────────────────── product visuals ──────────────────────────

@Composable
fun ProductThumb(product: Product, size: Dp, corner: Dp = 14.dp, padding: Dp = 8.dp) {
    Box(
        Modifier
            .size(size)
            .clip(RoundedCornerShape(corner))
            .background(tileColorOf(product.tileColor)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun AddCircle(onClick: () -> Unit, size: Dp = 30.dp) {
    Surface(
        shape = CircleShape, color = GreenTint,
        onClick = onClick, modifier = Modifier.size(size)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Filled.Add, contentDescription = "Add", tint = Leaf, modifier = Modifier.size(17.dp))
        }
    }
}

@Composable
fun QtyStepper(qty: Int, onMinus: () -> Unit, onPlus: () -> Unit, compact: Boolean = false) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = Surface,
        border = BorderStroke(1.dp, Border)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Outlined.Remove, contentDescription = "Minus",
                tint = Ink, modifier = Modifier
                    .size(if (compact) 26.dp else 30.dp)
                    .clip(CircleShape)
                    .clickable { onMinus() }
                    .padding(7.dp)
            )
            Text(
                "$qty", fontWeight = FontWeight.Bold,
                fontSize = if (compact) 12.sp else 13.sp, color = Ink
            )
            Icon(
                Icons.Filled.Add, contentDescription = "Plus",
                tint = Leaf, modifier = Modifier
                    .size(if (compact) 26.dp else 30.dp)
                    .clip(CircleShape)
                    .clickable { onPlus() }
                    .padding(7.dp)
            )
        }
    }
}

// Standard browse-list product card (B02 / B03 / B07)
@Composable
fun ProductRow(
    product: Product,
    qtyInCart: Int = 0,
    onOpen: () -> Unit = {},
    onAdd: () -> Unit = {},
    onMinus: () -> Unit = {}
) {
    Surface(
        color = Surface, shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Border),
        onClick = onOpen
    ) {
        Row(
            Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProductThumb(product, 64.dp)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Ink)
                Text(product.sizeLine, fontSize = 10.5.sp, color = Muted)
                Spacer(Modifier.height(3.dp))
                RatingRow(product.rating, product.ratingCount)
                Spacer(Modifier.height(3.dp))
                PriceRow(product.price, product.oldPrice)
            }
            if (qtyInCart > 0) {
                QtyStepper(qtyInCart, onMinus, onPlus, compact = true)
            } else {
                AddCircle(onAdd)
            }
        }
    }
}

// ────────────────────────── category tile ──────────────────────────

@Composable
fun CategoryTile(category: Category, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        color = Surface, shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Border),
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.padding(vertical = 12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(tileColorOf(category.tileColor)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    categoryIcon(category.iconKey), contentDescription = null,
                    tint = Leaf, modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.height(7.dp))
            Text(category.name, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Ink)
        }
    }
}

// ────────────────────────── form fields ──────────────────────────

@Composable
fun FieldLabel(text: String) {
    Text(text, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceVariant)
}

@Composable
fun LabeledField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    keyboardType: androidx.compose.ui.text.input.KeyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation =
        androidx.compose.ui.text.input.VisualTransformation.None,
    trailing: (@Composable () -> Unit)? = null
) {
    Column {
        FieldLabel(label)
        Spacer(Modifier.height(6.dp))
        Surface(
            shape = RoundedCornerShape(12.dp), color = Surface,
            border = BorderStroke(1.dp, Border)
        ) {
            Row(
                Modifier.padding(horizontal = 14.dp).height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.foundation.text.BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = Nunito,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Ink
                    ),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = keyboardType),
                    visualTransformation = visualTransformation,
                    cursorBrush = Brush.verticalGradient(listOf(Leaf, Leaf)),
                    modifier = Modifier.weight(1f),
                    decorationBox = { inner ->
                        Box {
                            if (value.isEmpty()) {
                                Text(hint, fontSize = 13.sp, color = Muted, fontWeight = FontWeight.Normal)
                            }
                            inner()
                        }
                    }
                )
                trailing?.invoke()
            }
        }
    }
}

@Composable
fun PhoneField(value: String, onValueChange: (String) -> Unit, label: String = "Phone number") {
    Column {
        FieldLabel(label)
        Spacer(Modifier.height(6.dp))
        Surface(
            shape = RoundedCornerShape(12.dp), color = Surface,
            border = BorderStroke(1.dp, Border)
        ) {
            Row(
                Modifier.padding(horizontal = 14.dp).height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("+63", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Leaf)
                Spacer(Modifier.width(10.dp))
                Box(
                    Modifier.width(1.dp).height(20.dp).background(Border)
                )
                Spacer(Modifier.width(10.dp))
                androidx.compose.foundation.text.BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = Nunito,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Ink
                    ),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone
                    ),
                    cursorBrush = Brush.verticalGradient(listOf(Leaf, Leaf)),
                    modifier = Modifier.weight(1f),
                    decorationBox = { inner ->
                        Box {
                            if (value.isEmpty()) Text("917 123 4567", fontSize = 13.sp, color = Muted)
                            inner()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit, label: String = "Password") {
    var show by remember { mutableStateOf(false) }
    LabeledField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        hint = "••••••••",
        visualTransformation = if (show) androidx.compose.ui.text.input.VisualTransformation.None
        else androidx.compose.ui.text.input.PasswordVisualTransformation(),
        trailing = {
            Icon(
                if (show) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                contentDescription = null,
                tint = Muted,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable { show = !show }
            )
        }
    )
}

@Composable
fun CheckRow(label: String, checked: Boolean, onToggle: () -> Unit, labelSp: Int = 11) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onToggle() }
    ) {
        Surface(
            shape = RoundedCornerShape(6.dp),
            color = if (checked) Leaf else Surface,
            border = if (checked) null else BorderStroke(1.4.dp, Muted.copy(alpha = 0.5f)),
            modifier = Modifier.size(20.dp)
        ) {
            if (checked) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = Surface, modifier = Modifier.size(14.dp))
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        Text(label, fontSize = labelSp.sp, color = OnSurfaceVariant, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun RadioCircle(selected: Boolean) {
    Box(
        Modifier
            .size(20.dp)
            .clip(CircleShape)
            .border(
                width = 1.6.dp,
                color = if (selected) Leaf else Muted.copy(alpha = 0.55f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(Modifier.size(10.dp).clip(CircleShape).background(Leaf))
        }
    }
}

// ────────────────────────── info / notice cards ──────────────────────────

@Composable
fun InfoCard(
    icon: ImageVector,
    iconTint: Color,
    bg: Color,
    title: String,
    lines: List<String>,
    modifier: Modifier = Modifier
) {
    Surface(color = bg, shape = RoundedCornerShape(14.dp), modifier = modifier.fillMaxWidth()) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(10.dp))
            Column {
                Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = iconTint)
                lines.forEach {
                    Spacer(Modifier.height(2.dp))
                    Text(it, fontSize = 11.sp, color = OnSurfaceVariant, lineHeight = 15.sp)
                }
            }
        }
    }
}

// ────────────────────────── misc rows ──────────────────────────

@Composable
fun IconTileCircle(icon: ImageVector, bg: Color, tint: Color, size: Dp = 42.dp, iconSize: Dp = 20.dp) {
    Box(
        Modifier
            .size(size)
            .clip(CircleShape)
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(iconSize))
    }
}

@Composable
fun ListRowCard(
    icon: ImageVector,
    tile: Color,
    title: String,
    sub: String,
    onClick: () -> Unit,
    trailing: (@Composable () -> Unit)? = null
) {
    Surface(
        color = Surface, shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Border),
        onClick = onClick
    ) {
        Row(
            Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconTileCircle(icon, tile, Leaf)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Ink)
                Text(sub, fontSize = 11.sp, color = Muted)
            }
            trailing?.invoke() ?: Icon(
                Icons.Outlined.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun ToggleRow(title: String, checked: Boolean, onToggle: (Boolean) -> Unit, inCard: Boolean = true) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Ink,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Leaf,
                checkedThumbColor = Surface,
                uncheckedTrackColor = Color(0xFFDCE4DB),
                uncheckedThumbColor = Surface,
                uncheckedBorderColor = Color.Transparent
            ),
            modifier = Modifier.graphicsLayer(scaleX = 0.9f, scaleY = 0.9f)
        )
    }
}

@Composable
fun SectionDivider() {
    HorizontalDivider(thickness = 1.dp, color = Border, modifier = Modifier.padding(horizontal = 14.dp))
}

// ────────────────────────── immersive green screens ──────────────────────────

@Composable
fun GreenScreenBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0E7A46), Leaf, Color(0xFF084B2B))))
    ) {
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .offset(x = 70.dp, y = (-50).dp)
                .size(240.dp)
                .clip(CircleShape)
                .background(Fresh.copy(alpha = 0.10f))
        )
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-80).dp, y = 90.dp)
                .size(280.dp)
                .clip(CircleShape)
                .background(Fresh.copy(alpha = 0.08f))
        )
        content()
    }
}

// ────────────────────────── D01 map preview ──────────────────────────

@Composable
fun MapPreview(modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE7F0E6))
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // blocks
            drawRect(Color(0xFFDDEBDA), topLeft = androidx.compose.ui.geometry.Offset(0f, h * 0.12f), size = androidx.compose.ui.geometry.Size(w, h * 0.22f))
            drawRect(Color(0xFFDDEBDA), topLeft = androidx.compose.ui.geometry.Offset(0f, h * 0.58f), size = androidx.compose.ui.geometry.Size(w, h * 0.20f))
            // roads
            val road = Color(0xFFFBFDF9)
            drawLine(road, Offset(w * 0.0f, h * 0.44f), Offset(w, h * 0.44f), strokeWidth = 14f)
            drawLine(road, Offset(w * 0.36f, 0f), Offset(w * 0.36f, h), strokeWidth = 12f)
            drawLine(road, Offset(w * 0.74f, 0f), Offset(w * 0.74f, h), strokeWidth = 10f)
            // dashed route
            val path = Path().apply {
                moveTo(w * 0.12f, h * 0.86f)
                cubicTo(w * 0.28f, h * 0.60f, w * 0.44f, h * 0.66f, w * 0.55f, h * 0.46f)
                cubicTo(w * 0.66f, h * 0.26f, w * 0.80f, h * 0.30f, w * 0.86f, h * 0.18f)
            }
            drawPath(
                path,
                color = Leaf,
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 12f))
                )
            )
            // start dot
            drawCircle(Color.White, radius = 13f, center = Offset(w * 0.12f, h * 0.86f))
            drawCircle(Leaf, radius = 8f, center = Offset(w * 0.12f, h * 0.86f))
            // destination pin
            drawCircle(Color.White, radius = 16f, center = Offset(w * 0.86f, h * 0.18f))
            drawCircle(Leaf, radius = 11f, center = Offset(w * 0.86f, h * 0.18f))
        }
        Surface(
            color = Surface, shape = RoundedCornerShape(999.dp),
            border = BorderStroke(1.dp, Border),
            modifier = Modifier.align(Alignment.TopStart).padding(10.dp)
        ) {
            Row(
                Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(7.dp).clip(CircleShape).background(Fresh))
                Spacer(Modifier.width(6.dp))
                Text("Rider is 1.2 km away", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Ink)
            }
        }
    }
}
