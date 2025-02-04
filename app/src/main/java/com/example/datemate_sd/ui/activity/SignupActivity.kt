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
    lateinit var binding: ActivitySignupBinding
    lateinit var userViewModel: UserViewModel

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

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userViewModel.register(email, password) { success, message, userId ->
                if (success) {
                    val userModel = UserModel(userId, email, password)

                    
//                    userViewModel.addUserToDatabase(userId, userModel) { isSuccess, msg ->
//                        if (isSuccess) {
//                            Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()
//
//
                            val intent = Intent(this, ProfileDetailsActivity::class.java)
                            intent.putExtra("User_Model", userModel)
                            startActivity(intent)
                            finish()
//                        } else {
//                            Toast.makeText(this, "Failed to save user details", Toast.LENGTH_SHORT).show()
//                        }
//                    }
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
