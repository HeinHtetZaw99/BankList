package com.daniel.banklist.di


import android.content.Context
import com.daniel.banklist.BuildConfig
import com.daniel.banklist.components.exception.NetworkExceptionInterceptor
import com.daniel.banklist.showLogE
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = [NetworkModule.Providers::class])
abstract class NetworkModule {

    @Module
    object Providers {
        @JvmStatic
        @Provides
        fun providesOkHttpClientBuilder(context: Context): OkHttpClient.Builder {
            return OkHttpClient.Builder().apply {

                val loggerInterceptor = HttpLoggingInterceptor().apply {
                    level = when (BuildConfig.DEBUG) {
                        true -> HttpLoggingInterceptor.Level.BODY
                        false -> HttpLoggingInterceptor.Level.NONE
                    }
                }

                addInterceptor(loggerInterceptor)
                    .addInterceptor(ChuckInterceptor(context))

                    .addInterceptor(NetworkExceptionInterceptor())
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .cache(null)
            }
        }


        @JvmStatic
        @Provides
        @Named("primary")
        fun providesPrimaryRetrofitBuilder(
            gson: Gson,
            @Named("base_url") baseUrl: String
        ): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        }


        @JvmStatic
        @Provides
        @Singleton
        fun gson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient()
            .create()


    }
}