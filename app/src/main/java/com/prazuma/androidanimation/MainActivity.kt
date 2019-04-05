package com.prazuma.androidanimation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_property_animation_button.setOnClickListener {
            startActivity(Intent(this, PropertyAnimationActivity::class.java))
        }

        main_layout_transition_button.setOnClickListener {
            startActivity(Intent(this, LayoutTransitionActivity::class.java))
        }

        main_state_list_animator_button.setOnClickListener {
            startActivity(Intent(this, StateListAnimatorActivity::class.java))
        }
    }
}
