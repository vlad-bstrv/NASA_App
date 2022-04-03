package com.vladbstrv.nasaapp.view.ux


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbstrv.nasaapp.databinding.FragmentUxButtonBinding
import com.vladbstrv.nasaapp.databinding.FragmentUxTextBinding
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener


class UxButtonFragment : Fragment() {

    private var _binding: FragmentUxButtonBinding? = null
    private val binding: FragmentUxButtonBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUxButtonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder = GuideView.Builder(requireContext())
            .setTitle("Устаревший подход")
            .setContentText("Мы не используем работу с цветом, размером и шрифтами")
            .setGravity(Gravity.center)
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.layoutBad)
            .setDismissType(DismissType.anywhere)
            .setGuideListener {
                val builder = GuideView.Builder(requireContext())
                    .setTitle("Новый подход")
                    .setContentText("Работа с прозрачностью и простарнством")
                    .setGravity(Gravity.center)
                    .setDismissType(DismissType.anywhere)
                    .setTargetView(binding.layoutGood)
                    .setDismissType(DismissType.anywhere)
                    .setGuideListener {
                        val builder = GuideView.Builder(requireContext())
                            .setTitle("Новый подход")
                            .setContentText("Material 3")
                            .setGravity(Gravity.center)
                            .setDismissType(DismissType.anywhere)
                            .setTargetView(binding.layoutGoodNewMaterial)
                            .setDismissType(DismissType.anywhere)


                        builder.build().show()
                    }

                builder.build().show()
            }
        builder.build().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}