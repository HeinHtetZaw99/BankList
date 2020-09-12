package com.daniel.banklist.adapters


import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.daniel.banklist.components.DiffUtils
import com.daniel.banklist.showLogD
import com.daniel.banklist.viewholders.BaseSelectableViewHolder
import java.util.*

abstract class BaseSelectableRecyclerAdapter<itemType, viewType : BaseSelectableViewHolder<itemType>>(
    private val context : Context
) :
    RecyclerView.Adapter<viewType>() {

    val inflater = LayoutInflater.from(context)

    protected var mData: MutableList<itemType>? = null

    var selectedPosition: Int = -1
        set(selectedPosition) {
            field = if (this.selectedPosition == selectedPosition) {
                -1
            } else {
                selectedPosition
            }
        }

    val items: List<itemType>
        get() = if (mData == null) ArrayList() else mData!!

    init {
        mData = ArrayList()
    }

    override fun onBindViewHolder(holder: viewType, position: Int) {
        showLogD("BaseSelectableRecyclerAdapter","selectedPosition : $selectedPosition and currentDataPosition : $position")
        if (mData!!.isNotEmpty()) {
            if (this.selectedPosition != -1)
                holder.setData(mData!![position], this.selectedPosition == position)
            else
                holder.setData(mData!![position], false)
        }
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun getItemAt(position: Int): itemType? {
        return if (position < mData!!.size + 1) mData!![position] else null

    }

    fun addNewData(newItem: itemType, position: Int) {
        if (mData != null) {
            mData!!.add(position, newItem)
            notifyDataSetChanged()
        }
    }

    fun getSelected() : itemType? {
        return if (selectedPosition == -1) null else items[selectedPosition]
    }

    fun update(newDataList: List<itemType>) {
        val diffResult = mData?.let { DiffUtils(it, newDataList) }?.let {
            DiffUtil.calculateDiff(
                it
            )
        }
        diffResult!!.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    fun appendNewData(newData: List<itemType>) {
        clearData()
        if (mData!!.isEmpty())
            mData!!.addAll(newData)
        else
            update(newData)
        notifyDataSetChanged()
    }

    fun removeData(data: itemType) {
        mData!!.remove(data)
        notifyDataSetChanged()
    }

    fun changeSelectedItem(currentSelectedPosition: Int) {
        notifyItemChanged(selectedPosition)
        selectedPosition = currentSelectedPosition
        notifyItemChanged(selectedPosition)
    }

    fun addNewData(data: itemType) {
        mData!!.add(data)
        notifyDataSetChanged()
    }

    fun addNewDataList(dataList: List<itemType>) {
        mData!!.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        mData = ArrayList()
        notifyDataSetChanged()
    }

}
