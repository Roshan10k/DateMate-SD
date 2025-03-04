package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityEditProfileBinding
import com.example.datemate_sd.databinding.ActivityTermsAndConditionBinding

class TermsAndConditionActivity : AppCompatActivity() {
    lateinit var binding: ActivityTermsAndConditionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityTermsAndConditionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrow.setOnClickListener{
           finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}