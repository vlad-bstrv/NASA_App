package com.vladbstrv.nasaapp.view.picture_galaxy.picture_mars

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.MarsPictureFragmentBinding

class MarsPictureFragment : Fragment() {

    private var _binding: MarsPictureFragmentBinding? = null
    private val binding: MarsPictureFragmentBinding get() = _binding!!


    private lateinit var viewModel: MarsPictureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MarsPictureFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarsPictureViewModel::class.java)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMarsPicture()
    }

    fun renderData(marsPictureState: MarsPictureState) {
        when (marsPictureState) {
            is MarsPictureState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.repeat_the_download)) {
                        viewModel.getMarsPicture()
                    }
                    .show()
            }
            is MarsPictureState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is MarsPictureState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                if(marsPictureState.serverResponseData.photos.isEmpty()){
                    Snackbar.make(binding.root, "В этот день curiosity не сделал ни одного снимка", Snackbar.LENGTH_SHORT).show()
                }else{
                    val url = marsPictureState.serverResponseData.photos.first().img_src
                    binding.imageView1.load(url)
                }
            }
        }
    }

}