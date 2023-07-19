package com.team03.helbred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
        showProgress(79)

    }
    private fun getThresholds(): ArrayList<Int>{ //lets just hope no bad info gets in here
        val prefs = File(applicationContext.filesDir, "pref")
        if (!prefs.exists()) {
            val prefWriter = prefs.bufferedWriter()
            prefWriter.write("25,50,75,100")
            prefWriter.close()
        }
        val prefReader = prefs.bufferedReader()
        val threshs = prefReader.readLine().split(",")
        val threshes: ArrayList<Int> = ArrayList<Int>()
        for (strang in threshs) {
            threshes.add(strang.toInt())
        }
        prefReader.close()
        return threshes

    }//TODO: preferences page
    private fun getStreak(): String {
        // the streak is just a number. Increment it each time user hits 100
        // I think this is a good compromise; making a file that grows every single day could get problematic...
        // though, we'd just be iterating over n days where app has been opened
        val streak = File(applicationContext.filesDir, "streak").bufferedReader()
        val dayCount:String = streak.readLine()
        streak.close()
        return dayCount

    }
    private fun setListeners() {
        val hydrateButton = findViewById<ImageButton>(R.id.hydrate_btn)
        val workoutsButton = findViewById<ImageButton>(R.id.workout_btn)
        val meditateButton = findViewById<ImageButton>(R.id.meditation_btn)
        val stretchButton = findViewById<ImageButton>(R.id.stretch_btn)
        val streakButton = findViewById<CircularProgressIndicator>(R.id.dailyProgressWheel)

        hydrateButton.setOnClickListener{
            val intent = Intent(this,DrinkingActivity::class.java)
            startActivity(intent)
        }
        workoutsButton.setOnClickListener{
            startActivity(Intent(this, WorkoutLocaleChooserActivity::class.java))
        }
    }
    private fun showProgress(completion: Int) {
        val progressWheel = findViewById<CircularProgressIndicator>(R.id.dailyProgressWheel)
        val progressMessage = findViewById<TextView>(R.id.goalsMessage)
        progressWheel.setProgress(completion, true)
        val percent = findViewById<TextView>(R.id.percentageText)
        percent.text = completion.toString() + "%"

        val threshes = getThresholds()
        if (completion == threshes[3]) {
            progressWheel.setIndicatorColor(getColor(R.color.success_green))
            progressMessage.setTextColor(getColor(R.color.success_green))
            progressMessage.text = buildString {
                append("Another day complete. Congratulations!")
            }
        }
        else if (completion > threshes[2]) {
            progressWheel.setIndicatorColor(getColor(R.color.success_green))
            progressMessage.setTextColor(getColor(R.color.success_green))
            progressMessage.text = buildString {
                append("Just a bit more. Keep going!")
            }
        }
        else if (completion > threshes[1]) {
            progressWheel.setIndicatorColor(getColor(R.color.success_green))
            progressMessage.setTextColor(getColor(R.color.success_green))
            progressMessage.text = buildString {
                append("You've got this, keep pushing!")
            }
        }
        else if (completion > threshes[0]) {
            progressWheel.setIndicatorColor(getColor(R.color.black))
            progressMessage.setTextColor(getColor(R.color.black))
            percent.setTextColor(getColor(R.color.black))
            progressMessage.text = buildString {
                append("Improvement begins here and now.")
            }
        }
        else if (completion < threshes[2]) {
            progressMessage.text = buildString {
                append("A fresh day. Time to make history!")
            }
        }

    }
}