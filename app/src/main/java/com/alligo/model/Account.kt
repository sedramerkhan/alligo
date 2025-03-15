package com.alligo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey
    val id: Long,
    val username: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val image: String,
    val lastName: String,
)