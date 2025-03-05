package com.example.datemate_sd.api

import com.example.datemate_sd.model.Notification

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import com.example.datemate_sd.AccessToken

interface NotificationInterface {

    @POST("/v1/projects/datemate-6978d/messages:send")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )

    fun sendNotification(
        @Body message: Notification,
        @Header("Authorization") accessToken: String = "Bearer ${AccessToken.getAccessToken()}"
    ):retrofit2.Call<Notification>
}