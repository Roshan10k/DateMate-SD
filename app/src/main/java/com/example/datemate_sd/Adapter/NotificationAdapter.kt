package com.example.datemate_sd.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.R
import com.example.datemate_sd.model.NotificationModel

class NotificationAdapter(
    private val context: Context,
    private var titleList: List<String>,
    private var desList: List<String>
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.notificationTitle)
        val description: TextView = view.findViewById(R.id.notificationDes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notificationitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = titleList[position]
        holder.description.text = desList[position]
    }

    override fun getItemCount(): Int = titleList.size

    fun updateNotifications(notifications: List<NotificationModel>) {
        titleList = notifications.map { it.title }
        desList = notifications.map { it.description }
        notifyDataSetChanged()
    }
}
