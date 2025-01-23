package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityForgetBinding
import com.example.datemate_sd.databinding.ActivityProfilePageBinding

class ProfilePageActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener{
            val intent = Intent(this@ProfilePageActivity, NavigationActivity::class.java)
            startActivity(intent)

        }


        binding.msgBtn.setOnClickListener{
            val intent = Intent(this@ProfilePageActivity, NavigationActivity::class.java)
            intent.putExtra("navigate_to", "MessageFragment")
            startActivity(intent)

        }

        var isLiked = false

        binding.likeBtn.setOnClickListener {
            if (isLiked) {
                binding.likeBtn.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                Toast.makeText(this, "Unliked!", Toast.LENGTH_SHORT).show()
            } else {
                binding.likeBtn.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.endpink))
                Toast.makeText(this, "Like Sent", Toast.LENGTH_SHORT).show()
            }
            isLiked = !isLiked
        }


    }
}


