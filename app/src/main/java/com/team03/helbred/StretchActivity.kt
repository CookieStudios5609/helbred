package com.team03.helbred

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.time.LocalDate
import java.util.Scanner

class StretchActivity : AppCompatActivity() {

    private val stretchList = arrayOf(
        "Calf Stretch",
        "Hamstring Stretch",
        "Quadriceps Stretch",
        "Hip Flexor Stretch"
    )

    private val stretchImages = arrayOf(
        R.drawable.calf_stretch,
        R.drawable.hamstring_stretch,
        R.drawable.quadriceps_stretch,
        R.drawable.hip_flexor_stretch
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stretch)

        val titleTextView: TextView = findViewById(R.id.title_text_view)
        val listView: ListView = findViewById(R.id.stretch_list_view)
        val imageView: ImageView = findViewById(R.id.stretch_image_view)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stretchList)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedStretch = stretchList[position]
            val selectedImage = stretchImages[position]

            titleTextView.text = selectedStretch
            imageView.setImageResource(selectedImage)
            writeTotal(100)
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
                strings[4] = value.toString()
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
}

