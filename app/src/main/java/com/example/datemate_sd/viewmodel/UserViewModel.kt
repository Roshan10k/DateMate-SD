package com.example.datemate_sd.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User

class UserViewModel(private val repo: UserRepository) {

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        repo.login(email, password, callback)
    }

    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.register(email, password, callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        repo.forgetPassword(email, callback)
    }

    fun addUserToDatabase(userID: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        repo.addUserToDatabase(userID, userModel, callback)
    }

    fun logout(callback: (Boolean, String) -> Unit) {
        repo.logout(callback)
    }

    fun editProfile(userId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        repo.editProfile(userId, data, callback)
    }

    fun getCurrentUser(): FirebaseUser? {
        return repo.getCurrentUSer()
    }

    var _users = MutableLiveData<UserModel>()
    var users  = MutableLiveData<UserModel>()
        get() = _users

    fun getUserFromDatabase(userId: String) {
        repo.getUserFromDatabase(userId){
            users,success,message->
            if (success){
                _users.value = users
            }
        }
    }


    var _getAllUsers = MutableLiveData<List<UserModel>>()
    var getAllUsers = MutableLiveData<List<UserModel>>()
        get() = _getAllUsers

    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading

    fun getAllUserFunc(){
        _loading.value = true
        repo.getAllUsers{
                allProducts,success,message->
            if (success){
                _getAllUsers.value = allProducts
                _loading.value = false
            }
        }
    }


    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit){
        repo.uploadImage(context, imageUri, callback)
    }

}
