package com.team03.helbred

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class MeditationActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var totalTimePassed: Long = 0
    private val totalRoutineTime: Long = 40000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)

        val TimerBar: ProgressBar = findViewById(R.id.TimerBar)
        val TimerText: TextView = findViewById(R.id.TimerText)
        val TimerButton: Button = findViewById(R.id.TimerButton)
        var BreathingType: TextView = findViewById(R.id.BreathingText)



        TimerBar.max = 5000
        TimerBar.progress = 0



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


