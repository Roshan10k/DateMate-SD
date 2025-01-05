package com.example.datemate_sd.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityGenderBinding

class GenderActivity : AppCompatActivity() {
    lateinit var  binding: ActivityGenderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGenderBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imageMale.setOnClickListener {
            selectGender(binding.imageMale,binding.imageFemale)

        }

        binding.imageFemale.setOnClickListener {
            selectGender(binding.imageFemale,binding.imageMale)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun selectGender(selected: ImageView, other: ImageView) {
        if (!selected.isSelected) {
            // Select the clicked ImageView
            selected.isSelected = true
            selected.setBackgroundResource(R.drawable.gradient)

            // Deselect the other ImageView
            other.isSelected = false
            other.setBackgroundResource(R.drawable.button_bg)
        }
    }
}