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
    private var checkVisibilityGroup: Boolean = true

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPercent.setOnClickListener {
            if (checkVisibilityGroup) {
                binding.groupBtn.visibility = View.GONE
                checkVisibilityGroup = false
            } else {
                binding.groupBtn.visibility = View.VISIBLE
                checkVisibilityGroup = true
            }

        }
    }


}