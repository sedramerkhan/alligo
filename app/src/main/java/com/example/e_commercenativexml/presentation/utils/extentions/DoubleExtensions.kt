package com.example.e_commercenativexml.presentation.utils.extentions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Double.formatPrice(): String {
    val formatter = DecimalFormat("#,###,###.##", DecimalFormatSymbols(Locale.US))
    return "${formatter.format(this)} $"
}

fun Number.formatToEnglish(): String {
    val formatter = DecimalFormat("#,###.##", DecimalFormatSymbols(Locale.US))
    return formatter.format(this)
}