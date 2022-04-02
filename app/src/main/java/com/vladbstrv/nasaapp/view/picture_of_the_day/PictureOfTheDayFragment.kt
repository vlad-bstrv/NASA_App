package com.vladbstrv.nasaapp.view.picture_of_the_day

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.lifecycle.Observer
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.PictureOfTheDayFragmentBinding
import com.vladbstrv.nasaapp.view.MainActivity
import com.vladbstrv.nasaapp.view.chips.ChipsFragment
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private var _binding: PictureOfTheDayFragmentBinding? = null
    private val binding: PictureOfTheDayFragmentBinding get() = _binding!!
    private var flag = false
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PictureOfTheDayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
//        viewModel.sendServerRequest(1)
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.chipGroup.findViewById<Chip>(checkedId)?.let {
                viewModel.sendServerRequest(checkedId)
            }
        }


        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.main)

        binding.imageView.setOnClickListener {
            showDescription(constraintSet)
        }
        binding.btnCloseCardView.setOnClickListener {
            hideDescription(constraintSet)
        }
    }

    private fun animateCardViewDescription() {
        val changeBounds = ChangeBounds()
        changeBounds.interpolator = AnticipateOvershootInterpolator(2.0f)
        changeBounds.duration = 1000L
        TransitionManager.beginDelayedTransition(binding.main, changeBounds)
    }


    private fun hideDescription(constraintSet: ConstraintSet) {
        flag = !flag
        animateCardViewDescription()
        constraintSet.connect(
            R.id.cardViewDescription,
            ConstraintSet.END,
            R.id.main,
            ConstraintSet.START
        )
        constraintSet.clear(R.id.cardViewDescription, ConstraintSet.START)
        constraintSet.applyTo(binding.main)
    }

    private fun showDescription(constraintSet: ConstraintSet) {
        flag = !flag
        animateCardViewDescription()

        if (flag) {

            constraintSet.connect(
                R.id.cardViewDescription,
                ConstraintSet.END,
                R.id.main,
                ConstraintSet.END
            )
            constraintSet.connect(
                R.id.cardViewDescription,
                ConstraintSet.START,
                R.id.main,
                ConstraintSet.START
            )
            constraintSet.applyTo(binding.main)
        } else {
            constraintSet.connect(
                R.id.cardViewDescription,
                ConstraintSet.END,
                R.id.main,
                ConstraintSet.START
            )
            constraintSet.clear(R.id.cardViewDescription, ConstraintSet.START)
            constraintSet.applyTo(binding.main)
        }
    }

    fun renderData(pictureOfTheDayState: PictureOfTheDayState) {
        when (pictureOfTheDayState) {
            is PictureOfTheDayState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.main, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.repeat_the_download)) {
                        viewModel.sendServerRequest(1)
                    }
                    .show()
            }
            is PictureOfTheDayState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is PictureOfTheDayState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.imageView.load(pictureOfTheDayState.serverResponseData.url)
                binding.included.bottomSheetDescriptionHeader.text =
                    pictureOfTheDayState.serverResponseData.title
                setFontAndText()
                binding.included.bottomSheetDescription.text =
                    pictureOfTheDayState.serverResponseData.explanation

                binding.tvTitle.text = pictureOfTheDayState.serverResponseData.title
                binding.tvDescription.text = pictureOfTheDayState.serverResponseData.explanation
            }
        }
    }

    private fun setFontAndText() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.included.bottomSheetDescriptionHeader.typeface = Typeface.createFromAsset(requireActivity().assets,
            "RubikGlitch-Regular.ttf")
        }

        val request = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "name=Montserrat&amp;weight=700",
            R.array.com_google_android_gms_fonts_certs
        )

        val callback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                binding.included.bottomSheetDescriptionHeader.typeface = typeface
                super.onTypefaceRetrieved(typeface)
            }
        }

        FontsContractCompat.requestFont(requireContext(), request, callback, Handler(Looper.myLooper()!!))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_botton_bar, menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                var answer: String = current.format(formatter)
                Toast.makeText(requireContext(), answer, Toast.LENGTH_SHORT).show()
            }
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ChipsFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}