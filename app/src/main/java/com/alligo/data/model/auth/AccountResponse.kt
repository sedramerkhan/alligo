package com.alligo.data.model.auth

import com.alligo.data.local.room.dao.AccountDao
import com.alligo.data.model.product.MetaResponse
import com.alligo.model.Account
import com.alligo.model.product.Meta

data class AccountResponse(
    val accessToken: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val refreshToken: String,
    val username: String
)


val AccountResponse.asDomainModel: Account
    get() = Account(
        id = id.toLong(),
        username = username,
        lastName = lastName,
        image = image,
        gender = gender,
        firstName = firstName,
        email = email
    )