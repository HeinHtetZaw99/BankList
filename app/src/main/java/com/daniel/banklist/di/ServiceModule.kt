package com.daniel.banklist.di

import androidx.annotation.NonNull
import com.daniel.banklist.services.BankService
import com.daniel.banklist.showLogD
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named

@Module(includes = [ServiceModule.Providers::class])
abstract class ServiceModule {

    @Module
    object Providers {

        @JvmStatic
        @NonNull
        @Provides
        fun provideLoginService(@Named("authenticatedBuilder") retrofitBuilder: Retrofit.Builder): BankService {
            return retrofitBuilder.build().create(BankService::class.java)
        }

        @JvmStatic
        @NonNull
        @Named("authenticatedBuilder")
        @Provides
        fun getAuthenticatedBuilder(
            @Named("token") token: String?,
            @Named("client_secret") clientSecret: String?,
            httpClientBuilder: OkHttpClient.Builder,
            @Named("primary") retrofitBuilder: Retrofit.Builder
        ): Retrofit.Builder {
            showLogD("NETWORK_MODULE", "TOKEN before interception : $token")
            val interceptor: Interceptor =
                AuthenticationInterceptor(null, token).apply {
                    setCustom("secret-key",null,clientSecret)
                }
            if (!httpClientBuilder.interceptors().contains(interceptor)) {
                httpClientBuilder.addInterceptor(interceptor)
            }
            return retrofitBuilder.client(httpClientBuilder.build())

        }
    }


}