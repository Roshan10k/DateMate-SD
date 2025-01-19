package com.example.datemate_sd.repository

import com.example.datemate_sd.model.UserModel
import com.google.firebase.auth.FirebaseUser

class UserRepositoryImpl:UserRepository {
    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addUserToDatabase(
        userID: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun editProfile(
        userId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUSer(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun getUserFromDatabase(
        userId: String,
        callback: (UserModel?, Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}