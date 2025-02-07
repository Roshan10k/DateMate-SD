package com.example.datemate_sd.ui.activity

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R

class BottomSheetLayout : AppCompatActivity() {

    private lateinit var interestedInMan: Button
    private lateinit var interestedInWoman: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.bottom_sheet_layout)

        // Initialize buttons
        interestedInMan = findViewById(R.id.interestedInMan)
        interestedInWoman = findViewById(R.id.interestedInWoman)


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
