package com.daniel.banklist.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.daniel.banklist.R
import com.daniel.banklist.delegate.BankDataListDelegate
import com.daniel.banklist.models.vos.ui.BankDataVO
import com.daniel.banklist.viewholders.BankDataViewHolder

class BankListAdapter(private val context: Context) :
    BaseRecyclerAdapter<BankDataVO, BankDataViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankDataViewHolder {
        return BankDataViewHolder(
            DataBindingUtil.inflate(inflater, R.layout.cardview_bank_data, parent, false),
            context as BankDataListDelegate
        )
    }

}