package com.example.datemate_sd.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.datemate_sd.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
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
//        val userMap = hashMapOf<String, Any>(
//            "emailAddress" to userModel.emailAddress,
//            "name" to userModel.name,
//            "username" to userModel.username,
//            "phnNumber" to userModel.phnNumber,
//            "dateOfBirth" to userModel.dateOfBirth,
//            "address" to userModel.address,
//            "gender" to userModel.gender,
//            "interestedIn" to userModel.interestedIn,
//            "idealMatch" to userModel.idealMatch
//        )

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

    override fun logout(callback: (Boolean, String) -> Unit) {
        try {
            auth.signOut()
            callback(true, "Logout successful")
        } catch (e: Exception) {
            callback(false, e.message.toString())
        }
    }

    override fun editProfile(userId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        reference.child(userId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Profile details edited successfully")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun getCurrentUSer(): FirebaseUser ? {
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
                            var currentUserId = getCurrentUSer()?.uid.toString()
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
}