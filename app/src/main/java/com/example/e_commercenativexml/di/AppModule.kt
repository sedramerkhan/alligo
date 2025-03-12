package com.example.e_commercenativexml.di

import android.app.Application
import android.content.Context
import com.example.e_commercenativexml.data.local.room.Room
import com.example.e_commercenativexml.data.local.room.dao.CartDao
import com.example.e_commercenativexml.data.remote.ProductApi
import com.example.e_commercenativexml.data.repository.CartRepository
import com.example.e_commercenativexml.data.repository.ProductRepository
import com.example.e_commercenativexml.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    private const val mainUrl = "https://dummyjson.com/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val interceptorClient = OkHttpClient.Builder()

            .addNetworkInterceptor { chain ->
                val builder = chain.request().newBuilder()
                val request = builder.build()
                chain.proceed(request)
            }

        return Retrofit.Builder()
            .baseUrl(mainUrl)
            .client(
                interceptorClient.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRoom(application: Application): Room =
        androidx.room.Room.databaseBuilder(application, Room::class.java, "Room")
            .build()


    @Singleton
    @Provides
    fun provideProductApi(
        retrofit: Retrofit
    ): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepo(
        productApi: ProductApi
    ): ProductRepository {
        return ProductRepository(productApi)
    }

    @Singleton
    @Provides
    fun provideCartItemDao(
        room: Room
    ): CartDao {
        return room.cartItemDao()
    }

    @Singleton
    @Provides
    fun provideCartRepo(
        cartDao: CartDao
    ): CartRepository {
        return CartRepository(cartDao)
    }

}
