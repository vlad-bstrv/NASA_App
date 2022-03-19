package com.vladbstrv.nasaapp.view.layouts.coordinator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.FragmentConstraintBinding
import com.vladbstrv.nasaapp.databinding.FragmentCoordinatorBinding


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


}