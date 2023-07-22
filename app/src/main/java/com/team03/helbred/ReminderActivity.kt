package com.team03.helbred

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
    }

    fun drinkWater(view: View) {
        Toast.makeText(this, "Don't forget to drink water throughout the day!", Toast.LENGTH_SHORT).show()
    }

    fun workout(view: View) {
        Toast.makeText(this, "It's time to workout!", Toast.LENGTH_SHORT).show()
    }
}