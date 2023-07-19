package com.team03.helbred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class WorkoutLocaleChooserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_locale_chooser)
        val home = findViewById<ImageButton>(R.id.homeButtonChoice)
        val gym = findViewById<ImageButton>(R.id.gymButtonChoice)
        gym.setOnClickListener{
            val toPage =  Intent(this, homeWorkoutActivity::class.java)
            toPage.putExtra("locale", "G")
            startActivity(toPage)
        }
        home.setOnClickListener{
            val toPage =  Intent(this, homeWorkoutActivity::class.java)
            toPage.putExtra("locale", "H")
            startActivity(toPage)
        }
    }

}