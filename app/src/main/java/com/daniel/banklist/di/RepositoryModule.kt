package com.daniel.banklist.di

import com.daniel.banklist.datasources.BankListDataSource
import com.daniel.banklist.datasources.BankListDataSourceImpl
import com.daniel.banklist.repositories.BankListRepository
import com.daniel.banklist.repositories.BankListRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun getBankListRepository(bankListRepository: BankListRepositoryImpl): BankListRepository

    @Binds
    abstract fun getBankListDataSource(bankListDataSourceImpl: BankListDataSourceImpl): BankListDataSource
}