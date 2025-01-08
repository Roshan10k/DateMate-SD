package com.example.datemate_sd.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R

class SelectInterestActivity : AppCompatActivity() {

    private val selectedInterests = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_interest)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonIds = arrayOf(
            R.id.button10,
            R.id.button12,
            R.id.button14,
            R.id.button15,
            R.id.button16,
            R.id.button13,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button11,

        )

        // Initialize all buttons and set click listeners
        buttonIds.forEach { id ->
            val button = findViewById<Button>(id)
            button.setOnClickListener {
                handleButtonClick(button)
            }
        }

        // Handle "Continue" button
        val continueButton = findViewById<Button>(R.id.continue_button)
        continueButton.setOnClickListener {
            handleContinueClick()
        }
    }

    private fun handleButtonClick(button: Button) {
        if (button.isSelected) {
            // Deselect button
            button.isSelected = false
            button.setBackgroundResource(R.drawable.button_bg)
            selectedInterests.remove(button.text.toString())
        } else {
            // Select button
            button.isSelected = true
            button.setBackgroundResource(R.drawable.gradient)
            selectedInterests.add(button.text.toString())
        }
    }

    private fun handleContinueClick() {
        if (selectedInterests.isEmpty()) {
            Toast.makeText(this, "Please select at least one interest", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                this,
                "Selected Interests: ${selectedInterests.joinToString(", ")}",
                Toast.LENGTH_LONG
            ).show()

            // TODO: logic to send data to the database
        }
    }
}
