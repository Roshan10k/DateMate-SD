package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityForgetBinding
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgetActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgetBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val repo = UserRepositoryImpl()

        // Initialize ViewModel (assuming you have a way to get the ViewModel instance)
        userViewModel = UserViewModel(repo) // Replace with your actual ViewModel initialization


        binding.forgetBackArrow.setOnClickListener{
            val intent = Intent(this@ForgetActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignUp.setOnClickListener{
            val intent=Intent(this@ForgetActivity,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.sendButton.setOnClickListener {
            val email = binding.editEmail.text.toString()
            userViewModel.forgetPassword(email){
                    success,message->
                if (success){
                    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
                }
            }


        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}