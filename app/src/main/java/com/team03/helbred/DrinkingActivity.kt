package com.team03.helbred

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class DrinkingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinking)

        val AddWater: Button = findViewById(R.id.addWater)
        val waterCount: TextView = findViewById(R.id.WaterCount)
        val ProgressBar: ProgressBar = findViewById(R.id.WaterProgressWheel)
        // waterType: TextView = findViewById(R.id.WaterType)

        var currentCups = 1
        val totalCups = 16

        waterCount.text = "$currentCups/$totalCups"
        ProgressBar.max = totalCups
        ProgressBar.progress = currentCups

        AddWater.setOnClickListener {
            if (currentCups < totalCups) {
                currentCups++
                waterCount.text = "$currentCups/$totalCups"
                ProgressBar.progress = currentCups
            }
        }

    }
}
