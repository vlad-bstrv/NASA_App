package com.vladbstrv.nasaapp.view.picture_galaxy.picture_earth

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
import com.vladbstrv.nasaapp.databinding.MarsPictureFragmentBinding
import com.vladbstrv.nasaapp.view.picture_galaxy.picture_mars.MarsPictureState
import com.vladbstrv.nasaapp.view.picture_galaxy.picture_mars.MarsPictureViewModel

class EarthPictureFragment : Fragment() {

    private var _binding: EarthPictureFragmentBinding? = null
    private val binding: EarthPictureFragmentBinding get() = _binding!!
    private var flag = false

    private lateinit var viewModel: EarthPictureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EarthPictureFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EarthPictureViewModel::class.java)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getEpic()

        binding.imageViewEarth.setOnClickListener {
            zoomImageView()
        }
    }

    private fun zoomImageView() {
        val changeBounds = ChangeImageTransform()
        TransitionManager.beginDelayedTransition(binding.container, changeBounds)

        flag = !flag
        binding.imageViewEarth.scaleType =
            if (flag) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.CENTER_INSIDE
    }

    fun renderData(earthPictureState: EarthPictureState) {
        when (earthPictureState) {
            is EarthPictureState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.repeat_the_download)) {
                        viewModel.getEpic()
                    }
                    .show()
            }
            is EarthPictureState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is EarthPictureState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                val strDate = earthPictureState.serverResponseData.last().date.split(" ").first()
                val image = earthPictureState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-", "/", true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.imageViewEarth.load(url)

            }
        }
    }

}