package com.example.datemate_sd.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.datemate_sd.R
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.ui.activity.ProfilePageActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(
    val context: Context,
    var data : ArrayList<UserModel>

    ): RecyclerView.Adapter<DashboardRecyclerAdapter.DasboardViewHolder>() {
    class DasboardViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.dashboardImage)
        val name: TextView = itemView.findViewById(R.id.name)
        val gender: TextView = itemView.findViewById(R.id.gender)
        val progessBar: ProgressBar = itemView.findViewById(R.id.dashboardProgressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DasboardViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.dashboarddesign,parent,false)
        return DasboardViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DasboardViewHolder,
        position: Int
    ) {
        holder.name.text = data[position].name
        holder.gender.text = data[position].gender

        Picasso.get().load(data[position].imageurl).into(holder.image,object:Callback{
            override fun onSuccess() {
                holder.progessBar.visibility = View.GONE
            }
            override fun onError(e: Exception?) {
            }

        })


        holder.image.setOnClickListener{
            val intent= Intent(context, ProfilePageActivity::class.java)
            intent.putExtra("User",data[position].UserId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(Users: List<UserModel>){
        data.clear()
        data.addAll(Users)
        notifyDataSetChanged()
    }

}