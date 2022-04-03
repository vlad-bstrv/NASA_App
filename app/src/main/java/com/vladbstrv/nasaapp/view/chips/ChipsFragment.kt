package com.vladbstrv.nasaapp.view.chips

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.vladbstrv.nasaapp.view.ThemeOne
import com.vladbstrv.nasaapp.view.ThemeThree
import com.vladbstrv.nasaapp.view.ThemeTwo
import com.vladbstrv.nasaapp.view.ux.UxButtonFragment
import com.vladbstrv.nasaapp.view.ux.UxTextFragment

class ChipsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentChipsBinding? = null
    private val binding: FragmentChipsBinding get() = _binding!!
    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
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
        binding.rbtnOrangeTheme.setOnClickListener(this)
        binding.rbtnTealTheme.setOnClickListener(this)
        binding.rbtnCyanTheme.setOnClickListener(this)

        when (parentActivity.getCurrentTheme()) {
            1 -> binding.radioGroup.check(R.id.rbtnOrangeTheme)
            2 -> binding.radioGroup.check(R.id.rbtnTealTheme)
            3 -> binding.radioGroup.check(R.id.rbtnCyanTheme)
        }

        binding.btnUXText.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, UxTextFragment())
                .addToBackStack("")
                .commit()
        }

        binding.btnUXButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, UxButtonFragment())
                .addToBackStack("")
                .commit()
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.rbtnOrangeTheme -> {
                parentActivity.setCurrentTheme(ThemeOne)
                parentActivity.recreate()
            }
            R.id.rbtnTealTheme -> {
                parentActivity.setCurrentTheme(ThemeTwo)
                parentActivity.recreate()
            }
            R.id.rbtnCyanTheme -> {
                parentActivity.setCurrentTheme(ThemeThree)
                parentActivity.recreate()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = ChipsFragment()
    }

}