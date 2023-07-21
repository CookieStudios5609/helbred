package com.team03.helbred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.EditText
import android.view.LayoutInflater
import android.app.AlertDialog
import android.content.Context


class homeWorkoutActivity : AppCompatActivity() {
    private var where: String? = "";
    private lateinit var timerTextView: TextView
    private lateinit var timerInputEditText: EditText
    private lateinit var startTimerButton: Button
    private var countDownTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_workout)
       where = intent.getStringExtra("locale")

        timerTextView = findViewById(R.id.timerTextView)
        timerInputEditText = findViewById(R.id.etTimerInput)
        startTimerButton = findViewById(R.id.btnStartTimer)
        if (where == "H") {
            setHomeWorkoutData()
        } else if (where == "G") {
            setGymWorkoutData()
        }
        startTimerButton.setOnClickListener {
            startTimer()
        }
    }

    private fun setHomeWorkoutData() {
        // Set images and texts for home workout
        findViewById<ImageView>(R.id.ivhk).setImageResource(R.drawable.highknees)
        findViewById<ImageView>(R.id.ivjj).setImageResource(R.drawable.jumpingjacks)
        findViewById<ImageView>(R.id.ivs).setImageResource(R.drawable.squats)
        findViewById<ImageView>(R.id.ivp).setImageResource(R.drawable.punches)
        findViewById<ImageView>(R.id.ivl).setImageResource(R.drawable.lunges)
        findViewById<ImageView>(R.id.ivkte).setImageResource(R.drawable.kneetoelbow)

        findViewById<TextView>(R.id.txt1).text = "High Knees"
        findViewById<TextView>(R.id.txt2).text = "Jumping Jacks"
        findViewById<TextView>(R.id.txt3).text = "Knee to Elbow"
        findViewById<TextView>(R.id.txt4).text = "Lunges"
        findViewById<TextView>(R.id.txt5).text = "Punches"
        findViewById<TextView>(R.id.txt6).text = "Squats"
    }

    private fun setGymWorkoutData() {
        // Set images and texts for gym workout
        findViewById<ImageView>(R.id.ivhk).setImageResource(R.drawable.elliptical)
        findViewById<ImageView>(R.id.ivjj).setImageResource(R.drawable.legpress)
        findViewById<ImageView>(R.id.ivs).setImageResource(R.drawable.lyinglegcurls)
        findViewById<ImageView>(R.id.ivp).setImageResource(R.drawable.stationarybike)
        findViewById<ImageView>(R.id.ivl).setImageResource(R.drawable.stepmill)
        findViewById<ImageView>(R.id.ivkte).setImageResource(R.drawable.threadmill)

        findViewById<TextView>(R.id.txt1).text = "Elliptical"
        findViewById<TextView>(R.id.txt2).text = "Leg Press"
        findViewById<TextView>(R.id.txt3).text = "Treadmill"
        findViewById<TextView>(R.id.txt4).text = "Step Mill"
        findViewById<TextView>(R.id.txt5).text = "Stationary Bike"
        findViewById<TextView>(R.id.txt6).text = "Lying Leg Curls"
    }

    private fun startTimer() {
        // Get the input time in minutes from the EditText
        val inputTime = timerInputEditText.text.toString()
        if (inputTime.isEmpty()) {
            // Handle the case where the input is empty
            return
        }

        // Convert the input time to milliseconds
        val timeInMillis = inputTime.toLong() * 60 * 1000

        // Create and start the CountDownTimer
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the timerTextView with the remaining time
                timerTextView.text = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                // Handle timer completion (e.g., show a message)
                timerTextView.text = "Timer Finished!"
                showWorkoutCompletedDialog(formatTime(timeInMillis))
            }
        }.start()
    }

    private fun formatTime(milliseconds: Long): String {
        val minutes = (milliseconds / 1000) / 60
        val seconds = (milliseconds / 1000) % 60
        return String.format("Timer: %02d:%02d", minutes, seconds)
    }

    private fun showWorkoutCompletedDialog(duration: String) {
        // Create a custom AlertDialog
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_workout_completed, null)
        builder.setView(view)
        val dialog = builder.create()

        // Initialize views in the dialog layout
        val tvCongratulations: TextView = view.findViewById(R.id.tvCongratulations)
        val tvWorkoutDuration: TextView = view.findViewById(R.id.tvWorkoutDuration)
        val btnMarkAsCompleted: Button = view.findViewById(R.id.btnMarkAsCompleted)

        // Set the workout duration in the dialog
        tvWorkoutDuration.text = duration

        // Set onClickListener for the "Mark as Completed" button
        btnMarkAsCompleted.setOnClickListener {
            // Handle the button click (e.g., mark the workout as completed in your app)
            saveWorkoutTimeToFile(duration)
            dialog.dismiss() // Dismiss the dialog after handling the button click
        }

        // Show the dialog
        dialog.show()
    }

    private fun saveWorkoutTimeToFile(duration: String) {
        val fileName = "completed_workouts.txt"
        val timeEntry = "$duration minutes\n"

        try {
            val fileOutputStream = openFileOutput(fileName, Context.MODE_APPEND)
            fileOutputStream.write(timeEntry.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        // Cancel the timer to avoid potential memory leaks
        countDownTimer?.cancel()
    }


}