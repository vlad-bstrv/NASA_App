package com.vladbstrv.nasaapp.view.layouts.coordinator.coordinatorsExample.exampleOne

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView

class SampleTitleBehavior(context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is TextView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        var deltaY = 0.0F
        if (deltaY == 0.0F) {
            deltaY = dependency.y - child.height
        }
        var dy = dependency.y - child.height
        dy = if (dy < 0) 0.0F
        else dy

        val y = -(dy / deltaY) * child.height
        child.translationY = y

        return true
    }
}