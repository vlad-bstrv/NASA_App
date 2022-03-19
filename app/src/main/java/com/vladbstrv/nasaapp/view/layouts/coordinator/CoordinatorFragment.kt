package com.vladbstrv.nasaapp.view.layouts.coordinator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.FragmentConstraintBinding
import com.vladbstrv.nasaapp.databinding.FragmentCoordinatorBinding
import com.vladbstrv.nasaapp.view.layouts.coordinator.coordinatorsExample.exampleOne.CoordinatorOneFragment


class CoordinatorFragment : Fragment() {

    private var _binding: FragmentCoordinatorBinding? = null
    private val binding: FragmentCoordinatorBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val behavior = ButtonBehavior(requireContext())
        (binding.myButton.layoutParams as CoordinatorLayout.LayoutParams).behavior = behavior

        binding.myButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("")
                .replace(R.id.container, CoordinatorOneFragment())
                .commit()
        }
    }


}