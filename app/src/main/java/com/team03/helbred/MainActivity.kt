package com.team03.helbred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.io.File
import java.time.LocalDate
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

     override fun onResume() {
         super.onResume()

        showProgress(getCompletion())
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
    private fun getCompletion(): Int {
        //I take it back. {creation_timestamp} {total} {water} {workout} {stretch} {meditate}

        val streakfile = File(applicationContext.filesDir, "streak")
        if (!streakfile.exists()) {
            val make = streakfile.bufferedWriter()
            make.write(String.format("%s, 0, 0, 0, 0, 0", LocalDate.now().toString()))
            make.close()
        }
        val streak = streakfile.bufferedReader()
        for (line in streak.lines()) {
            val scanner = Scanner(line)
            if (line.split(", ")[0] == LocalDate.now().toString()) {
                val out = line.split(", ")[1].toDouble().toInt()
                streak.close()
                scanner.close()
                return out
            }
        } // if it never finds today, write one!
        streak.close()
        val writer = File(applicationContext.filesDir, "streak").bufferedWriter()
        writer.newLine()
            writer.write(
                String.format("%s, %d, %d, %d, %d, %d", LocalDate.now().toString(), 0, 0, 0, 0, 0))
        writer.close()
        return 0
    }
    fun writeTotals(index: Int, value: Int) {
        val info = File(applicationContext.filesDir, "streak").bufferedReader()
        for (line in info.lines()) {
            val scanner = Scanner(line)
            if (line.split(", ")[0] == LocalDate.now().toString()) {
                val write = File(applicationContext.filesDir, "streak").bufferedWriter()
                val strings = line.split(", ").toMutableList()
                strings[index] = value.toString()
                strings[1] = strings[2] + strings[3] + strings[4] + strings[5]
                write.write(strings.toString())
                scanner.close()
                write.close()
                info.close()
                break
            }
        }
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
     fun showProgress(completion: Int) {
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
