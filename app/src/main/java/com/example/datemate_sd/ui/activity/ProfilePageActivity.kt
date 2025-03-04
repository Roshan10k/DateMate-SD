package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityProfilePageBinding
import com.example.datemate_sd.viewmodel.NotificationViewModel
import androidx.activity.viewModels
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.viewmodel.UserViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ProfilePageActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilePageBinding
    lateinit var userViewModel: UserViewModel
    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        val userId = intent.getStringExtra("User").toString()  // Get user ID from the intent
        userViewModel.getUserFromDatabase(userId)

        userViewModel.users.observe(this) {
            binding.nameText.setText(it?.name)
            binding.textProfession.setText(it?.gender)
            Picasso.get().load(it?.imageurl).into(binding.profileImage)
            binding.interests.setText(it?.interestedIn)
            binding.locationOfUser.setText(it?.address)
            binding.lookingForData.setText(it?.idealMatch)
            Picasso.get().load(it?.galleryImage1).into(binding.galleryImage1)
            Picasso.get().load(it?.galleryImage2).into(binding.galleryImage2)
            binding.bornOnData.setText(it?.dateOfBirth)
        }

        binding.backBtn.setOnClickListener {
            val intent = Intent(this@ProfilePageActivity, NavigationActivity::class.java)
            startActivity(intent)
        }

        binding.msgBtn.setOnClickListener {
            val intent = Intent(this@ProfilePageActivity, ChatPageActivity::class.java)
            intent.putExtra("receiverId", userId)
            intent.putExtra("userName", userViewModel.users.value?.name)
            intent.putExtra("userImage", userViewModel.users.value?.imageurl)
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

                val targetUserId = "target_user_id_here"
                val targetUserToken = "target_user_fcm_token_here"

                notificationViewModel.saveNotification(targetUserId, "New Like", "Someone liked your profile.")
                notificationViewModel.sendPushNotification(targetUserToken, "New Like", "Someone liked your profile.")
            }
            isLiked = !isLiked
        }
    }
}
