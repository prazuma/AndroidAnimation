package com.prazuma.androidanimation

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
}
