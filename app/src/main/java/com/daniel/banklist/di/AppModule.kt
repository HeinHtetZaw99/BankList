package com.daniel.banklist.di


import com.daniel.banklist.di.utils.BaseAppModule

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [BaseAppModule::class, ConstantModule::class, ActivityModule::class, ViewModelModule::class, RepositoryModule::class, NetworkModule::class, ServiceModule::class])
class AppModule {

}
