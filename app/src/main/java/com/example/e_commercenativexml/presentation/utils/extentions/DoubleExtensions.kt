package com.example.e_commercenativexml.presentation.utils.extentions

import java.text.DecimalFormat

fun Double.formatPrice(): String {
    val formatter = DecimalFormat("#,###,###")
    return "${formatter.format(this)} $"
}
