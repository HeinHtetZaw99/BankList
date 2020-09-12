package com.daniel.banklist.adapters


import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.daniel.banklist.components.DiffUtils
import com.daniel.banklist.showLogD

import com.daniel.banklist.viewholders.BaseViewHolder
import java.util.*

/**Created by Daniel McCoy @ 25th Feb 2020*/
abstract class BaseRecyclerAdapter<itemType, viewType : BaseViewHolder<itemType>>(private val context: Context) :
    RecyclerView.Adapter<viewType>() {

    val inflater = LayoutInflater.from(context)
    protected var dataList: MutableList<itemType>? = null
    protected var updatedIndex: Int = -1


    val items: List<itemType>
        get() = if (dataList == null) ArrayList() else dataList!!

    init {
        dataList = ArrayList()

    }


    override fun onBindViewHolder(holder: viewType, position: Int) {
        if (dataList!!.isNotEmpty()) {
            holder.setData(dataList!![position])
        }
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    fun getItemAt(position: Int): itemType? {
        showLogD("Data List : $dataList")
        return if (position < dataList!!.size - 1) dataList!![position] else null

    }

    fun addNewData(newItem: itemType, position: Int) {
        if (dataList != null) {
            dataList!!.add(position, newItem)
            notifyDataSetChanged()
        }
    }

    open fun update(newDataList: List<itemType>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtils(newDataList, dataList!!))
        dataList!!.clear()
        dataList!!.addAll(newDataList)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }


    open fun appendDataAndUpdate(newData: List<itemType>) {
        clearData()
        if (dataList!!.isEmpty()) {
            dataList!!.addAll(newData)
            notifyDataSetChanged()
        } else
            update(newData)

    }

    fun appendNewData(newData: List<itemType>) {
        clearData()
        dataList!!.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeItemAt(position: Int) {
        this.dataList!!.removeAt(position)
        notifyDataSetChanged()
    }

    fun removeData(data: itemType) {
        this.dataList!!.remove(data)
        notifyDataSetChanged()
    }

    open fun addNewData(data: itemType) {
        this.dataList!!.add(data)
        notifyDataSetChanged()
        notifyItemInserted(dataList!!.lastIndex)
    }

    fun addNewDataList(dataList: List<itemType>) {
        this.dataList!!.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        dataList = ArrayList()
        notifyDataSetChanged()
    }


    fun updateItemAt(index: Int, item: itemType) {
        dataList!![index] = item
        notifyItemChanged(index)
    }

}
