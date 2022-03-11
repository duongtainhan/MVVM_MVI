package com.dienty.structure.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface BaseEvent<T> {
    fun onClickView(view: View?)
    fun onClickView(position: Int, view: View?, data: T)
    fun onClickedItem(position: Int)
    fun onClickedItem(position: Int, data: T)
    fun onLoadMore()
    fun onLongClickedItem(position: Int, view: View)
    fun onSelectedItem(position: Int)
    fun onSelectedItem(position: Int, data: T)
    fun onSelectedItem(position: Int, data: T?, viewHolder: RecyclerView.ViewHolder?)
}