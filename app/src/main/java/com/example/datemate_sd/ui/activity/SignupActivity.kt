package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datemate_sd.databinding.ActivitySignupBinding
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.viewmodel.UserViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.BackTOLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.SignupBtn.setOnClickListener {
            val email = binding.EmailView.text.toString().trim()
            val password = binding.passwordView.text.toString().trim()
            val confirmPassword = binding.passwordView2.text.toString().trim() // Add confirm password field

            // Input validation
            if (email.isEmpty()) {
                showToast("Email cannot be empty")
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                showToast("Password cannot be empty")
                return@setOnClickListener
            }



            if (confirmPassword.isEmpty()) {
                showToast("Confirm password cannot be empty")
                return@setOnClickListener
            }


            if (password != confirmPassword) {
                showToast("Passwords do not match")
                return@setOnClickListener
            }
            if (password.length < 6) {
                showToast("Password must be at least 6 characters long")
                return@setOnClickListener
            }

            userViewModel.register(email, password) { success, message, userId ->
                if (success) {
                    val userModel = UserModel(userId, email, password)

                    val intent = Intent(this, ProfileDetailsActivity::class.java)
                    intent.putExtra("User_Model", userModel)
                    startActivity(intent)
                    finish()
                } else {
                    showToast(message)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
