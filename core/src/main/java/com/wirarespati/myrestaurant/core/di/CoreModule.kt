package com.wirarespati.myrestaurant.core.di

import android.os.Build
import androidx.room.Room
import com.wirarespati.myrestaurant.core.data.source.local.LocalDataSource
import com.wirarespati.myrestaurant.core.data.source.local.room.RestaurantDatabase
import com.wirarespati.myrestaurant.core.data.source.remote.RemoteDataSource
import com.wirarespati.myrestaurant.core.data.source.remote.network.ApiService
import com.wirarespati.myrestaurant.core.domain.repository.IRestaurantRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



val databaseModule = module {
    factory { get<RestaurantDatabase>().restaurantDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            RestaurantDatabase::class.java, "Restaurant.db"
        ).fallbackToDestructiveMigration().build()
    }
}
const val BASE_URL = com.wirarespati.myrestaurant.core.BuildConfig.BASE_URL
val loggingInterceptor = if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) }
val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IRestaurantRepository> {
        com.wirarespati.myrestaurant.core.data.RestaurantRepository(
            get(),
            get()
        )
    }
}