package com.daniel.banklist.viewholders


import android.view.View

import androidx.recyclerview.widget.RecyclerView


abstract class BaseSelectableViewHolder<W>(
    itemView: View,
    private val listener: RecyclerItemSelectedListener
) : RecyclerView.ViewHolder(itemView) {


    abstract fun setData(mData: W, isSelected: Boolean)

    interface RecyclerItemSelectedListener {
        fun onSelected(selectedPosition: Int)
    }
}
