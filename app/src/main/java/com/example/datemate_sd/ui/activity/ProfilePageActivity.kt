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
import androidx.lifecycle.ViewModel
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityForgetBinding
import com.example.datemate_sd.databinding.ActivityProfilePageBinding
import com.example.datemate_sd.ViewModel.NotificationViewModel
import androidx.activity.viewModels
class ProfilePageActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilePageBinding

    private val notificationViewModel: NotificationViewModel by viewModels()

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

                val targetUserId = "target_user_id_here" // Get the target user's ID
                val targetUserToken = "target_user_fcm_token_here" // Get the target user's FCM token

                // Save the notification in Firestore
                notificationViewModel.saveNotification(targetUserId, "New Like", "Someone liked your profile.")

                // Send a push notification to the target user
                notificationViewModel.sendPushNotification(targetUserToken, "New Like", "Someone liked your profile.")
            }
            isLiked = !isLiked
        }


    }
}


