package com.team03.helbred

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.io.File
import java.time.LocalDate

class PreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferences_activity)

        val reset = findViewById<Button>(R.id.res_btn)
        val threshSet = findViewById<Button>(R.id.submit_btn)
        val thresh1 = findViewById<EditText>(R.id.editThr1)
        val thresh2 = findViewById<EditText>(R.id.editThr2)
        val thresh3 = findViewById<EditText>(R.id.editThr3)
        val thresh4 = findViewById<EditText>(R.id.editThr4)

        var holds = getThresholds()
        thresh1.setText(holds[0].toString())
        thresh2.setText(holds[1].toString())
        thresh3.setText(holds[2].toString())
        thresh4.setText(holds[3].toString())

        reset.setOnClickListener {
            val streakfile = File(applicationContext.filesDir, "streak")
            val make = streakfile.bufferedWriter()
            make.write(String.format("%s, 0, 0, 0, 0, 0", LocalDate.now().toString()))
            make.close()
        }
        threshSet.setOnClickListener {
            writeThresholds(
                thresh1.text.toString().toInt(),
                thresh2.text.toString().toInt(),
                thresh3.text.toString().toInt(),
                thresh4.text.toString().toInt())
            holds = getThresholds()
            thresh1.setText(holds[0].toString())
            thresh2.setText(holds[1].toString())
            thresh3.setText(holds[2].toString())
            thresh4.setText(holds[3].toString())
        }

    }

    private fun getThresholds(): ArrayList<Int>{ //lets just hope no bad info gets in here
        val prefs = File(applicationContext.filesDir, "pref")
        val prefReader = prefs.bufferedReader()
        val threshs = prefReader.readLine().split(",")
        val threshes: ArrayList<Int> = ArrayList<Int>()
        for (strang in threshs) {
            threshes.add(strang.toInt())
        }
        prefReader.close()
        return threshes
    }
    private fun writeThresholds(thr_1: Int, thr_2: Int, thr_3: Int, thr_4: Int) {
        val prefs = File(applicationContext.filesDir, "pref")
            val prefWriter = prefs.bufferedWriter()
            prefWriter.write(String.format("%d,%d,%d,%d", thr_1, thr_2, thr_3, thr_4))
            prefWriter.close()
    }

}