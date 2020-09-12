package com.daniel.banklist.delegate

import com.daniel.banklist.models.vos.ui.BankDataVO

interface BankDataListDelegate {
    fun onClickedBank(data: BankDataVO)
}