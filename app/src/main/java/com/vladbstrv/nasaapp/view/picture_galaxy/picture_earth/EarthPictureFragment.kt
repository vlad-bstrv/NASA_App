package com.vladbstrv.nasaapp.view.picture_galaxy.picture_earth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbstrv.nasaapp.R

class EarthPictureFragment : Fragment() {

    companion object {
        fun newInstance() = EarthPictureFragment()
    }

    private lateinit var viewModel: EarthPictureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.earth_picture_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EarthPictureViewModel::class.java)
        // TODO: Use the ViewModel
    }

}