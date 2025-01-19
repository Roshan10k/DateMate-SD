package com.example.datemate_sd.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.databinding.NotificationitemBinding

class NotificationAdapter(
    val context: Context,
    val titleList: ArrayList<String>,
    val desList: ArrayList<String>
): RecyclerView.Adapter<NotificationAdapter.NotificationViewholder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewholder {
        val binding = NotificationitemBinding.inflate(LayoutInflater.from(context),parent,false)
        return NotificationViewholder(binding)
    }

    override fun onBindViewHolder(
        holder: NotificationViewholder,
        position: Int
    ) {
        holder.binding.notificationTitle.text = titleList[position]
        holder.binding.NotificationDes.text = desList[position]

    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    class NotificationViewholder(val binding: NotificationitemBinding): RecyclerView.ViewHolder(binding.root) {

    }

}