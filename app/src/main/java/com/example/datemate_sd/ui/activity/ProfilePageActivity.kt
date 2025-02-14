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

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)


        var userId: String = intent.getStringExtra("User").toString()
        userViewModel.getUserFromDatabase(userId)



        userViewModel.users.observe(this){
            binding.nameText.setText(it?.name.toString())
            binding.textProfession.setText(it?.gender.toString())
            Picasso.get()
                .load(it?.imageurl)  // URL from database
                .placeholder(R.drawable.sampleperson1)
                .into(binding.profileImage)
            binding.interests.setText(it?.interestedIn.toString())
            binding.locationOfUser.setText(it?.address.toString())
            binding.lookingForData.setText(it?.idealMatch.toString())
            Picasso.get()
                .load(it?.galleryImage1)
                .placeholder(R.drawable.sampleperson1)
                .into(binding.galleryImage1)
            Picasso.get()
                .load(it?.galleryImage2)
                .placeholder(R.drawable.sampleperson1)
                .into(binding.galleryImage2)
            binding.bornOnData.setText(it?.dateOfBirth.toString())
        }



        binding.backBtn.setOnClickListener{
            val intent = Intent(this@ProfilePageActivity, NavigationActivity::class.java)
            startActivity(intent)

        }


        binding.msgBtn.setOnClickListener {
            val intent = Intent(this@ProfilePageActivity, ChatPageActivity::class.java)
            intent.putExtra("receiverId", userId)
            intent.putExtra("userName", userViewModel.users.value?.name)  // Pass user's name
            intent.putExtra("userImage", userViewModel.users.value?.imageurl)  // Pass user's profile image URL
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


