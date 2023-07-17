package com.team03.helbred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val HydrateButton : ImageButton = findViewById(R.id.hydrate_btn)



        val progressWheel = findViewById<CircularProgressIndicator>(R.id.dailyProgressWheel)
        val progressMessage = findViewById<TextView>(R.id.goalsMessage)
        progressWheel.setProgress(22, true)
        val percent = findViewById<TextView>(R.id.percentageText)
        percent.text = "22%" //TODO: collaborator implements progress/streak calculation

        if (progressWheel.progress > 20) { //TODO: collaborator implements success thresholds
            progressWheel.setIndicatorColor(getColor(R.color.success_green))
            progressMessage.setTextColor(getColor(R.color.success_green))
            progressMessage.text = buildString {
        append("You've made some progress today. Keep going!")
    }
        }
        HydrateButton.setOnClickListener{
            val intent = Intent(this,DrinkingActivity::class.java)
            startActivity(intent)
        }
        //TODO: placeholder for future activities
//        fun onMainButtonsPressed(button: ImageButton) {
//            if (button.id.toString() == "workout_btn") {
//                startActivity(Intent(this, WorkoutPickActivity::class.kt))
//            }
//        }
//        fun gotoSettings() {
//            startActivity(Intent(this))
//        }
        val workoutsButton = findViewById<ImageButton>(R.id.workout_btn)
        workoutsButton.setOnClickListener{
            startActivity(Intent(this, WorkoutLocaleChooserActivity::class.java))
        }



    }
}