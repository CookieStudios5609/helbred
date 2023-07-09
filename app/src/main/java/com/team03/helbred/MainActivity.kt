package com.team03.helbred

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressWheel = findViewById<CircularProgressIndicator>(R.id.dailyProgressWheel)
        val progressMessage = findViewById<TextView>(R.id.goalsMessage)
        progressWheel.setProgress(22, true)
        val percent = findViewById<TextView>(R.id.percentageText)
        percent.text = "22%"

        if (progressWheel.progress > 20) {
            progressWheel.setIndicatorColor(getColor(R.color.success_green))
            progressMessage.setTextColor(getColor(R.color.success_green))
            progressMessage.text = buildString {
        append("You've made some progress today. Keep going!")
    }
        }



    }
}