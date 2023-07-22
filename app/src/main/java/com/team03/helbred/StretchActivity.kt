package com.team03.helbred

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
        }
    }
}

