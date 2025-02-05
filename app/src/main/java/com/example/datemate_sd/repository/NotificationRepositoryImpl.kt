package com.example.datemate_sd.repository

// NotificationRepositoryImpl.kt
import android.util.Log
import com.example.datemate_sd.model.NotificationModel
import com.example.datemate_sd.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class NotificationRepositoryImpl : NotificationRepositoryInterface {

    private val db = FirebaseFirestore.getInstance()

    // Save notification to Firestore
    override fun saveNotification(receiverId: String, title: String, description: String) {
        val notification = NotificationModel(title, description)
        db.collection("users").document(receiverId)
            .collection("notifications")
            .add(notification)
            .addOnSuccessListener {
                Log.d("Firebase", "Notification stored successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Failed to store notification", e)
            }
    }

    // Listen for notifications in real-time
    override fun listenForNotifications(userId: String, callback: (Boolean, List<NotificationModel>?, String?) -> Unit) {
        db.collection("users").document(userId)
            .collection("notifications")
            .orderBy("timestamp")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.e("Firebase", "Error fetching notifications", e)
                    callback(false, null, e.message)
                    return@addSnapshotListener
                }

                val notificationList = snapshots?.map { it.toObject(NotificationModel::class.java) } ?: emptyList()
                callback(true, notificationList, null)
            }
    }

    // Send push notification via FCM
    override fun sendPushNotification(receiverToken: String, title: String, message: String) {
        val json = JSONObject().apply {
            put("to", receiverToken)
            put("notification", JSONObject().apply {
                put("title", title)
                put("body", message)
            })
        }

        val client = OkHttpClient()
        val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(Constants.FCM_URL)
            .post(requestBody)
            .addHeader("Authorization", "key=${Constants.FCM_SERVER_KEY}")
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("FCM", "Failed to send notification", e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("FCM", "Notification sent successfully: ${response.body?.string()}")
            }
        })
    }
}
