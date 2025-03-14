package com.alligo.presentation.utils.extentions

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
fun String.isValidPassword(): String? {
    return when {
        this.isEmpty() -> {
            // Password field is empty
            "Password is required"
        }
        this.length < 8 -> {
            // Password is too short
            "Password must be at least 8 characters"
        }
        else -> null // No error, valid password
    }
}
