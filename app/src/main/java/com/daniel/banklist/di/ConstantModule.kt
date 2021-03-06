package com.daniel.banklist.di


import com.daniel.banklist.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object ConstantModule {

    init {
        System.loadLibrary("keys")
    }

    private external fun getBaseProductionURL(): String?

    private external fun getBaseStagingURL(): String?

    private external fun getAppToken(): String?

    private external fun getAppClientSecret(): String?

    @Provides
    @Singleton
    @JvmStatic
    @Named("client_id")
    fun providesClientID(): String = "1"

    @Provides
    @Singleton
    @JvmStatic
    @Named("token")
    fun providesToken(): String = getAppToken()!!

    @Provides
    @Singleton
    @JvmStatic
    @Named("client_secret")
    fun providesClientSecret(): String = getAppClientSecret()!!


    @Provides
    @Singleton
    @JvmStatic
    @Named("api_key")
    fun providesApiKey(): String = "BuildConfig.API_KEY"


    @Provides
    @JvmStatic
    @Singleton
    @Named("base_url")
    fun providesBaseUrl(): String =
        if (BuildConfig.DEBUG) getBaseStagingURL()!! else getBaseProductionURL()!!
}