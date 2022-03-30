package com.vladbstrv.nasaapp.view.recyclerview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.FragmentRecyclerBinding
import com.vladbstrv.nasaapp.databinding.MarsPictureFragmentBinding
import com.vladbstrv.nasaapp.view.recyclerview.item_touch_helper.ItemTouchHelperCallback

class RecyclerFragment : Fragment() {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding: FragmentRecyclerBinding get() = _binding!!
    private var flag = false
    lateinit var itemTouchHelper: ItemTouchHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arrayListOf(

            Pair(Data(getString(R.string.mars), type = TYPE_MARS), false),
        )
        data.shuffle()

        data.add(0, Pair(Data("Header", type = TYPE_HEADER), false))

        val adapter = RecyclerAdapter(object : OnListItemClickListener {
            override fun onItemClick(data: Data) {
                Toast.makeText(requireContext(), data.name, Toast.LENGTH_SHORT).show()
            }
        },
        object : OnStartDragListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }

        })

        adapter.setData(data)
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener { adapter.appendItem() }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}