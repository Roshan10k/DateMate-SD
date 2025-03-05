package com.example.datemate_sd.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.databinding.ItemUserBinding
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.ui.activity.ProfilePageActivity
import com.squareup.picasso.Picasso

class UserAdapter(private var userList: List<UserModel>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            binding.searchNameDisplay.text = user.name
            binding.genderDisplay.text = user.gender

            // Load image using Picasso
            Picasso.get().load(user.imageurl).into(binding.searchImageDisplay)

            // Handle item click, navigate to ProfilePageActivity with the user ID
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ProfilePageActivity::class.java)
                intent.putExtra("User", user.UserId)  // Pass the user ID
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    // Update the list of users in the adapter
    fun updateList(newList: List<UserModel>) {
        userList = newList
        notifyDataSetChanged()
    }
}
