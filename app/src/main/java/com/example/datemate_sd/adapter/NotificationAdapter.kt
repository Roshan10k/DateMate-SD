package com.example.datemate_sd.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.R
import com.example.datemate_sd.model.NotificationModel
import com.example.datemate_sd.ui.activity.ItsMatchActivity
import com.example.datemate_sd.ui.activity.LoginActivity
import com.example.datemate_sd.ui.activity.ProfilePageActivity
import com.example.datemate_sd.viewmodel.UserViewModel

class NotificationAdapter(
    private val context: Context,
    private var notifications: List<NotificationModel>, // Single list of NotificationModel
    val userViewModel: UserViewModel
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.notificationTitle)
        val description: TextView = view.findViewById(R.id.notificationDes)
        val rootView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notificationitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        holder.title.text = "DateMate"
        holder.description.text = notification.message


        holder.rootView.setOnClickListener{
            val intent= Intent(context, ProfilePageActivity::class.java)
            intent.putExtra("User",notifications[position].likerId)
            context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int = notifications.size

    // Method to update notifications
    fun updateNotifications(newNotifications: List<NotificationModel>) {
        notifications = newNotifications
        notifyDataSetChanged()
    }
}
