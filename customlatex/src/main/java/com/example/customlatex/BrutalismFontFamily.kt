package com.example.customlatex

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

val BrutalismFontFamilyBalsamiq = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.balsamiqsans_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.balsamiqsans_regular,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        )
    )
)

val BrutalismFontFamilyRoboto = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.roboto_bold_font,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_medium_font,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_bold_font,
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_light,
            weight = FontWeight.Light,
            style = FontStyle.Normal
        )
    )
)