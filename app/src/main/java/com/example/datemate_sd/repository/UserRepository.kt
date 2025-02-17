package com.example.datemate_sd.repository

import android.content.Context
import android.net.Uri
import com.example.datemate_sd.model.NotificationModel
import com.example.datemate_sd.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun login( email:String,password:String,callback:(Boolean,String) ->Unit)

    fun register(email: String,password: String,callback: (Boolean, String, String) -> Unit)

    fun forgetPassword(email: String,callback: (Boolean, String) -> Unit)

    fun addUserToDatabase(
        userID:String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    )

    fun logout(callback: (Boolean, String) -> Unit)

    fun editProfile(userId:String,data :MutableMap<String,Any>,callback:(Boolean,String) ->Unit)

    fun getCurrentUser(): FirebaseUser?

    fun getUserFromDatabase(
        userId: String,
        callback: (UserModel?,Boolean, String) -> Unit
    )

    fun getAllUsers(callback: (List<UserModel>?, Boolean, String) -> Unit)

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri): String?

    fun saveUserFCMToken()

    fun getUserFCMToken(userId: String, callback: (String?) -> Unit)

    fun saveNotificationToDatabase(userID: String,message : String,callback: (Boolean, String) -> Unit)

    fun getNotificationForUser(userID: String, callback: (List<NotificationModel>?, Boolean, String) -> Unit)



}
