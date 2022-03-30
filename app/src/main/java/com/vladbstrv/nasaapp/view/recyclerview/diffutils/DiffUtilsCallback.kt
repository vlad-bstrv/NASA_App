package com.vladbstrv.nasaapp.view.recyclerview.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.vladbstrv.nasaapp.view.recyclerview.Data

class DiffUtilsCallback(
    val oldItems: List<Pair<Data, Boolean>>,
    val newItems: List<Pair<Data, Boolean>>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].first.name == newItems[newItemPosition].first.name


    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return Change(oldItem, newItem)
    }
}