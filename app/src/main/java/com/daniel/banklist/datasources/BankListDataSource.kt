package com.daniel.banklist.datasources

import com.daniel.banklist.models.vos.ui.BankDataVO

interface BankListDataSource {
    fun getAllBanks() : List<BankDataVO>
}