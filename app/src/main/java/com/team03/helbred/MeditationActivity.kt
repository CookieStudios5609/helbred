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
import java.io.File
import java.time.LocalDate
import java.util.Scanner
import java.util.concurrent.TimeUnit

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
        val TimerStop: Button = findViewById(R.id.StopTimer)
        var BreathingType: TextView = findViewById(R.id.BreathingText)

        var TimerLeft : TextView = findViewById(R.id.test1)

        var timeLeftBar = findViewById<ProgressBar>(R.id.TimeLeftBar)

        val TimeSelector: Spinner = findViewById(R.id.TimeSelect)
        timeLeftBar.setProgressDrawable(resources.getDrawable(android.R.drawable.progress_horizontal))
        TimerLeft.text = totalRoutineTime.toString()
        timeLeftBar.max = totalRoutineTime.toInt()
        timeLeftBar.progress = 0



        val adapter = ArrayAdapter.createFromResource(this, R.array.Time, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        TimeSelector.adapter = adapter

        TimerBar.max = 5000
        TimerBar.progress = 0

        TimeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Update the selectedTime variable with the user's selection
                totalRoutineTime = when (position) {
                    0 -> 300000 // 5 minutes in milliseconds
                    1 -> 600000 // 10 minutes in milliseconds
                    2 -> 900000 // 15 minutes in milliseconds
                    3 -> 1800000 // 30 minutes in milliseconds
                    else -> totalRoutineTime
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                totalRoutineTime =300000
            }
        }


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
            timeLeftBar.max = totalRoutineTime.toInt()
            timeLeftBar.progress = 0
            TimerLeft.visibility = TextView.VISIBLE

            TimerButton.visibility=Button.INVISIBLE
            TimerStop.visibility =Button.VISIBLE

            timer = object : CountDownTimer(totalRoutineTime, 10) {
                override fun onTick(remaining: Long) {
                    timeLeftBar.progress = (totalRoutineTime - remaining).toInt()
                    TimerLeft.text = formatTimeToMinutes(remaining)

                }

                override fun onFinish() {

                }
            }
            timer.start()

        }
        TimerStop.setOnClickListener {
            timeLeftBar.progress = 0
            TimerBar.progress = 0
            recreate()



        }

    }
    fun writeTotal(value: Int) {
        //oops, this looks bad
        val info = File(applicationContext.filesDir, "streak").bufferedReader()
        for (line in info.lines()) {
            val scanner = Scanner(line)
            if (line.split(", ")[0] == LocalDate.now().toString()) {
                val write = File(applicationContext.filesDir, "streak").bufferedWriter()
                val strings = line.split(", ").toMutableList()
                strings[5] = value.toString()
                strings[1] = ((strings[2].toInt() + strings[3].toInt() + strings[4].toInt() + strings[5].toInt())/4.0).toString()
                write.write(strings.toString().removePrefix("[").removeSuffix("]"))
                scanner.close()
                write.close()
                info.close()
                break
            }
        }
        info.close()
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
                writeTotal(100)
            }
        }
        timer.start()
    }

    fun BreathingExercise(BreathingType: TextView, TimerText: TextView, TimerBar: ProgressBar) {
        // Phase 1: Breath In
        BreathingType.text = "Breath In"
        timer = object : CountDownTimer(5000, 10) {
            override fun onTick(remaining: Long) {
                TimerText.text = formatTimeToSeconds(remaining)
                TimerBar.progress = 5000 - remaining.toInt()
            }

            override fun onFinish() {
                // Phase 2: Hold
                BreathingType.text = "Hold"
                timer = object : CountDownTimer(5000, 10) {
                    override fun onTick(remaining: Long) {
                        TimerText.text = formatTimeToSeconds(remaining)
                        //TimerBar.progress = remaining.toInt()
                    }

                    override fun onFinish() {
                        // Phase 3: Breath Out
                        BreathingType.text = "Breath Out"
                        timer = object : CountDownTimer(5000, 10) {
                            override fun onTick(remaining: Long) {
                                TimerText.text = formatTimeToSeconds(remaining)
                                TimerBar.progress = remaining.toInt()
                            }

                            override fun onFinish() {
                                // Phase 4: Hold
                                BreathingType.text = "Hold"
                                timer = object : CountDownTimer(5000, 10) {
                                    override fun onTick(remaining: Long) {
                                        TimerText.text = formatTimeToSeconds(remaining)
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
fun formatTimeToMinutes(milliseconds: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
            TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%02d:%02d", minutes, seconds)
}
fun formatTimeToSeconds(milliseconds: Long): String {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

    return String.format("%02d", seconds)
}


