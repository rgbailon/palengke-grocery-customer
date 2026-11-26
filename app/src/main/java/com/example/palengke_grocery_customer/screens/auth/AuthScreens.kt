package com.example.palengke_grocery_customer.screens.auth

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
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.ChevronRight
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
import com.example.palengke_grocery_customer.R
import com.example.palengke_grocery_customer.navigation.Screen
import com.example.palengke_grocery_customer.ui.components.*
import com.example.palengke_grocery_customer.ui.theme.*

// ─────────────────────────── A01 Splash / onboarding ───────────────────────────

@Composable
fun SplashScreen(navController: NavController) {
    GreenScreenBox(Modifier.statusBarsPadding()) {
        Column(
            Modifier.fillMaxSize().padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(64.dp))
            Box(
                Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Surface.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.ShoppingBasket, contentDescription = null,
                    tint = Surface, modifier = Modifier.size(30.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "PALENGKE",
                color = Surface, fontSize = 28.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp
            )
            Spacer(Modifier.height(6.dp))
            Text("Grocery delivery in your barangay", color = Surface.copy(alpha = 0.85f), fontSize = 12.sp)
            Text("Irosin, Sorsogon · Same-day delivery", color = Surface.copy(alpha = 0.6f), fontSize = 11.sp)
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Surface.copy(alpha = 0.10f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.ShoppingBasket, contentDescription = null,
                    tint = Surface.copy(alpha = 0.9f), modifier = Modifier.size(72.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                "Cash on delivery · GCash · Maya",
                color = Surface.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(18.dp))
            Button(
                onClick = { navController.navigate(Screen.Register.route) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Surface, contentColor = Leaf)
            ) {
                Text("Get started", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
            }
            Spacer(Modifier.height(14.dp))
            Text("I already have an account", color = Surface.copy(alpha = 0.75f), fontSize = 12.sp)
            Text(
                "Log in",
                color = Accent, fontWeight = FontWeight.Bold, fontSize = 12.sp,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Login.route)
                }
            )
            Spacer(Modifier.height(20.dp))
            PageDots(active = 0)
            Spacer(Modifier.height(18.dp))
        }
    }
}

// ─────────────────────────── A02 Log in ───────────────────────────

@Composable
fun LoginScreen(navController: NavController) {
    var phone by remember { mutableStateOf("917 123 4567") }
    var password by remember { mutableStateOf("password") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Ink)
        }
        Spacer(Modifier.height(10.dp))
        Text("Welcome back", style = MaterialTheme.typography.displaySmall, color = Ink)
        Spacer(Modifier.height(4.dp))
        Text("Sign in to start shopping", fontSize = 12.sp, color = OnSurfaceVariant)
        Spacer(Modifier.height(26.dp))
        PhoneField(phone, { phone = it })
        Spacer(Modifier.height(16.dp))
        PasswordField(password, { password = it })
        Spacer(Modifier.height(6.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                "Forgot password?",
                color = Leaf, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { }
                    .padding(6.dp)
            )
        }
        Spacer(Modifier.height(14.dp))
        PrimaryButton("Sign in", {
            navController.navigate(Screen.Home.route) { popUpTo(Screen.Splash.route) { inclusive = true } }
        }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(Modifier.weight(1f), thickness = 1.dp, color = Border)
            Text("  or continue with  ", fontSize = 10.sp, color = Muted)
            HorizontalDivider(Modifier.weight(1f), thickness = 1.dp, color = Border)
        }
        Spacer(Modifier.height(20.dp))
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, Border),
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Row(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(
                    painterResource(R.drawable.google_g),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text("Continue with Google", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Ink)
            }
        }
        Spacer(Modifier.height(26.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("New to Palengke? ", fontSize = 12.sp, color = OnSurfaceVariant)
            Text(
                "Create an account",
                color = Leaf, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate(Screen.Register.route) }
            )
        }
        Spacer(Modifier.height(18.dp))
        Text(
            "By continuing you agree to our Terms and Privacy Policy",
            fontSize = 9.5.sp, color = Muted, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
    }
}

// ─────────────────────────── A03 Create account ───────────────────────────

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("Juan Dela Cruz") }
    var phone by remember { mutableStateOf("917 123 4567") }
    var password by remember { mutableStateOf("password") }
    var agreed by remember { mutableStateOf(true) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Ink)
        }
        Text("Create account", style = MaterialTheme.typography.displaySmall, color = Ink)
        Spacer(Modifier.height(4.dp))
        Text("Fresh groceries, delivered same day", fontSize = 12.sp, color = OnSurfaceVariant)
        Spacer(Modifier.height(16.dp))
        Surface(color = GreenTint, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.LocalOffer, contentDescription = null, tint = Leaf, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    buildString {
                        append("₱50 off your first order")
                        append("  ·  code FRESH50")
                    },
                    fontSize = 11.sp,
                    color = LeafDeep
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        LabeledField("Full name", name, { name = it }, hint = "Juan Dela Cruz")
        Spacer(Modifier.height(14.dp))
        PhoneField(phone, { phone = it })
        Spacer(Modifier.height(14.dp))
        PasswordField(password, { password = it })
        Spacer(Modifier.height(14.dp))
        LabeledField("Barangay", "Poblacion", {}, hint = "Barangay",
            trailing = {
                Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(18.dp))
            })
        Spacer(Modifier.height(14.dp))
        CheckRow("I agree to the Terms & Privacy Policy", agreed, { agreed = !agreed })
        Spacer(Modifier.height(16.dp))
        PrimaryButton("Create account", {
            navController.navigate(Screen.Otp.route)
        }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(18.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Already have an account?  ", fontSize = 12.sp, color = OnSurfaceVariant)
            Text(
                "Sign in",
                color = Leaf, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate(Screen.Login.route) }
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

// ─────────────────────────── A04 OTP verification ───────────────────────────

@Composable
fun OtpScreen(navController: NavController) {
    val code = "4829"

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Ink)
            }
        }
        Spacer(Modifier.height(8.dp))
        Box(
            Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(GreenTint),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Security, contentDescription = null, tint = Leaf, modifier = Modifier.size(34.dp))
        }
        Spacer(Modifier.height(18.dp))
        Text("Verify your number", style = MaterialTheme.typography.headlineLarge, color = Ink)
        Spacer(Modifier.height(6.dp))
        Text("We sent a 6-digit code to", fontSize = 12.sp, color = OnSurfaceVariant)
        Text("0917 123 4567", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
        Spacer(Modifier.height(22.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val digits = "4829".map { it.toString() }
            digits.forEach { d ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Surface,
                    border = androidx.compose.foundation.BorderStroke(1.4.dp, Leaf),
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(d, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                    }
                }
            }
            // cursor box
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Surface,
                border = androidx.compose.foundation.BorderStroke(1.4.dp, Border),
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        Modifier
                            .width(2.dp)
                            .height(22.dp)
                            .background(Leaf)
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Surface,
                border = androidx.compose.foundation.BorderStroke(1.4.dp, Border),
                modifier = Modifier.size(46.dp)
            ) {}
        }
        Spacer(Modifier.height(18.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Didn't get the code? ", fontSize = 11.sp, color = OnSurfaceVariant)
            Text("Resend in 0:24", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Leaf)
        }
        Spacer(Modifier.height(26.dp))
        PrimaryButton("Verify & continue", {
            navController.navigate(Screen.Home.route) { popUpTo(Screen.Splash.route) { inclusive = true } }
        }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        Text(
            "Change number",
            color = Leaf, fontWeight = FontWeight.Bold, fontSize = 12.sp,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Spacer(Modifier.height(24.dp))
        InfoCard(
            icon = Icons.Outlined.Schedule,
            iconTint = Grain,
            bg = Cream,
            title = "This code expires in 10 minutes",
            lines = listOf(
                "For your security, never share your code.",
                "Palengke will never ask for it by call."
            )
        )
        Spacer(Modifier.height(20.dp))
        Text(
            "Trouble verifying? Chat with support",
            fontSize = 11.sp, color = Muted, fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(20.dp))
    }
}
