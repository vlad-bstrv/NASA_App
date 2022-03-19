package com.vladbstrv.nasaapp.view.layouts.constraint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.FragmentConstraintBinding


class ConstraintFragment : Fragment() {

    private var _binding: FragmentConstraintBinding? = null
    private val binding: FragmentConstraintBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConstraintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}