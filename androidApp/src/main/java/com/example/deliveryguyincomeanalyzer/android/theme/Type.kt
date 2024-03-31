package com.example.deliveryguyincomeanalyzer.android.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val typography1 = Typography(
    headlineSmall = TextStyle(
       // fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    ),
    displayLarge= TextStyle(
        //fontWeight = FontWeight.SemiBold,
        fontSize = 110.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    titleLarge = TextStyle(
      //  fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)