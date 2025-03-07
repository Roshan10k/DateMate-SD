package com.example.datemate_sd.service
import android.R.id.message
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.datemate_sd.ui.activity.LoginActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.example.datemate_sd.R
import java.util.Random
import android.Manifest
import android.widget.Toast
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.ui.activity.ItsMatchActivity
import com.example.datemate_sd.viewmodel.UserViewModel
import com.google.firebase.firestore.auth.User

class NotificationService : FirebaseMessagingService() {

    private val channelID = "class-update"
    private val channelName = "class-updates"

    lateinit var userViewModel: UserViewModel

    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        val currentUserId = userViewModel.getCurrentUser()?.uid.toString()
        val likerId = message.data["LikerId"] ?: ""
        val likedId = message.data["LikedId"] ?: ""
        val userName = message.data["LikedName"] ?: ""
        val userImage = message.data["LikedImage"] ?: ""


        if (currentUserId!= null){
            userViewModel.saveNotificationToDatabase(currentUserId,likerId,message.data["body"] ?: ""){success , message ->
                if (success){
                    Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext, "Error: $message", Toast.LENGTH_SHORT).show()
                }

            }
        }
        // Check if mutual likes exist before opening ProfilePageActivity
        userViewModel.checkMutualLikes(currentUserId,likerId) { isMutual, message ->
            if (isMutual) {
                // If mutual like exists, open ProfilePageActivity
                val intent = Intent(this@NotificationService, ItsMatchActivity::class.java)
                intent.putExtra("User", likerId)
                intent.putExtra("LikedId", likedId)
                intent.putExtra("userName", userName)
                intent.putExtra("userImage", userImage)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK // Ensure activity is started correctly
                startActivity(intent)
            } else {
                // Handle case when mutual like doesn't exist
                Toast.makeText(this@NotificationService, "No mutual like found", Toast.LENGTH_SHORT).show()
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(IconCompat.createWithResource(applicationContext, R.drawable.ic_launcher_foreground))
            .setColor(applicationContext.getColor(R.color.black))
            .setContentTitle(message.data["title"])
            .setContentText(message.data["body"])
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setBadgeIconType(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setOngoing(false)
            .setLights(
                ContextCompat.getColor(applicationContext, R.color.black),
                5000,
                5000
            )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            with(NotificationManagerCompat.from(applicationContext)) {
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                notify(Random().nextInt(3000), builder.build())
            }
        }else{
            NotificationManagerCompat.from(applicationContext).notify(Random().nextInt(3000), builder.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH,
        )
        notificationManager.createNotificationChannel(channel)
    }



}
