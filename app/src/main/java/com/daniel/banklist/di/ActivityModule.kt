package com.daniel.banklist.di

import com.daniel.banklist.activities.BankDetailsActivity
import com.daniel.banklist.activities.HomeActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun bankDetailsActivity(): BankDetailsActivity

}