package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityIdealMatchPageBinding
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.viewmodel.UserViewModel

class IdealMatchPage : AppCompatActivity() {
    private var selectedOption: String? = null
    private lateinit var binding: ActivityIdealMatchPageBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userModel: UserModel // Store the UserModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityIdealMatchPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize repository
        userViewModel = UserViewModel(UserRepositoryImpl())

        // Retrieve UserModel from the intent
        userModel = intent.getParcelableExtra("User_Model") ?: UserModel()

        // Set up click listeners for options
        resetOptionsBackground()
        binding.loveOption.setBackgroundResource(R.drawable.gradient)
        selectedOption = "Love"

        binding.loveOption.setOnClickListener {
            handleOptionSelection("Love", binding.loveOption, binding.friendsOption, binding.businessOption)
        }

        binding.friendsOption.setOnClickListener {
            handleOptionSelection("Friends", binding.friendsOption, binding.loveOption, binding.businessOption)
        }

        binding.businessOption.setOnClickListener {
            handleOptionSelection("Business", binding.businessOption, binding.loveOption, binding.friendsOption)
        }

        // Handle continue button click
        binding.loginButton.setOnClickListener {
            if (selectedOption != null) {
                saveIdealMatchToDatabase()
            } else {
                Toast.makeText(this, "Please select an option before continuing.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleOptionSelection(option: String, selectedView: LinearLayout, vararg otherViews: LinearLayout) {
        selectedOption = option
        resetOptionsBackground()
        selectedView.setBackgroundResource(R.drawable.gradient)
        otherViews.forEach { it.setBackgroundResource(R.drawable.button_bg) }
    }

    private fun resetOptionsBackground() {
        binding.loveOption.setBackgroundResource(R.drawable.button_bg)
        binding.friendsOption.setBackgroundResource(R.drawable.button_bg)
        binding.businessOption.setBackgroundResource(R.drawable.button_bg)
    }

    private fun saveIdealMatchToDatabase() {
        // Update the userModel with the selected ideal match
        userModel.idealMatch = selectedOption ?: ""

        userViewModel.addUserToDatabase(userModel.UserId, userModel) { isSuccess, message ->
            if (isSuccess) {
                Toast.makeText(this, "User is registered successfully", Toast.LENGTH_SHORT).show()
                // Navigate to the next activity
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "error in registration: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}