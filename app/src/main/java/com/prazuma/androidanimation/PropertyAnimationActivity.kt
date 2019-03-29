package com.prazuma.androidanimation

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
