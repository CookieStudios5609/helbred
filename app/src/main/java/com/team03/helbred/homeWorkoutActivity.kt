package com.team03.helbred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class homeWorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_workout)
        clickListener();
    }

    public fun clickListener(){

        var imghk = findViewById<ImageView>(R.id.ivhk);
        var imgjj = findViewById<ImageView>(R.id.ivjj);
        var imgs = findViewById<ImageView>(R.id.ivs);
        var imgp = findViewById<ImageView>(R.id.ivp);
        var imgl = findViewById<ImageView>(R.id.ivl);
        var imgkte = findViewById<ImageView>(R.id.ivkte);

        imghk.setOnClickListener {
        openHK()
        }
        imgjj.setOnClickListener {
            openJJ()
        }
        imgs.setOnClickListener {
            openS()
        }
        imgp.setOnClickListener {
            openP()
        }
        imgl.setOnClickListener {
            openL()
        }
        imgkte.setOnClickListener {
            openKTE()
        }



    }

    public fun openHK(){
        startActivity(Intent(this@homeWorkoutActivity, highKnees::class.java ))

    }

    public fun openJJ(){
        startActivity(Intent(this@homeWorkoutActivity, jumpingJacks::class.java ))

    }

    public fun openS(){
        startActivity(Intent(this@homeWorkoutActivity, squats::class.java ))

    }

    public fun openP(){
        startActivity(Intent(this@homeWorkoutActivity, punches::class.java ))

    }

    public fun openL(){
        startActivity(Intent(this@homeWorkoutActivity, lunges::class.java ))


    }

    public fun openKTE(){
        startActivity(Intent(this@homeWorkoutActivity, kneeToElbow::class.java ))


    }

}