package com.vladbstrv.nasaapp.view.layouts.coordinator.coordinatorsExample.exampleOne

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.FragmentConstraintBinding
import com.vladbstrv.nasaapp.databinding.FragmentCoordinatorBinding
import com.vladbstrv.nasaapp.databinding.FragmentCoordinatorOneBinding
import com.vladbstrv.nasaapp.view.layouts.coordinator.ButtonBehavior


class CoordinatorOneFragment : Fragment() {

    private var _binding: FragmentCoordinatorOneBinding? = null
    private val binding: FragmentCoordinatorOneBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoordinatorOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}