package com.example.palengke_grocery_customer.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

@Composable
fun SplashScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Leaf).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("PALENGKE", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
        Spacer(Modifier.height(4.dp))
        Text("Grocery ordering app", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        Text("Irosin, Sorsogon, Philippines", color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp)
        Spacer(Modifier.height(40.dp))
        Text("Cash on delivery · GCash · Maya", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Get started", { navController.navigate(Screen.Register.route) }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        Text("I already have an account", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        Text("Log in", color = Accent, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.clickable { navController.navigate(Screen.Login.route) })
        Spacer(Modifier.height(40.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            repeat(3) { i ->
                Box(Modifier.size(if (i == 0) 18.dp else 6.dp, 4.dp).background(if (i == 0) Accent else Color.White.copy(alpha = 0.35f), RoundedCornerShape(2.dp)))
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().background(Background).verticalScroll(rememberScrollState()).padding(24.dp)) {
        Spacer(Modifier.height(40.dp))
        Text("Welcome back", style = MaterialTheme.typography.displaySmall, color = OnSurface)
        Text("Log in to your Palengke account", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email or phone") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it }, label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = { IconButton(onClick = { showPass = !showPass }) { Icon(if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = null) } }
        )
        Spacer(Modifier.height(8.dp))
        Text("Forgot password?", color = Leaf, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.End).clickable { })
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Log in", { navController.navigate(Screen.Home.route) { popUpTo(Screen.Splash.route) { inclusive = true } } }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(Modifier.weight(1f))
            Text("  or  ", color = Muted, fontSize = 12.sp)
            HorizontalDivider(Modifier.weight(1f))
        }
        Spacer(Modifier.height(16.dp))
        OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(12.dp)) {
            Text("Continue with Google", color = OnSurface)
        }
        Spacer(Modifier.height(24.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("New to Palengke? ", color = OnSurfaceVariant)
            Text("Create an account", color = Leaf, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { navController.navigate(Screen.Register.route) })
        }
    }
}

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().background(Background).verticalScroll(rememberScrollState()).padding(24.dp)) {
        Spacer(Modifier.height(24.dp))
        Text("Create account", style = MaterialTheme.typography.displaySmall, color = OnSurface)
        Text("Join Palengke today", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone number") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Create account", { navController.navigate(Screen.Otp.route) }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Already have an account? ", color = OnSurfaceVariant)
            Text("Log in", color = Leaf, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { navController.navigate(Screen.Login.route) })
        }
    }
}

@Composable
fun OtpScreen(navController: NavController) {
    var code by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().background(Background).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(60.dp))
        Text("Verify your number", style = MaterialTheme.typography.displaySmall, color = OnSurface)
        Spacer(Modifier.height(8.dp))
        Text("We sent a 6-digit code to your phone", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant, textAlign = TextAlign.Center)
        Spacer(Modifier.height(40.dp))
        OutlinedTextField(
            value = code, onValueChange = { if (it.length <= 6) code = it }, label = { Text("Enter code") },
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Verify", { navController.navigate(Screen.Home.route) { popUpTo(Screen.Splash.route) { inclusive = true } } }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Text("Resend code", color = Leaf, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { })
    }
}