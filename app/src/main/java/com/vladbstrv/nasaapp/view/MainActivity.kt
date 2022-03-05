package com.vladbstrv.nasaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.ActivityMainBinding
import com.vladbstrv.nasaapp.view.chips.ChipsFragment
import com.vladbstrv.nasaapp.view.picture_of_the_day.PictureOfTheDayFragment

const val ThemeOne = 1
const val ThemeTwo = 2
const val ThemeThree = 3

class MainActivity : AppCompatActivity() {

    private val KEY_SP = "KEY_SP"
    private val KEY_CURRENT_THEME = "KEY_CURRENT_THEME"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigationView()

    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_view_main -> {
                    navigationTo(PictureOfTheDayFragment())
                    true
                }
                R.id.bottom_view_photo_system -> {
                    true
                }
                R.id.bottom_view_settings -> {
                    navigationTo(ChipsFragment())
                    true
                }
                else -> true
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_main
    }

    private fun navigationTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeOne -> R.style.MyOrangeTheme
            ThemeTwo -> R.style.MyTealTheme
            ThemeThree -> R.style.MyCyanTheme
            else -> 0
        }
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }


}

