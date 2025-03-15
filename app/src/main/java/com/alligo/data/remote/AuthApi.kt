package com.alligo.data.remote

import com.alligo.data.model.auth.AccountResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {


    @FormUrlEncoded
    @POST("auth/login")
    suspend  fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("expiresInMins") expiresInMins: Int = 30
    ): AccountResponse
}