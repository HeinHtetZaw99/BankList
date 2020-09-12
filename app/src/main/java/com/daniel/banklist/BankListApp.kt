package com.daniel.banklist

import android.app.Activity
import android.app.Application
import com.daniel.banklist.di.AppComponent
import com.daniel.banklist.di.DaggerAppComponent
import com.daniel.banklist.di.utils.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BankListApp : Application(), HasActivityInjector {

    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent? {
        return appComponent ?: createAppComponent()
    }


    override fun onCreate() {
        super.onCreate()
        appComponent = getAppComponent()
        appComponent!!.inject(this)
        AppInjector.initAutoInjection(this)
    }

    fun clearAppComponent() {
        showLogE("AppComponent has been slain! c(x_x)၁")
        appComponent = null
    }

    private fun createAppComponent(): AppComponent? {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    @Inject
    lateinit var dispatchingAndroidActivityInjector:
            DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidActivityInjector


}