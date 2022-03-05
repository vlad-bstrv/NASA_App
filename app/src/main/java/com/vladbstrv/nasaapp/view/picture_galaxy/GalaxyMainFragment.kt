package com.vladbstrv.nasaapp.view.picture_galaxy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.vladbstrv.nasaapp.databinding.FragmentGalaxyMainBinding

class GalaxyMainFragment : Fragment() {

    private var _binding: FragmentGalaxyMainBinding? = null
    private val binding: FragmentGalaxyMainBinding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalaxyMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity())

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = when(position) {
                    0 -> "Earth"
                    1 -> "Mars"
                    2 -> "Moon"
                    else -> "Earth"
                }
            }.attach()
    }

}