package com.vladbstrv.nasaapp.view.splash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.vladbstrv.nasaapp.BuildConfig
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.EarthPictureFragmentBinding
import com.vladbstrv.nasaapp.databinding.FragmentSplashBinding
import com.vladbstrv.nasaapp.databinding.MarsPictureFragmentBinding
import com.vladbstrv.nasaapp.view.picture_galaxy.picture_mars.MarsPictureState
import com.vladbstrv.nasaapp.view.picture_galaxy.picture_mars.MarsPictureViewModel

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSplash.animate().rotationBy(720f).setDuration(5000L).start()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}