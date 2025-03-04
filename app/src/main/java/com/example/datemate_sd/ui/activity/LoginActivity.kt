package com.example.datemate_sd.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityLoginBinding
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class LoginActivity : AppCompatActivity() {
    lateinit var  binding: ActivityLoginBinding

    lateinit var  userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.SignupBtn.setOnClickListener{
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginButton.setOnClickListener{
            val email = binding.EmailView.text.toString()
            val password = binding.passwordView.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userViewModel.login(email, password) { success, message ->
                if (success) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity,NavigationActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }

        }

        binding.forgetbtn.setOnClickListener{
            val intent = Intent(this@LoginActivity, ForgetActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            Dexter.withContext(applicationContext)
                .withPermission(Manifest.permission.POST_NOTIFICATIONS)
                .withListener(object : PermissionListener{
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {}

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {}

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        p1?.continuePermissionRequest()
                    }

                }).check()

        }

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}