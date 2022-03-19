package com.vladbstrv.nasaapp.view.layouts.coordinator

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs

class ButtonBehavior(context: Context, attr:AttributeSet?=null): CoordinatorLayout.Behavior<View>(context, attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ) = dependency is AppBarLayout

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val bar = dependency as AppBarLayout
        val barHeight = bar.height.toFloat() + bar.y
        val barY = bar.y

        if(abs(barY) > (barHeight*0.66)) {
            child.visibility = View.GONE
        } else {
            child.visibility = View.VISIBLE
            child.alpha = ((barHeight*2/3)- kotlin.math.abs(barY / 2))/(barHeight*2/3)
        }
        Log.d("AAA", child.y.toString())

        return super.onDependentViewChanged(parent, child, dependency)
    }
}