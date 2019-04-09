package com.prazuma.androidanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_animations_with_interpolators.*

class AnimationsWithInterpolatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animations_with_interpolators)

        activity_animate_with_interpolator_button.setOnClickListener {
            doAnimationWithInterpolator(activity_animate_with_interpolator_image)
        }

        activity_animate_without_interpolator_button.setOnClickListener {
            doAnimationWithoutInterpolator(activity_animate_with_interpolator_image)
        }
    }

    private fun doAnimationWithInterpolator(view: View) {
        ObjectAnimator.ofFloat(view, "translationX", 100f).apply {
            duration = 1000
            interpolator = CustomAccelerateDecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    // Called when the animation ends.
                    super.onAnimationEnd(animation)
                    ObjectAnimator.ofFloat(view, "translationX", 0f).apply {
                        duration = 1000
                        interpolator = CustomAccelerateDecelerateInterpolator()
                        start()
                    }
                }
            })
            start()
        }
    }

    private fun doAnimationWithoutInterpolator(view: View) {
        ObjectAnimator.ofFloat(view, "translationX", 100f).apply {
            duration = 1000
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    // Called when the animation ends.
                    super.onAnimationEnd(animation)
                    ObjectAnimator.ofFloat(view, "translationX", 0f).apply {
                        duration = 1000
                        start()
                    }
                }
            })
            start()
        }
    }

    class CustomAccelerateDecelerateInterpolator : AccelerateDecelerateInterpolator() {
        override fun getInterpolation(input: Float): Float {
            (Math.cos((input + 1) * Math.PI) / 2.0f).toFloat() + 0.5f
            var x = input * 2.0f
            if (input < 0.5f) return 0.5f * x * x * x * x * x
            x = (input - 0.5f) * 2 - 1
            return 0.5f * x * x * x * x * x + 1
        }
    }
}
