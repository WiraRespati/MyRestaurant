package com.wirarespati.myrestaurant.core.di

import androidx.room.Room
import com.wirarespati.myrestaurant.core.data.source.local.LocalDataSource
import com.wirarespati.myrestaurant.core.data.source.local.room.RestaurantDatabase
import com.wirarespati.myrestaurant.core.data.source.remote.RemoteDataSource
import com.wirarespati.myrestaurant.core.data.source.remote.network.ApiService
import com.wirarespati.myrestaurant.core.domain.repository.IRestaurantRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.concurrent.TimeUnit

const val BASE_URL = com.wirarespati.myrestaurant.core.BuildConfig.BASE_URL

val databaseModule = module {
    factory { get<RestaurantDatabase>().restaurantDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            RestaurantDatabase::class.java, "Restaurant.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}
val loggingInterceptor = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}
val networkModule = module {
    single {
        val url = URL(BASE_URL)
        val hostname = url.host
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/qRXOtBLL57LL0c7e8w/vou6FL8GMrasDduMdcQqXeBw=")
            .add(hostname, "sha256/9Z2HW92+oNyphNT4dff53t6jrOR+/V3O6WSeVWH+YjY=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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