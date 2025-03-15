package com.alligo.presentation.utils.extentions

import com.alligo.R
import java.text.SimpleDateFormat
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


fun String.formatDate(): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
    return formatter.format(this)
}