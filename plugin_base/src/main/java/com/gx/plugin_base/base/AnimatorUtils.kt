package com.gx.plugin_base.base

import android.animation.ObjectAnimator
import android.view.View

/**
 * Created by GuoXu on 2020/10/20 9:35.
 *
 */
object AnimatorUtils {
    fun startRotate(view: View, startValues: Float, endValues: Float) {
        val rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", startValues, endValues)
        rotateAnimator.duration = 1000
        rotateAnimator.start()
    }

    fun translationX(view: View, startValues: Float, endValues: Float) {
        val rotateAnimator = ObjectAnimator.ofFloat(view, "translationX", startValues, endValues)
        rotateAnimator.duration = 1000
        rotateAnimator.start()
    }

    fun translationY(view: View, startValues: Float, endValues: Float) {
        val rotateAnimator = ObjectAnimator.ofFloat(view, "translationY", startValues, endValues)
        rotateAnimator.duration = 1000
        rotateAnimator.start()
    }
}