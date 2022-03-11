package com.dienty.structure.base

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<DataType : BaseObject, ViewHolderType : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<ViewHolderType>() {
    open var eventListener: BaseEvent<DataType>? = null

    protected val differ: AsyncListDiffer<DataType> by lazy {
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<DataType>() {
            override fun areItemsTheSame(oldItem: DataType, newItem: DataType): Boolean {
                return this@BaseAdapter.areItemTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: DataType, newItem: DataType): Boolean {
                return this@BaseAdapter.areContentTheSame(oldItem, newItem)
            }

        })
    }


    protected open fun areItemTheSame(oldItem: BaseObject, newItem: BaseObject) =
        oldItem.id == newItem.id

    protected open fun areContentTheSame(oldItem: BaseObject, newItem: BaseObject) =
        oldItem == newItem


    fun bind(data: List<DataType>?, callback: Runnable? = null) {
        differ.submitList(data, callback)
    }

    fun add(data: DataType, callback: Runnable? = null) {
        val modifyData = differ.currentList.toMutableList()
        modifyData.add(data)
        differ.submitList(modifyData, callback)
    }

    fun removeLast(callback: Runnable? = null) {
        val modifyData = differ.currentList.toMutableList()
        if (modifyData.isNotEmpty()) {
            modifyData.removeLast()
            differ.submitList(modifyData, callback)
        } else {
            callback?.run()
        }
    }

    fun add(data: List<DataType>, isBind: Boolean = false, callback: Runnable? = null) {
        if (isBind) {
            bind(data, callback)
        } else {
            val mutableData = differ.currentList.toMutableList()
            mutableData.addAll(data)
            differ.submitList(mutableData, callback)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun size() = differ.currentList.size
    fun data(): List<DataType> = differ.currentList
}