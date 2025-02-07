package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityGenderBinding
import com.example.datemate_sd.model.UserModel

class GenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGenderBinding
    private var selectedGender: String? = null // To store selected gender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the UserModel from the intent
        val userModel = intent.getParcelableExtra<UserModel>("User_Model") ?: UserModel()

        // Set content descriptions for accessibility
        binding.imageMale.contentDescription = "Male"
        binding.imageFemale.contentDescription = "Female"

        // Handle Male selection
        binding.imageMale.setOnClickListener {
            selectGender("Male", binding.imageMale, binding.imageFemale)
        }

        // Handle Female selection
        binding.imageFemale.setOnClickListener {
            selectGender("Female", binding.imageFemale, binding.imageMale)
        }

        // Handle Continue button click
        binding.continueButton.setOnClickListener {
            if (selectedGender != null) {
                // Update the userModel with the selected gender
                userModel.gender = selectedGender!!

                // Pass the updated UserModel to the next activity
                val intent = Intent(this, SelectInterestActivity::class.java)
                intent.putExtra("User_Model", userModel) // Pass the UserModel to the next activity
                startActivity(intent)
            } else {
                // Inform user to select a gender
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun selectGender(gender: String, selected: ImageView, other: ImageView) {
        if (!selected.isSelected) {
            // Set selected gender
            selectedGender = gender

            // Select the clicked ImageView
            selected.isSelected = true
            selected.setBackgroundResource(R.drawable.gradient)

            // Deselect the other ImageView
            other.isSelected = false
            other.setBackgroundResource(R.drawable.button_bg)

            // Enable the Continue button
            binding.continueButton.isEnabled = true

            // Add visual feedback (e.g., change border color)
            selected.setPadding(16, 16, 16, 16)
            other.setPadding(0, 0, 0, 0)
        }
    }
}