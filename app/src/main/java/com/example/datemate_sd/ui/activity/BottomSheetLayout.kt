package com.example.datemate_sd.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityProfileDetailsBinding
import com.example.datemate_sd.databinding.BottomSheetLayoutBinding

class BottomSheetLayout : AppCompatActivity() {
    private lateinit var binding: BottomSheetLayoutBinding
    private lateinit var interestedInMan: Button
    private lateinit var interestedInWoman: Button



    private val cities = arrayOf(
        "Kathmandu", "Bhaktapur", "Lalitpur", "Pokhara",
        "Butwal", "Janakpur", "Dharan", "Mahendranagar", "Biratnagar"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= BottomSheetLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize buttons
        val interestedInMan = binding.interestedInMan
        val interestedInWoman = binding.interestedInWoman



        val cityAdapter = ArrayAdapter(
            this@BottomSheetLayout,
            android.R.layout.simple_dropdown_item_1line,
            cities
        )
        binding.addressSpinner.adapter = cityAdapter


        // Set up button click listeners
        val buttons = listOf(interestedInMan, interestedInWoman)
        buttons.forEach { button ->
            button.setOnClickListener {
                setSelectedButton(button, buttons)
            }
        }

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun setSelectedButton(selected: Button, buttons: List<Button>) {
        // Debug log to verify button clicks
        println("Selected button: ${selected.text}")

        // Update states for all buttons
        buttons.forEach { button ->
            button.isSelected = button == selected
            if (button.isSelected) {
                button.setTextColor(resources.getColor(R.color.white, theme))
                button.setBackgroundColor(resources.getColor(R.color.endpink, theme))
            } else {
                button.setTextColor(resources.getColor(R.color.white, theme))
                button.setBackgroundColor(resources.getColor(R.color.background, theme))
            }
        }



    }
}
