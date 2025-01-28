package com.example.datemate_sd.viewmodel

import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo:UserRepository) {
    fun login( email:String,password:String,callback:(Boolean,String) ->Unit){
        repo.login(email,password, callback)

    }

    fun register(email: String,password: String,callback: (Boolean, String, String) -> Unit){
        repo.register(email, password, callback)

    }

    fun forgetPassword(email: String,callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email, callback)
    }

    fun addUserToDatabase(
        userID:String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ){
        repo.addUserToDatabase(userID, userModel, callback)

    }

    fun logout(callback: (Boolean, String) -> Unit){
        repo.logout(callback)

    }

    fun editProfile(userId:String,data :MutableMap<String,Any>,callback:(Boolean,String) ->Unit){
        repo.editProfile(userId, data, callback)

    }



    fun getUserFromDatabase(
        userId: String,
        callback: (UserModel?, Boolean, String) -> Unit
    ){

    }
}