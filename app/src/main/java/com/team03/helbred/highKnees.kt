package com.team03.helbred

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class highKnees : AppCompatActivity() {


    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var durationEditText: EditText
    private lateinit var startButton: Button
    private lateinit var markCompleteButton: Button
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_knees)

        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)
        timerTextView = findViewById(R.id.timerTextView)
        durationEditText = findViewById(R.id.durationEditText)
        startButton = findViewById(R.id.startButton)
        markCompleteButton = findViewById(R.id.markCompleteButton)


        imageView.setImageResource(R.drawable.highknees)
        textView.text = "High Knees"

        startButton.setOnClickListener {
            startTimer()
    }
}


    private fun startTimer() {
        val durationMinutes = durationEditText.text.toString().toLong()
        val durationMillis = durationMinutes * 60 * 1000

        countDownTimer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / (60 * 1000)
                val seconds = (millisUntilFinished % (60 * 1000)) / 1000
                timerTextView.text = "$minutes:$seconds"
                timerTextView.visibility = View.VISIBLE // Make the timer visible
            }

            override fun onFinish() {
                saveToFile()
                timerTextView.text = "Timer finished!"
                markCompleteButton.visibility = View.VISIBLE
            }
        }

        countDownTimer.start()
    }

    private fun saveToFile() {
        val text = textView.text.toString()
        val duration = durationEditText.text.toString()

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDateAndTime = sdf.format(Date())

        val dataToWrite = "Date: $currentDateAndTime\nDuration: $duration minutes\nText: $text"

        try {
            val filename = "timer_data.txt"
            val file = File(this.filesDir, filename)
            file.writeText(dataToWrite)



            Toast.makeText(this, "Data saved to file!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving data to file!", Toast.LENGTH_SHORT).show()
        }
    }

    fun onMarkCompleteClicked(view: View) {
        // Implement the logic for what should happen when the "Mark as Complete" button is clicked.
        // For example, you could reset the timer, clear the text, and hide the button again.
        // Here, we are just showing a toast message indicating that the task is marked as complete.
        Toast.makeText(this, "Task marked as complete!", Toast.LENGTH_SHORT).show()
    }
}
