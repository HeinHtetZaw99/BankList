package com.daniel.banklist.viewholders

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.daniel.banklist.components.ClickGuard
import com.daniel.banklist.databinding.CardviewBankDataBinding
import com.daniel.banklist.delegate.BankDataListDelegate
import com.daniel.banklist.models.vos.ui.BankDataVO
import com.daniel.banklist.show

class BankDataViewHolder(
    private val binding: CardviewBankDataBinding, private val delegate: BankDataListDelegate
) : BaseViewHolder<BankDataVO>(binding.root) {
    override fun setData(data: BankDataVO) {
        binding.data = data

        if (data.startColor != "" && data.centerColor != "" && data.endColor != "")
            binding.bankDetailsCardLayout.background =
                getGradientDrawable(data.startColor, data.centerColor, data.endColor)

        binding.logoIv.show(data.logoUrl)

        /*      if (data.bgColor != "")
                  binding.logoIv.background =
                      getGradientDrawable(data.bgColor, data.bgColor, data.bgColor)*/

        if (data.fontColor != "") {
            binding.bankDetailsTitleTv.setTextColor(Color.parseColor(data.fontColor))
            binding.bankDetailsTypeTv.setTextColor(Color.parseColor(data.fontColor))
        }

        binding.bankDetailsCardLayout.setOnClickListener {
            delegate.onClickedBank(
                data
            )
        }
        ClickGuard.guard(binding.bankDetailsCardLayout)

    }

    private fun getGradientDrawable(start: String, center: String, end: String): Drawable {
        return GradientDrawable(
            GradientDrawable.Orientation.BL_TR,
            intArrayOf(
                Color.parseColor(start),
                Color.parseColor(center),
                Color.parseColor(end)
            )
        )
    }
}