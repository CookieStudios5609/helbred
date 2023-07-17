package com.team03.helbred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class WorkoutLocaleChooserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_locale_chooser)
    }
//    fun goToWorkoutPage(button: ImageButton) {
//        val toPage =  Intent(this, WorkoutsActivity::class.java)
//        if (button.id.toString() == "gymButtonChoice") {
//            toPage.putExtra("locale", "G")
//        }
//        if (button.id.toString() == "homeButtonChoice") {
//            toPage.putExtra("locale", "H")
//        }
//        startActivity(toPage)
//    }
}