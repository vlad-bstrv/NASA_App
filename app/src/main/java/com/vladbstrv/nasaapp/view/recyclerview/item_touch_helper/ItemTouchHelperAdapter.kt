package com.vladbstrv.nasaapp.view.recyclerview.item_touch_helper

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}