package com.example.palengke_grocery_customer.screens.extras

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.palengke_grocery_customer.data.MockData
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

@Composable
fun FavoritesScreen(navController: NavController) {
    Scaffold(topBar = { PalengkeTopBar("Favorites", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            items(MockData.products.take(4)) { p ->
                Row(Modifier.fillMaxWidth().clickable { navController.navigate(Screen.ProductDetail.createRoute(p.id)) }.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model = p.imageUrl, contentDescription = null, modifier = Modifier.size(64.dp).clip(RoundedCornerShape(10.dp)), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(p.name, fontWeight = FontWeight.SemiBold, color = OnSurface)
                        Text("₱${p.price.toInt()} / ${p.unit}", color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    IconButton(onClick = { }) { Icon(Icons.Default.Favorite, contentDescription = null, tint = Error) }
                }
                HorizontalDivider(color = Border)
            }
        }
    }
}

@Composable
fun ChatScreen(navController: NavController) {
    var input by remember { mutableStateOf("") }
    Scaffold(topBar = { PalengkeTopBar("Chat with support", onBack = { navController.popBackStack() }) }) { padding ->
        Column(Modifier.fillMaxSize().background(Background).padding(padding)) {
            LazyColumn(Modifier.weight(1f).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(MockData.chat) { msg ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = if (msg.fromMe) Arrangement.End else Arrangement.Start) {
                        Surface(color = if (msg.fromMe) Leaf else Surface, shape = RoundedCornerShape(12.dp), shadowElevation = if (msg.fromMe) 0.dp else 1.dp) {
                            Column(Modifier.padding(10.dp)) {
                                Text(msg.text, color = if (msg.fromMe) Color.White else OnSurface, fontSize = 13.sp)
                                Text(msg.time, color = if (msg.fromMe) Color.White.copy(alpha = 0.7f) else Muted, fontSize = 9.sp)
                            }
                        }
                    }
                }
            }
            Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(value = input, onValueChange = { input = it }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(20.dp), placeholder = { Text("Type a message...") })
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = { input = "" }, modifier = Modifier.background(Leaf, RoundedCornerShape(20.dp))) {
                    Icon(Icons.Default.Send, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun HelpFaqScreen(navController: NavController) {
    Scaffold(topBar = { PalengkeTopBar("Help & FAQ", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            items(MockData.faqs) { faq ->
                var expanded by remember { mutableStateOf(false) }
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Surface), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { expanded = !expanded }) {
                    Column(Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(faq.q, fontWeight = FontWeight.SemiBold, color = OnSurface, modifier = Modifier.weight(1f))
                            Icon(if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null, tint = Muted)
                        }
                        if (expanded) {
                            Spacer(Modifier.height(8.dp))
                            Text(faq.a, fontSize = 12.sp, color = OnSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    var darkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    Scaffold(topBar = { PalengkeTopBar("Settings", onBack = { navController.popBackStack() }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().background(Background).padding(padding), contentPadding = PaddingValues(16.dp)) {
            item {
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Surface)) {
                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DarkMode, contentDescription = null, tint = Leaf)
                        Spacer(Modifier.width(12.dp))
                        Text("Dark mode", color = OnSurface, modifier = Modifier.weight(1f))
                        Switch(checked = darkMode, onCheckedChange = { darkMode = it })
                    }
                    HorizontalDivider(color = Border)
                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = Leaf)
                        Spacer(Modifier.width(12.dp))
                        Text("Push notifications", color = OnSurface, modifier = Modifier.weight(1f))
                        Switch(checked = notifications, onCheckedChange = { notifications = it })
                    }
                    HorizontalDivider(color = Border)
                    Row(Modifier.fillMaxWidth().clickable { }.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = Leaf)
                        Spacer(Modifier.width(12.dp))
                        Text("Language", color = OnSurface, modifier = Modifier.weight(1f))
                        Text("English", color = Muted, fontSize = 12.sp)
                    }
                    HorizontalDivider(color = Border)
                    Row(Modifier.fillMaxWidth().clickable { }.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Leaf)
                        Spacer(Modifier.width(12.dp))
                        Text("Change password", color = OnSurface, modifier = Modifier.weight(1f))
                    }
                    HorizontalDivider(color = Border)
                    Row(Modifier.fillMaxWidth().clickable { }.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Leaf)
                        Spacer(Modifier.width(12.dp))
                        Text("About", color = OnSurface, modifier = Modifier.weight(1f))
                        Text("v1.0", color = Muted, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}