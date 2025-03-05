package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityItsMatchBinding
import com.example.datemate_sd.ui.activity.ProfilePageActivity

class ItsMatchActivity : AppCompatActivity() {
    lateinit var binding: ActivityItsMatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityItsMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId: String = intent.getStringExtra("User").toString()
        val likedId: String = intent.getStringExtra("LikedId").toString()
        val userName: String = intent.getStringExtra("userName").toString()
        val userImage: String = intent.getStringExtra("userImage").toString()


        binding.back.setOnClickListener{
            val intent = Intent(this@ItsMatchActivity, NavigationActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.helloBtn.setOnClickListener{
            val intent = Intent(this@ItsMatchActivity, ChatPageActivity::class.java)
            intent.putExtra("receiverId", likedId)
            intent.putExtra("userName", userName)  // Pass user's name
            intent.putExtra("userImage", userImage)  // Pass user's profile image URL
            startActivity(intent)
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}