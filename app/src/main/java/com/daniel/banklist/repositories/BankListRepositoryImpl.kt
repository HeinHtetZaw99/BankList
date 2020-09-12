package com.daniel.banklist.repositories

import com.daniel.banklist.datasources.BankListDataSource
import com.daniel.banklist.models.vos.ui.BankDataVO
import io.reactivex.Observable
import javax.inject.Inject

class BankListRepositoryImpl @Inject constructor(
    private val bankListDataSource: BankListDataSource
) : BankListRepository{
    override fun getAllBanks(): Observable<List<BankDataVO>> {
        return Observable.fromCallable { bankListDataSource.getAllBanks() }
    }

}