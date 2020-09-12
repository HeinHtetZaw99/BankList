package com.daniel.banklist.di

import javax.inject.Inject

class AppConstants  @Inject constructor(){

    init {
        System.loadLibrary("keys")
    }

    external fun getBaseURL(): String?

    external fun getAppToken(): String?

    external fun getAppClientSecret(): String?

    external fun getDynamicDomain(): String?

    external fun getDynamicLink(): String?

    external fun getCCPPToken(): String?
}