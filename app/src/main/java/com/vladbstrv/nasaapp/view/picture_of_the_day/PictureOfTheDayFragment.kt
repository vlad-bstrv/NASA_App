package com.vladbstrv.nasaapp.view.picture_of_the_day

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BulletSpan
import android.text.style.RelativeSizeSpan
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.TextView
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.vladbstrv.nasaapp.R
import com.vladbstrv.nasaapp.databinding.PictureOfTheDayFragmentBinding
import com.vladbstrv.nasaapp.view.chips.ChipsFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

//                binding.included.bottomSheetDescription.text =
//                    setSpanForBottomSheetDescription(pictureOfTheDayState.serverResponseData.explanation)

                var spannableStringBuilder = SpannableStringBuilder(pictureOfTheDayState.serverResponseData.explanation)
                binding.included.bottomSheetDescription.setText(
                    spannableStringBuilder,
                    TextView.BufferType.EDITABLE
                )
                spannableStringBuilder = binding.included.bottomSheetDescription.text as SpannableStringBuilder
                binding.included.slider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
                    when(value) {
                        1.0f -> {
                            spannableStringBuilder.setSpan(
                                RelativeSizeSpan(0.5f),
                                0,
                                spannableStringBuilder.length,
                                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                        2.0f -> {
                            spannableStringBuilder.setSpan(
                                RelativeSizeSpan(1.0f),
                                0,
                                spannableStringBuilder.length,
                                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                        3.0f -> {
                            spannableStringBuilder.setSpan(
                                RelativeSizeSpan(2.0f),
                                0,
                                spannableStringBuilder.length,
                                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    }

                })

                binding.tvTitle.text = pictureOfTheDayState.serverResponseData.title
                binding.tvDescription.text = pictureOfTheDayState.serverResponseData.explanation
            }
        }
    }

    private fun setFontAndText() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.included.bottomSheetDescriptionHeader.typeface = Typeface.createFromAsset(
                requireActivity().assets,
                "RubikGlitch-Regular.ttf"
            )
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

        FontsContractCompat.requestFont(
            requireContext(),
            request,
            callback,
            Handler(Looper.myLooper()!!)
        )
    }

    private fun setSpanForBottomSheetDescription(textDescription: String): SpannableStringBuilder {
        var spannableStringBuilder = SpannableStringBuilder(textDescription)
        spannableStringBuilder = binding.included.bottomSheetDescription.text as SpannableStringBuilder

        spannableStringBuilder.insert(10, "\n")
        spannableStringBuilder.insert(20, "\n")
        spannableStringBuilder.insert(30, "\n")
        spannableStringBuilder.insert(40, "\n")

        setBulletSpan(spannableStringBuilder)

        spannableStringBuilder.setSpan(
            RelativeSizeSpan(2.0f),
            0,
            spannableStringBuilder.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

//        setRelativeSizeSpan(spannableStringBuilder)
//        binding.included.slider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
//            spannableStringBuilder.setSpan(
//                RelativeSizeSpan(value),
//                0,
//                spannableStringBuilder.length,
//                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        })
//        spannableStringBuilder.setSpan(AbsoluteSizeSpan(20, true), 0, spannableStringBuilder.length/2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannableStringBuilder
    }

    private fun setRelativeSizeSpan(spannableStringBuilder: SpannableStringBuilder) {
        binding.included.slider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            spannableStringBuilder.setSpan(
                RelativeSizeSpan(value),
                0,
                spannableStringBuilder.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        })

    }


    private fun setBulletSpan(spannableStringBuilder: SpannableStringBuilder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannableStringBuilder.setSpan(
                BulletSpan(50, ContextCompat.getColor(requireContext(), R.color.purple_700), 10),
                11,
                12,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannableStringBuilder.setSpan(
                BulletSpan(50, ContextCompat.getColor(requireContext(), R.color.purple_700), 10),
                21,
                31,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannableStringBuilder.setSpan(
                BulletSpan(50, ContextCompat.getColor(requireContext(), R.color.purple_700), 10),
                31,
                41,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannableStringBuilder.setSpan(
                BulletSpan(50, ContextCompat.getColor(requireContext(), R.color.purple_700), 10),
                41,
                51,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
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