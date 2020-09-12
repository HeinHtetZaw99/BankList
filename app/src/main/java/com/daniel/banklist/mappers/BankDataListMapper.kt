package com.daniel.banklist.mappers

import com.daniel.banklist.models.vos.network.DataItem
import com.daniel.banklist.models.vos.ui.BankDataVO
import javax.inject.Inject

class BankDataListMapper @Inject constructor() :
    UnidirectionalMap<List<DataItem>, List<BankDataVO>> {
    override fun map(data: List<DataItem>): List<BankDataVO> {
        return data.map {
            return@map BankDataVO().apply {
                this.instruction2 = it.instruction2 ?: ""
                this.endColor = it.endColor ?: ""
                this.instruction1 = it.instruction1 ?: ""
                this.length = it.length ?: 0
                this.logoThumbnail = it.logoThumbnail ?: ""
                this.description = it.description ?: "Currently Not Available"
                this.type = it.type ?: "Not Specified"
                this.logoUrl = it.logoUrl ?: ""
                this.enabled = it.enabled ?: 0
                this.startColor = it.startColor ?: ""
                this.centerColor = it.centerColor ?: ""
                this.bgColor = it.bgColor ?: ""
                this.isMajor = false
                this.name = it.name ?: "Unknown"
                this.id = it.id ?: 0
                this.maxAmount = if(it.maxAmount == null || it.maxAmount == "" ){
                    "Not Specified"
                }else {
                    "${it.maxAmount} MMKs"
                }
                this.fontColor = it.fontColor  ?: ""
            }
        }
    }

}