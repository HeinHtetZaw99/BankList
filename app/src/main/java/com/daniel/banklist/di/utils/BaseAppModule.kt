package com.daniel.banklist.di.utils

import android.app.Application

import com.daniel.banklist.components.impls.GenericErrorMessageFactoryImpl
import com.daniel.banklist.components.impls.NetworkExceptionMessageFactoryImpl
import com.daniel.banklist.components.interfaces.GenericErrorMessageFactory
import com.daniel.banklist.components.interfaces.NetworkExceptionMessageFactory
import com.daniel.banklist.components.rx.JobExecutor
import com.daniel.banklist.components.rx.UIThread
import com.daniel.banklist.components.rx.PostExecutionThread
import com.daniel.banklist.components.rx.ThreadExecutor

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [BaseAppModule.Provider::class, ViewModelFactoryModule::class])
abstract class BaseAppModule {

    @Binds
    abstract fun threadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Binds
    abstract fun postExecutionThread(uiThread: UIThread): PostExecutionThread

    @Binds
    abstract fun genericErrorMessageFactory(genericErrorMessageFactory: GenericErrorMessageFactoryImpl): GenericErrorMessageFactory

    @Binds
    abstract fun networkErrorMessageFactory(networkExceptionMessageFactory: NetworkExceptionMessageFactoryImpl): NetworkExceptionMessageFactory

    @Module
    object Provider {
        @Provides
        @JvmStatic
        @Singleton
        fun providesContext(application: Application) = application.applicationContext

    }

}