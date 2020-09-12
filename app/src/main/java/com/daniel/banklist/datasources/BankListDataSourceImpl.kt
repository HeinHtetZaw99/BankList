package com.daniel.banklist.datasources

import com.daniel.banklist.mappers.BankDataListMapper
import com.daniel.banklist.models.vos.ui.BankDataVO
import com.daniel.banklist.services.BankService
import javax.inject.Inject

class BankListDataSourceImpl @Inject constructor(
    private val bankService: BankService,
    private val bankDataListMapper: BankDataListMapper
) : BankListDataSource {
    override fun getAllBanks(): List<BankDataVO> {
        return bankDataListMapper.map(bankService.getBanks().execute().body()!!.data ?: ArrayList())
    }
}