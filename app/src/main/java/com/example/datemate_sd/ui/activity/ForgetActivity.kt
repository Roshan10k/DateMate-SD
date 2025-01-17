package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityForgetBinding

class ForgetActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgetBackArrow.setOnClickListener{
            val intent = Intent(this@ForgetActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}