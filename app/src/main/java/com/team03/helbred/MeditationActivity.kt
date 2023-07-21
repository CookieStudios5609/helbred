package com.team03.helbred

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView

class MeditationActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var totalTimePassed: Long = 0
    private var totalRoutineTime: Long = 300000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)

        val TimerBar: ProgressBar = findViewById(R.id.TimerBar)
        val TimerText: TextView = findViewById(R.id.TimerText)
        val TimerButton: Button = findViewById(R.id.TimerButton)
        var BreathingType: TextView = findViewById(R.id.BreathingText)

        val TimeSelector: Spinner = findViewById(R.id.TimeSelect)

        val adapter = ArrayAdapter.createFromResource(this, R.array.Time, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        TimeSelector.adapter = adapter

        TimerBar.max = 5000
        TimerBar.progress = 0

        TimeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Update the selectedTime variable with the user's selection
                var selectedTime = when (position) {
                    0 -> 300000 // 5 minutes in milliseconds
                    1 -> 600000 // 10 minutes in milliseconds
                    2 -> 900000 // 15 minutes in milliseconds
                    else -> totalRoutineTime
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                totalRoutineTime =300000
            }
        }
        totalRoutineTime = selectedTime

        timer = object : CountDownTimer(5000, 10) {
            override fun onTick(remaining: Long) {  //Breath In
                TimerText.text = remaining.toString()
                TimerBar.progress = 5000 - remaining.toInt()

            }

            override fun onFinish() {
                TimerBar.progress = 0
                BreathingType.text = "Hold"
                timer.start()

            }

        }
        TimerButton.setOnClickListener {
            startBreathingRoutine(BreathingType, TimerText, TimerBar)
        }

    }



    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    private fun startBreathingRoutine(BreathingType: TextView, TimerText: TextView, TimerBar: ProgressBar) {
        timer = object : CountDownTimer(totalRoutineTime - totalTimePassed, 20000) {
            override fun onTick(remaining: Long) {
                totalTimePassed = totalRoutineTime - remaining
                BreathingExercise(BreathingType, TimerText, TimerBar)
            }

            override fun onFinish() {
                BreathingType.text = "Done!"
                TimerText.text="0"
            }
        }
        timer.start()
    }

    fun BreathingExercise(BreathingType: TextView, TimerText: TextView, TimerBar: ProgressBar) {
        // Phase 1: Breath In
        BreathingType.text = "Breath In"
        timer = object : CountDownTimer(5000, 10) {
            override fun onTick(remaining: Long) {
                TimerText.text = remaining.toString()
                TimerBar.progress = 5000 - remaining.toInt()
            }

            override fun onFinish() {
                // Phase 2: Hold
                BreathingType.text = "Hold"
                timer = object : CountDownTimer(5000, 10) {
                    override fun onTick(remaining: Long) {
                        TimerText.text = remaining.toString()
                        //TimerBar.progress = remaining.toInt()
                    }

                    override fun onFinish() {
                        // Phase 3: Breath Out
                        BreathingType.text = "Breath Out"
                        timer = object : CountDownTimer(5000, 10) {
                            override fun onTick(remaining: Long) {
                                TimerText.text = remaining.toString()
                                TimerBar.progress = remaining.toInt()
                            }

                            override fun onFinish() {
                                // Phase 4: Hold
                                BreathingType.text = "Hold"
                                timer = object : CountDownTimer(5000, 10) {
                                    override fun onTick(remaining: Long) {
                                        TimerText.text = remaining.toString()
                                        //TimerBar.progress = remaining.toInt()
                                    }

                                    override fun onFinish() {
                                        TimerText.text = "Done!"
                                    }
                                }
                                // Start Phase 4
                                timer.start()
                            }
                        }
                        // Start Phase 3
                        timer.start()
                    }
                }
                // Start Phase 2
                timer.start()
            }
        }
        // Start Phase 1
        timer.start()
    }

}


