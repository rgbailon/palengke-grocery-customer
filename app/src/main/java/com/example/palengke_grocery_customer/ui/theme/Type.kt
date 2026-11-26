package com.example.palengke_grocery_customer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.palengke_grocery_customer.R

val Nunito = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold),
    Font(R.font.nunito_black, FontWeight.Black)
)

val PalengkeTypography = Typography(
    displayLarge = TextStyleEx(FontWeight.ExtraBold, 30, 37),
    displayMedium = TextStyleEx(FontWeight.ExtraBold, 26, 33),
    displaySmall = TextStyleEx(FontWeight.ExtraBold, 23, 29),
    headlineLarge = TextStyleEx(FontWeight.ExtraBold, 20, 26),
    headlineMedium = TextStyleEx(FontWeight.Bold, 18, 24),
    headlineSmall = TextStyleEx(FontWeight.Bold, 16, 22),
    titleLarge = TextStyleEx(FontWeight.Bold, 16, 22),
    titleMedium = TextStyleEx(FontWeight.SemiBold, 14, 20),
    titleSmall = TextStyleEx(FontWeight.SemiBold, 12, 17),
    bodyLarge = TextStyleEx(FontWeight.Normal, 14, 21),
    bodyMedium = TextStyleEx(FontWeight.Normal, 12, 18),
    bodySmall = TextStyleEx(FontWeight.Normal, 11, 15),
    labelLarge = TextStyleEx(FontWeight.Bold, 13, 18),
    labelMedium = TextStyleEx(FontWeight.SemiBold, 11, 16),
    labelSmall = TextStyleEx(FontWeight.SemiBold, 9, 13)
)

private fun TextStyleEx(weight: FontWeight, size: Int, line: Int) =
    androidx.compose.ui.text.TextStyle(
        fontFamily = Nunito,
        fontWeight = weight,
        fontSize = size.sp,
        lineHeight = line.sp
    )
