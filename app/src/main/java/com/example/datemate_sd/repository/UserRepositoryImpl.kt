package com.example.datemate_sd.repository

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.datemate_sd.model.NotificationModel
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.ui.activity.ItsMatchActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import java.io.InputStream
import java.util.concurrent.Executors

class UserRepositoryImpl : UserRepository {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference = database.reference.child("users")

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login successful")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    callback(true,"succes",userId)
                } else {

                    callback(false, task.exception?.message.toString(), "")
                }
            }
    }





    override fun addUserToDatabase(userID: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        reference.child(userID).setValue(userModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "User  added to database successfully")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Password reset link sent to $email")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun updateProfile(
        userId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(userId).updateChildren(data).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Profile updated successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        try {
            auth.signOut()
            callback(true, "Logout successful")
        } catch (e: Exception) {
            callback(false, e.message.toString())
        }
    }

    override fun getCurrentUser(): FirebaseUser ? {
        return auth.currentUser
    }

    override fun getUserFromDatabase(userId: String, callback: (UserModel?, Boolean, String) -> Unit) {
        reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(UserModel::class.java)
                    callback(user, true, "User  data fetched successfully")
                } else {
                    callback(null, false, "User  not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun getAllUsers(callback: (List<UserModel>?, Boolean, String) -> Unit) {
        reference.addValueEventListener(
            object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var Users = mutableListOf<UserModel>()
                        for (eachData in snapshot.children){
                            var model = eachData.getValue(UserModel::class.java)
                            var currentUserId = getCurrentUser()?.uid.toString()
                            if (model != null && model.UserId!=currentUserId){
                                Users.add(model)
                            }
                        }
                        callback(Users,true,"User fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null,false,error.message)
                }

            }
        )
    }

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dkveof11h",
            "api_key" to "567832146462918",
            "api_secret" to "6q2XWdyL2glCB0lO8WHavbz8PUk"
        )
    )

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }

    override fun saveUserFCMToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            auth.currentUser?.uid?.let { uid ->
                reference.child(uid).child("fcmToken").setValue(token)
            }
        }
    }

    override fun getUserFCMToken(userId: String, callback: (String?) -> Unit) {
        reference.child(userId).child("fcmToken").get().addOnSuccessListener {
            callback(it.value as? String)
        }
    }

    override fun saveNotificationToDatabase(
        userID: String,
        likerId: String,
        message: String,
        callback: (Boolean, String) -> Unit
    ) {
        val notificationsRef = database.reference.child("notifications").child(userID)
        val notificationId = notificationsRef.push().key ?: return callback(false, "Error generating ID")

        val notificationData = hashMapOf(
            "notificationId" to notificationId,
            "likerId" to likerId,
            "message" to message,
            "timestamp" to System.currentTimeMillis()
        )

        notificationsRef.child(notificationId).setValue(notificationData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Notification saved successfully")
                } else {
                    callback(false, it.exception?.message.toString())
                }
            }
    }

    override fun saveLikes(
        userID: String,
        likerId: String,
        callback: (Boolean, String) -> Unit
    ) {
        val database = FirebaseDatabase.getInstance().reference
        val likeRef = database.child("likes").child(userID).child(likerId)

        likeRef.setValue(true) { error, _ ->
            if (error != null) {
                callback(false, "Failed to save like: ${error.message}")
            } else {
                callback(true, "Like saved successfully")
            }
        }
    }
    override fun checkMutualLikes(
        userID: String,
        likerId: String,
        callback: (Boolean, String) -> Unit
    ) {
        val database = FirebaseDatabase.getInstance().reference
        val userLikesRef = database.child("likes").child(userID).child(likerId)
        val likerLikesRef = database.child("likes").child(likerId).child(userID)

        // Check if both users have liked each other
        userLikesRef.get().addOnCompleteListener { userLikeTask ->
            if (userLikeTask.isSuccessful && userLikeTask.result.exists()) {
                likerLikesRef.get().addOnCompleteListener { likerLikeTask ->
                    if (likerLikeTask.isSuccessful && likerLikeTask.result.exists()) {
                        // Both users have liked each other, mutual like exists
                        callback(true, "Mutual like found")
                    } else {
                        // No mutual like from the liker to the user
                        callback(false, "No mutual like found from $likerId to $userID")
                    }
                }
            } else {
                // No like from user to the liker
                callback(false, "No like found from $userID to $likerId")
            }
        }
    }



    override fun getNotificationForUser(
        userID: String,
        callback: (List<NotificationModel>?, Boolean, String) -> Unit
    ) {
        val notificationsRef = database.reference.child("notifications").child(userID)

        notificationsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Create a list to store the notifications
                    val notificationsList = mutableListOf<NotificationModel>()

                    // Iterate through all the notifications for this user
                    for (notificationSnapshot in snapshot.children) {
                        val notification = notificationSnapshot.getValue(NotificationModel::class.java)
                        if (notification != null) {
                            notificationsList.add(notification)
                        }
                    }
                    callback(notificationsList, true, "Notifications fetched successfully")
                } else {
                    callback(null, false, "No notifications found for this user")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)            }
        })
    }





}