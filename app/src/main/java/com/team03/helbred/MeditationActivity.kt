package com.team03.helbred

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class MeditationActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)

        val TimerBar: ProgressBar = findViewById(R.id.TimerBar)
        val TimerText:TextView = findViewById(R.id.TimerText)
        val TimerButton : Button = findViewById(R.id.TimerButton)
        var BreathingType : TextView = findViewById(R.id.BreathingText)

        TimerBar.max = 7000
        TimerBar.progress = 0



    timer = object : CountDownTimer(7000,10){
        override fun onTick(remaining: Long) {
            TimerText.text=remaining.toString()
            TimerBar.progress = 7000 - remaining.toInt()

        }

        override fun onFinish() {
            TimerText.text="Done!"
        }

    }
        TimerButton.setOnClickListener {
            timer.start()
        }
    }

    override fun onStart() {
        super.onStart()

    }
    override fun onStop(){
        super.onStop()
        timer.cancel()
    }


    fun BreathingExcerise(BreathingType: TextView){
        BreathingType.text = "Breath In"
        timer.start()
        BreathingType.text = "Hold"
        timer.start()

    }

}