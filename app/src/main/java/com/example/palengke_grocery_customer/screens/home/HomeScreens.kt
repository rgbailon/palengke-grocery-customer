package com.example.palengke_grocery_customer.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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

@Composable
fun HomeScreen(navController: NavController) {
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
            HomeHeader(navController)
            Spacer(Modifier.height(4.dp))
            HomeSearch(navController)
            Spacer(Modifier.height(14.dp))
            HeroBanner(navController)
            Spacer(Modifier.height(6.dp))
            CategoriesSection(navController)
            DealsSection(navController)
            Spacer(Modifier.height(12.dp))
        }
        BottomNavBar("home") { route ->
            navController.navigateBottom(route)
        }
    }
}

@Composable
private fun HomeHeader(navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.ShoppingBasket, contentDescription = null,
                    tint = Leaf, modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("PALENGKE", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Leaf, letterSpacing = 1.sp)
            }
            Spacer(Modifier.height(3.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = Muted, modifier = Modifier.size(12.dp))
                Spacer(Modifier.width(3.dp))
                Text("Poblacion, Irosin", fontSize = 10.sp, color = OnSurfaceVariant)
            }
        }
        Box(contentAlignment = Alignment.TopEnd) {
            Surface(
                shape = CircleShape, color = Surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                onClick = { navController.navigate(Screen.Notifications.route) },
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = Ink, modifier = Modifier.size(19.dp))
                }
            }
            Box(
                Modifier
                    .offset(x = (-6).dp, y = 2.dp)
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(Tomato)
            )
        }
        Spacer(Modifier.width(8.dp))
        Surface(
            shape = CircleShape, color = GreenTint,
            onClick = { navController.navigate(Screen.Profile.route) },
            modifier = Modifier.size(38.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("JD", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = LeafDeep)
            }
        }
    }
}

@Composable
private fun HomeSearch(navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, Border),
            onClick = { navController.navigate(Screen.SearchResults.createRoute("pancit canton")) },
            modifier = Modifier.weight(1f).height(46.dp)
        ) {
            Row(
                Modifier.padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Search, contentDescription = null, tint = Muted, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(10.dp))
                Text("Search groceries", fontSize = 12.sp, color = Muted)
            }
        }
        Spacer(Modifier.width(10.dp))
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Leaf,
            onClick = { navController.navigate(Screen.SearchFilters.route) },
            modifier = Modifier.size(46.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Tune, contentDescription = "Filters", tint = Surface, modifier = Modifier.size(19.dp))
            }
        }
    }
}

@Composable
private fun HeroBanner(navController: NavController) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Brush.horizontalGradient(listOf(Leaf, Color(0xFF0E7A46))))
            .clickable { navController.navigate(Screen.BrowseCategory.createRoute("Noodles")) }
    ) {
        Row(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Fresh from the\npublic market", color = Surface, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, lineHeight = 20.sp)
                Spacer(Modifier.height(6.dp))
                Text("Same-day delivery in Irosin", color = Surface.copy(alpha = 0.75f), fontSize = 10.5.sp)
                Spacer(Modifier.height(10.dp))
                Text("Shop now  →", color = Accent, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
            Box(
                Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(Surface.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.ShoppingBasket, contentDescription = null,
                    tint = Surface, modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
private fun CategoriesSection(navController: NavController) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Categories", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Ink, modifier = Modifier.weight(1f))
        Text(
            "See all",
            color = Leaf, fontWeight = FontWeight.Bold, fontSize = 11.sp,
            modifier = Modifier.clickable { navController.navigate(Screen.AllCategories.route) }
        )
    }
    Column(Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        MockData.categories.chunked(4).forEach { rowCats ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                rowCats.forEach { cat ->
                    CategoryTile(cat, Modifier.weight(1f)) {
                        navController.navigate(Screen.BrowseCategory.createRoute(cat.name))
                    }
                }
            }
        }
    }
}

@Composable
private fun DealsSection(navController: NavController) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Today's deals", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Ink, modifier = Modifier.weight(1f))
        Text(
            "View all",
            color = Leaf, fontWeight = FontWeight.Bold, fontSize = 11.sp,
            modifier = Modifier.clickable { navController.navigate(Screen.BrowseCategory.createRoute("Noodles")) }
        )
    }
    Row(
        Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DealCard(MockData.byId("p_pancit"), "-20%", Modifier.weight(1f)) {
            navController.navigate(Screen.ProductDetail.createRoute("p_pancit"))
        }
        DealCard(MockData.byId("p_rice"), "-10%", Modifier.weight(1f)) {
            navController.navigate(Screen.ProductDetail.createRoute("p_rice"))
        }
    }
}

@Composable
fun DealCard(product: Product, badge: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        color = Surface,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
        onClick = onClick,
        modifier = modifier
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(104.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(tileColorOf(product.tileColor)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize().padding(14.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )
                Box(Modifier.align(Alignment.TopStart).padding(8.dp)) {
                    PromoPill(badge)
                }
            }
            Column(Modifier.padding(10.dp)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Ink, maxLines = 1)
                Spacer(Modifier.height(3.dp))
                PriceRow(product.price, product.oldPrice, priceSize = 13, oldSize = 9)
            }
        }
    }
}
