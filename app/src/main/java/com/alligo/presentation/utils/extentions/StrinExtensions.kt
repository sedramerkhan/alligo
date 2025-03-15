package com.alligo.presentation.utils.extentions

import com.alligo.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.isValidEmail(): String? {
    return when {
        this.isEmpty() -> {
            // Email field is empty
            "Email is required"
        }
        !android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches() -> {
            // Invalid email format
            "Invalid email format"
        }
        else -> null // No error, valid email
    }
}

// Extension function for password validation
fun String.isValidPassword(): Int? {
    return when {
        this.isEmpty() -> {
            // Password field is empty
           R.string.field_is_required
        }
        this.length < 8 -> {
            // Password is too short
           R.string.password_must_be_at_least_8_characters
        }
        else -> null // No error, valid password
    }
}


fun String.formatIso8601Date(): String {
    return try {
        // Parse the ISO 8601 date
        val instant = Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(this))

        // Format the date in a readable format
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH)
            .withZone(ZoneId.systemDefault())  // You can set a specific timezone, e.g., ZoneId.of("UTC")

        formatter.format(instant)
    } catch (e: Exception) {
        e.printStackTrace()
        "Invalid Date"
    }
}