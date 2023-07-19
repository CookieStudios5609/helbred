package com.team03.helbred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.io.File
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
        showProgress(getCompletion()[0])

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
    }

    //TODO: preferences page
    private fun getCompletion(): ArrayList<Int> {
        // the streak is just a number. Increment it each time user hits 100
        // I think this is a good compromise; making a file that grows every single day could get problematic...
        // though, we'd just be iterating over n days where app has been opened
//        val streak = File(applicationContext.filesDir, "streak").bufferedReader()
//        val dayCount:String = streak.readLine()
//        streak.close()
//        return dayCount

        //I take it back. {creation_timestamp} {total} {water} {workout} {stretch} {meditate}
        val streakfile = File(applicationContext.filesDir, "streak")
        if (!streakfile.exists()) {
            val make = streakfile.bufferedWriter()
            make.write(String.format("%d 0 0 0 0 0", DateUtils.DAY_IN_MILLIS))
        }
        val streak = streakfile.bufferedReader()

        val out = ArrayList<Int>();
        for (line in streak.lines()) {
            val scanner = Scanner(line)
            if (DateUtils.isToday(scanner.nextLong())) {
                out.add(scanner.nextInt())
                out.add(scanner.nextInt())
                out.add(scanner.nextInt())
                out.add(scanner.nextInt())
                out.add(scanner.nextInt())
                streak.close()
                return out
            }
        } // if it never finds today, write one!
        streak.close()
        val writer = File(applicationContext.filesDir, "streak").bufferedWriter()
        writer.newLine()
            writer.write(
                String.format("%d %d %d %d %d %d", DateUtils.DAY_IN_MILLIS, 0, 0, 0, 0, 0))
        writer.close()
        out.add(0)
        out.add(0)
        out.add(0)
        out.add(0)
        out.add(0)
        return out
    }
    private fun writeTotals(index: Int, value: Int) {
        val info = File(applicationContext.filesDir, "streak").bufferedReader()
        info.close()
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
        meditateButton.setOnClickListener{
            val intent = Intent(this,MeditationActivity::class.java)
            startActivity(intent)
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