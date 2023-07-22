package com.team03.helbred

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.io.File
import java.time.LocalDate
import java.util.Calendar
import java.util.Scanner


class DrinkingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinking)

        val AddWater: Button = findViewById(R.id.addWater)
        val waterCount: TextView = findViewById(R.id.WaterCount)
        val ProgressBar: ProgressBar = findViewById(R.id.WaterProgressWheel)
        // waterType: TextView = findViewById(R.id.WaterType)

        val totalCups = 16
        var currentCups = getWater()

        waterCount.text = String.format("%d/%d", currentCups, totalCups)
        if (currentCups == 100) {
            waterCount.text = String.format("%d/%d", totalCups, totalCups) //stop warning me
        }

        ProgressBar.max = totalCups
        ProgressBar.progress = currentCups

        AddWater.setOnClickListener {

            if (currentCups < totalCups) {
                currentCups++
                waterCount.text = String.format("%d/%d", currentCups, totalCups)
                ProgressBar.progress = currentCups
                writeTotal(currentCups) //note this will store the actual number of cups, cutting the overall progress until ALL is drank
            }
            if (currentCups == totalCups) {
                writeTotal(100)
            }

        }

    }
    fun writeTotal(value: Int) {
        //oops, this looks bad
        val info = File(applicationContext.filesDir, "streak").bufferedReader()
        for (line in info.lines()) {
            val scanner = Scanner(line)
            Log.d("today",value.toString())
            if (line.split(", ")[0] == LocalDate.now().toString()) {
                val write = File(applicationContext.filesDir, "streak").bufferedWriter()
                val strings = line.split(", ").toMutableList()
                strings[2] = value.toString()
                strings[1] = ((strings[2].toInt() + strings[3].toInt() + strings[4].toInt() + strings[5].toInt())/4.0).toString()
                write.write(strings.toString().removePrefix("[").removeSuffix("]"))
                Log.d("today", strings.toString().removePrefix("[").removeSuffix("]"))
                scanner.close()
                write.close()
                info.close()
                break
            }

            }
        info.close()
    }
    private fun getWater(): Int {
        //I take it back. {creation_timestamp} {total} {water} {workout} {stretch} {meditate}
        val streakfile = File(applicationContext.filesDir, "streak")
        val streak = streakfile.bufferedReader()
        for (line in streak.lines()) {
            val scanner = Scanner(line)
            if (line.split(", ")[0] == LocalDate.now().toString()) {
                val out = line.split(", ")[2].toInt()
                streak.close()
                scanner.close()
                return out
            }
        } // if it never finds today, return 0
        streak.close()
        return 0
    }
}