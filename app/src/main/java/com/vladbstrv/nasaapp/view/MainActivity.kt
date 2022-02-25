package com.vladbstrv.nasaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.view.picture_of_the_day.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }
    }
}

