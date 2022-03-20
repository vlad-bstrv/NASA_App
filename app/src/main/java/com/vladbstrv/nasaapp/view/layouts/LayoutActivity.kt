package com.vladbstrv.nasaapp.view.layouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.ActivityLayoutBinding
import com.vladbstrv.nasaapp.view.layouts.constraint.ConstraintFragment
import com.vladbstrv.nasaapp.view.layouts.coordinator.CoordinatorFragment
import com.vladbstrv.nasaapp.view.layouts.motion.MotionFragment

class LayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bottom_constraint -> {
                    navigateTo(ConstraintFragment())
                    true
                }
                R.id.bottom_coordinator -> {
                    navigateTo(CoordinatorFragment())
                    true
                }
                R.id.bottom_motion -> {
                    navigateTo(MotionFragment())
                    true
                }
                else -> true
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_constraint
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}