package com.example.datemate_sd.repository

import com.example.datemate_sd.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

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
                    callback(true,"success",userId)
                } else {
                    callback(false, "Registration failed", "")
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
}