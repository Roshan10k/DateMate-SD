package com.example.datemate_sd.repository

import com.example.datemate_sd.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class UserRepositoryImpl:UserRepository {
    var auth:FirebaseAuth = FirebaseAuth.getInstance()
    var database:FirebaseDatabase=FirebaseDatabase.getInstance()
    var reference = database.reference.child("users")

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true,"Login successful")
            }else{
                callback(false,it.exception?.message.toString())
            }
        }
    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true,"Registration succesful",auth.currentUser?.uid.toString())
            }else{
                callback(false,it.exception?.message.toString(),"")
            }
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true,"Password reset link sent to $email ")
            }else{
                callback(false,it.exception?.message.toString())
            }
        }
    }

    override fun addUserToDatabase(
        userID: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        try {
            auth.signOut()
            callback(true,"Logout successful")

        }catch (e:Exception){
            callback(false,e.message.toString())
        }
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