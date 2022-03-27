package com.vladbstrv.nasaapp.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladbstrv.nasaapp.databinding.FragmentRecyclerItemEarthBinding
import com.vladbstrv.nasaapp.databinding.FragmentRecyclerItemHeaderBinding
import com.vladbstrv.nasaapp.databinding.FragmnetRecyclerItemMarsBinding

class RecyclerAdapter(val onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

    private lateinit var listData: MutableList<Data>

    fun setData(listData: MutableList<Data>) {
        this.listData = listData
    }

    fun appendItem() {
        listData.add(generateItem())
        notifyItemInserted(listData.size - 1)
    }

    private fun generateItem() = Data("Mars", type = TYPE_MARS)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.BaseViewHolder {
        return when(viewType) {
            TYPE_EARTH -> {
                val binding = FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EarthViewHolder(binding.root)
            }
            TYPE_HEADER -> {
                val binding = FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding.root)
            }
            else -> {
                val binding = FragmnetRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MarsViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun getItemCount() = listData.size

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Data)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            FragmentRecyclerItemEarthBinding.bind(itemView).apply {
                tvName.text = data.name
                tvDescriptionItemEarth.text = data.description
                ivEarth.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            FragmnetRecyclerItemMarsBinding.bind(itemView).apply {
                tvName.text = data.name
                ivMars.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
                addItemImageView.setOnClickListener {
                    listData.add(layoutPosition, generateItem())
                    notifyItemInserted(layoutPosition)
                }
                removeItemImageView.setOnClickListener {
                    listData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            FragmentRecyclerItemHeaderBinding.bind(itemView).apply {
                tvName.text = data.name
                itemView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }
}