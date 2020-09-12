package com.daniel.banklist.di

import androidx.lifecycle.ViewModel

import com.daniel.banklist.di.utils.ViewModelKey
import com.daniel.banklist.viewmodels.MainViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun homeViewModel(
        homeViewModel: MainViewModel
    ): ViewModel

}
