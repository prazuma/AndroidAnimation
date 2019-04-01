package com.prazuma.androidanimation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_property_animation.*

class PropertyAnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_animation)

        property_animation_value_button.setOnClickListener {
            doValueAnimation(property_animation_image)
        }

        property_animation_value_with_type_evaluator_button.setOnClickListener {
            doValueAnimatorWithTypeEvaluator(property_animation_image)
        }

        property_animation_object_button.setOnClickListener {
            doObjectAnimation(property_animation_image)
        }

        property_animation_object_with_type_evaluator.setOnClickListener {
            doObjectAnimatorWithTypeEvaluator(property_animation_image)
        }

        property_animation_multi_button.setOnClickListener {
            doMultipleAnimationsUsingAnimatorSet(property_animation_image)
        }
    }

    private fun doValueAnimation(view: View) {
        ValueAnimator.ofFloat(0f, 100f).apply {
            duration = 1000
            addUpdateListener { updatedAnimation ->
                view.translationX = updatedAnimation.animatedValue as Float
            }
            start()
        }
    }

    private fun doValueAnimatorWithTypeEvaluator(view: View) {
        ValueAnimator.ofObject(
                MyTypeEvaluator(),
                Pair(0f, 0f),
                Pair(100f, 100f)
        ).apply {
            duration = 1000
            addUpdateListener { updatedAnimation ->
                val pair = updatedAnimation.animatedValue as Pair<*, *>
                view.translationX = pair.first as Float
                view.translationY = pair.second as Float
            }
            start()
        }
    }

    private fun doObjectAnimation(view: View) {
        // ObjectAnimator is much easier than ValueAnimator,
        // because the animated property updates automatically.
        ObjectAnimator.ofFloat(view, "translationX", 100f).apply {
            duration = 1000
            start()
        }
    }

    private fun doObjectAnimatorWithTypeEvaluator(view: View) {
        ObjectAnimator.ofObject(
                MyTypeEvaluator(),
                Pair(0f, 0f),
                Pair(100f, 100f)
        ).apply {
            duration = 1000
            addUpdateListener { updatedAnimation ->
                val pair = updatedAnimation.animatedValue as Pair<*, *>
                view.translationX = pair.first as Float
                view.translationY = pair.second as Float
            }
            start()
        }
    }

    private fun doMultipleAnimationsUsingAnimatorSet(view: View) {
        val upAnimation = ObjectAnimator.ofFloat(view, "translationY", -50f).apply {
            duration = 250
        }
        val downAnimation = ObjectAnimator.ofFloat(view, "translationY", 50f).apply{
            duration = 250
        }
        val scaleXAnimation1 = ObjectAnimator.ofFloat(view, "scaleX", 10f).apply {
            duration = 100
        }
        val scaleYAnimation1 = ObjectAnimator.ofFloat(view, "scaleY", 10f).apply {
            duration = 100
        }
        val scaleXAnimation2 = ObjectAnimator.ofFloat(view, "scaleX", 1f).apply {
            duration = 100
        }
        val scaleYAnimation2 = ObjectAnimator.ofFloat(view, "scaleY", 1f).apply {
            duration = 100
        }
        AnimatorSet().apply {
            play(upAnimation).before(scaleXAnimation1)
            play(scaleXAnimation1).with(scaleYAnimation1)
            play(scaleXAnimation2).after(scaleYAnimation1)
            play(scaleXAnimation2).with(scaleYAnimation2)
            play(downAnimation).after(scaleXAnimation2)
            start()
        }
    }

    class MyTypeEvaluator : TypeEvaluator<Pair<Float, Float>> {
        override fun evaluate(
                fraction: Float,
                startValue: Pair<Float, Float>?,
                endValue: Pair<Float, Float>?
        ): Pair<Float, Float> {
            startValue ?: return Pair(0f, 0f)
            endValue ?: return Pair(0f, 0f)

            return when {
                fraction < 0.3f -> startValue
                fraction < 0.6f -> {
                    Pair((startValue.first + endValue.first) / 2,
                            (startValue.second + endValue.second) / 2)
                }
                else -> endValue
            }
        }
    }
}
