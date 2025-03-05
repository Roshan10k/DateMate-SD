package com.example.datemate_sd

import com.google.firebase.auth.FirebaseAuth

class RegRepoImpl(var auth: FirebaseAuth) : RegRepo {
    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Password reset link sent to $email")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }    }

}