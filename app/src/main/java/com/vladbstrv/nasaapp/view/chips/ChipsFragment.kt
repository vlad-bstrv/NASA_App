package com.vladbstrv.nasaapp.view.chips

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.FragmentChipsBinding
import com.vladbstrv.nasaapp.databinding.PictureOfTheDayFragmentBinding
import com.vladbstrv.nasaapp.view.MainActivity

class ChipsFragment : Fragment() {

    private var _binding: FragmentChipsBinding? = null
    private val binding: FragmentChipsBinding get() = _binding!!

    companion object {
        fun newInstance() = ChipsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.chipGroup.findViewById<Chip>(checkedId)?.let {
                Toast.makeText(requireContext(), "chep $checkedId ${it.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}