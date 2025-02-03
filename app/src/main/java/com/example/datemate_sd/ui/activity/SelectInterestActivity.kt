package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivitySelectInterestBinding
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.repository.UserRepository

class SelectInterestActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectInterestBinding
    private val selectedInterests = mutableSetOf<String>()
    private lateinit var userRepository: UserRepository
    private lateinit var userModel: UserModel // Store the UserModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySelectInterestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = UserRepositoryImpl()
        userModel = intent.getParcelableExtra("User_Model") ?: UserModel() // Retrieve UserModel

        // Set up the button IDs for interests
        val buttonIds = arrayOf(
            R.id.button10, R.id.button12, R.id.button14, R.id.button15, R.id.button16,
            R.id.button13, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button11
        )

        // Set click listeners for all interest buttons
        buttonIds.forEach { id ->
            val button = findViewById<Button>(id)
            button.setOnClickListener {
                handleButtonClick(button)
            }
        }

        // Handle "Continue" button click
        binding.continueButton.setOnClickListener {
            handleContinueClick()
        }
    }

    private fun handleButtonClick(button: Button) {
        if (button.isSelected) {
            // Deselect button
            button.isSelected = false
            button.setBackgroundResource(R.drawable.button_bg) // Default background
            selectedInterests.remove(button.text.toString())
        } else {
            // Select button
            button.isSelected = true
            button.setBackgroundResource(R.drawable.gradient) // Selected background
            selectedInterests.add(button.text.toString())
        }
    }

    private fun handleContinueClick() {
        if (selectedInterests.isEmpty()) {
            Toast.makeText(this, "Please select at least one interest", Toast.LENGTH_SHORT).show()
        } else {
            // Update the userModel with selected interests
            userModel.interestedIn = selectedInterests.joinToString(", ")

                                val intent = Intent(this, IdealMatchPage::class.java)
                    intent.putExtra("User_Model", userModel) // Pass the UserModel to the next activity
                    startActivity(intent)
            // Call the repository to save the updated userModel
//            userRepository.addUserToDatabase(userModel.UserId, userModel) { isSuccess, message ->
//                if (isSuccess) {
//                    Toast.makeText(this, "Interests updated successfully", Toast.LENGTH_SHORT).show()
//                    // Pass the UserModel to the IdealMatchPage
//                    val intent = Intent(this, IdealMatchPage::class.java)
//                    intent.putExtra("User  Model", userModel) // Pass the UserModel to the next activity
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(this, "Failed to update interests: $message", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }
