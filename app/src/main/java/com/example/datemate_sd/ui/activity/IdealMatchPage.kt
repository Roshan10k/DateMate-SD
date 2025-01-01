package com.example.datemate_sd.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R

class IdealMatchPage : AppCompatActivity() {
    private var selectedOption: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ideal_match_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loveOption = findViewById<LinearLayout>(R.id.loveOption)
        val friendsOption = findViewById<LinearLayout>(R.id.friendsOption)
        val businessOption = findViewById<LinearLayout>(R.id.businessOption)

        resetOptionsBackground()
        loveOption.setBackgroundResource(R.drawable.gradient)
        selectedOption = "Love"

        loveOption.setOnClickListener {
            handleOptionSelection("Love", loveOption, friendsOption, businessOption)
        }

        friendsOption.setOnClickListener {
            handleOptionSelection("Friends", friendsOption, loveOption, businessOption)
        }

        businessOption.setOnClickListener {
            handleOptionSelection("Business", businessOption, loveOption, friendsOption)
        }

        // Handle continue button click
        val continueButton = findViewById<android.widget.Button>(R.id.loginButton)
        continueButton.setOnClickListener {
            if (selectedOption != null) {
                Log.d("IdealMatchPage", "Selected Option: $selectedOption")
                Toast.makeText(this, "Selected: $selectedOption", Toast.LENGTH_SHORT).show()
                // detebase coding
            } else {
                Toast.makeText(this, "Please select an option before continuing.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     *
     * @param option The selected option's name.
     * @param selectedView The selected option's view.
     * @param otherViews The unselected option views.
     */
    private fun handleOptionSelection(
        option: String,
        selectedView: LinearLayout,
        vararg otherViews: LinearLayout
    ) {
        selectedOption = option
        resetOptionsBackground()
        selectedView.setBackgroundResource(R.drawable.gradient)
        otherViews.forEach { it.setBackgroundResource(R.drawable.button_bg) }
    }


    private fun resetOptionsBackground() {
        val loveOption = findViewById<LinearLayout>(R.id.loveOption)
        val friendsOption = findViewById<LinearLayout>(R.id.friendsOption)
        val businessOption = findViewById<LinearLayout>(R.id.businessOption)

        loveOption.setBackgroundResource(R.drawable.button_bg)
        friendsOption.setBackgroundResource(R.drawable.button_bg)
        businessOption.setBackgroundResource(R.drawable.button_bg)
    }
}
