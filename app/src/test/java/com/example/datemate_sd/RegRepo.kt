package com.example.datemate_sd

interface RegRepo {
    fun forgetPassword(email: String,callback: (Boolean, String) -> Unit)
}