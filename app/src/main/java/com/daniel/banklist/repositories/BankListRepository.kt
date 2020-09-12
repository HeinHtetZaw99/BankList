package com.daniel.banklist.repositories

import com.daniel.banklist.models.vos.ui.BankDataVO
import io.reactivex.Observable

interface BankListRepository {
    fun getAllBanks() : Observable<List<BankDataVO>>
}