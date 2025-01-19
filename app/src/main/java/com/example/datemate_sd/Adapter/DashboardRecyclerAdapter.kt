package com.example.datemate_sd.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.datemate_sd.R
import androidx.recyclerview.widget.RecyclerView

class DashboardRecyclerAdapter(
    val context: Context,
    val imagelist: ArrayList<Int>,
    val nameList: ArrayList<String>,
    val ageList: ArrayList<String>,
    val profession: ArrayList<String>,

    ): RecyclerView.Adapter<DashboardRecyclerAdapter.DasboardViewHolder>() {
    class DasboardViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val age: TextView = itemView.findViewById(R.id.age)
        val profession: TextView = itemView.findViewById(R.id.profession)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DasboardViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.dashboarddesign,parent,false)
        return DasboardViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DasboardViewHolder,
        position: Int
    ) {
        holder.name.text = nameList[position]
        holder.age.text = ageList[position]
        holder.profession.text = profession[position]
        holder.image.setImageResource(imagelist[position])
    }

    override fun getItemCount(): Int {
        return imagelist.size
    }

}