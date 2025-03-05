package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.datemate_sd.AccessToken
import com.example.datemate_sd.R
import com.example.datemate_sd.api.NotificationApi
import com.example.datemate_sd.databinding.ActivityProfilePageBinding
import com.example.datemate_sd.model.Notification
import com.example.datemate_sd.model.NotificationData
import com.example.datemate_sd.model.UserModel
//import com.example.datemate_sd.viewmodel.NotificationViewModel
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.viewmodel.UserViewModel
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.IOException


class ProfilePageActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilePageBinding

    lateinit var userViewModel: UserViewModel

//    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)


        var userId: String = intent.getStringExtra("User").toString()
        userViewModel.getUserFromDatabase(userId)


        var receiverFCMToken: String? = null
        var likedId: String? = null
        val likerId = userViewModel.getCurrentUser()?.uid.toString()

        userViewModel.users.observe(this) {
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

            receiverFCMToken = it?.fcmToken
            likedId = it?.UserId
        }


        binding.backBtn.setOnClickListener {
            val intent = Intent(this@ProfilePageActivity, NavigationActivity::class.java)
            startActivity(intent)

        }


        binding.msgBtn.setOnClickListener {
            val intent = Intent(this@ProfilePageActivity, ChatPageActivity::class.java)
            intent.putExtra("receiverId", userId)
            intent.putExtra("userName", userViewModel.users.value?.name)  // Pass user's name
            intent.putExtra(
                "userImage",
                userViewModel.users.value?.imageurl
            )  // Pass user's profile image URL
            startActivity(intent)
        }

        var isLiked = false

        binding.likeBtn.setOnClickListener {
            if (isLiked) {
                binding.likeBtn.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                Toast.makeText(this, "Unliked!", Toast.LENGTH_SHORT).show()
                isLiked = false
            } else {
                binding.likeBtn.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.endpink))
                Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show()
                sendNotification(receiverFCMToken.toString(),likedId.toString())
                userViewModel.saveLikes(userId, likerId) { success, message ->
                }
                userViewModel.checkMutualLikes(userId, likerId) { success, message ->
                    if (success) {
                        val intent = Intent(this@ProfilePageActivity, ItsMatchActivity::class.java)
                        intent.putExtra("User", likerId)
                        intent.putExtra("LikedId", likedId)
                        intent.putExtra(
                            "userName",
                            userViewModel.users.value?.name
                        )  // Pass user's name
                        intent.putExtra(
                            "userImage",
                            userViewModel.users.value?.imageurl
                        )  // Pass user's profile image URL
                        startActivity(intent)
                    }
                }
                isLiked = true
            }
        }

    }

    private fun sendNotification(receiverToken: String, likedId: String) {
        val userID = userViewModel.getCurrentUser()?.uid.toString()

        // Fetch the current user data
        FirebaseDatabase.getInstance().getReference("users").child(userID)
            .get()
            .addOnSuccessListener { snapshot ->
                val userName = snapshot.child("name").value.toString()
                val likedName = snapshot.child("name").value.toString()
                val likedImageUrl = snapshot.child("imageurl").value.toString()

                // Fetch the liked user's data using likedId
                FirebaseDatabase.getInstance().getReference("users").child(likedId) // Liked user
                    .get()
                    .addOnSuccessListener { likedSnapshot ->

                        val notification = Notification(
                            message = NotificationData(
                                token = receiverToken,
                                hashMapOf(
                                    "title" to "DateMate",
                                    "body" to "$userName liked your profile ❤\uFE0F",
                                    "LikerId" to userID,
                                    "LikedId" to userID, // Pass the likedId
                                    "LikedName" to likedName, // Pass the liked user's name
                                    "LikedImage" to likedImageUrl // Pass the liked user's image URL
                                )
                            )
                        )

                        NotificationApi.create().sendNotification(notification)
                            .enqueue(object : retrofit2.Callback<Notification> {
                                override fun onResponse(
                                    p0: Call<Notification?>,
                                    p1: Response<Notification?>
                                ) {
                                    Toast.makeText(
                                        this@ProfilePageActivity,
                                        "Notification sent",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                override fun onFailure(
                                    p0: Call<Notification?>,
                                    p1: Throwable
                                ) {
                                    Toast.makeText(
                                        this@ProfilePageActivity,
                                        "Error: ${p1.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                    }
            }
    }
}




//private fun ProfilePageActivity.sendNotification(receiverToken: String) {
//    val senderUserId = userViewModel.getCurrentUser()?.uid.toString();
//
//    val jsonObject = JSONObject().apply {
//        put("to", receiverToken)
//        put("priority", "high")
//
//        val notification = JSONObject().apply {
//            put("title", "New Like!")
//            put("body", "Someone liked your profile.")
//        }
//
//        val data = JSONObject().apply {
//            put("userId",senderUserId) // Replace with sender's user ID
//        }
//
//        put("notification", notification)
//        put("data", data)
//    }
//
//    callApi(jsonObject)
//}

//
//fun callApi(jsonObject: JSONObject) {
//    val JSON = "application/json".toMediaType() // Correct way to specify media type
//    val client = OkHttpClient()
//
//    val url = "https://fcm.googleapis.com/v1/projects/datemate-6978d/messages:send"
//    val body = jsonObject.toString().toRequestBody(JSON)
//
//    val request = Request.Builder()
//        .url(url)
//        .post(body)
//        .header("Authorization", AccessToken.toString()) // Replace with actual server key
//        .header("Content-Type", "application/json")
//        .build()
//
//    client.newCall(request).enqueue(object : okhttp3.Callback{
//        override fun onFailure(call: Call, e: IOException) {
//            e.printStackTrace()
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            response.use {
//                if (!response.isSuccessful) {
//                    println("Failed to send notification: ${response.code}")
//                } else {
//                    println("Notification sent successfully")
//                }
//            }        }
//
//    })
//}
