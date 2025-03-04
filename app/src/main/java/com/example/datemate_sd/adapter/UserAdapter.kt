package com.example.datemate_sd.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.databinding.ItemUserBinding
import com.example.datemate_sd.model.UserModel
import com.squareup.picasso.Picasso

class UserAdapter(private var userList: List<UserModel>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            binding.searchNameDisplay.text = user.name
            binding.genderDisplay.text = user.gender

//             You can set image using Glide or Picasso here for the searchImage
//            (binding.searchImage.context).load(user.imageurl).into(binding.searchImage)
            Picasso.get().load(user.imageurl).into(binding.searchImageDisplay)
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

    fun updateList(newList: List<UserModel>) {
        userList = newList
        notifyDataSetChanged()
    }
}
