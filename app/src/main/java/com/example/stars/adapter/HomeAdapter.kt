package com.example.stars.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.base.formatNumber
import com.example.stars.db.local.User
import kotlinx.android.synthetic.main.item_home_rv.view.*

class HomeAdapter(var list:MutableList<User>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv , parent,false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
       holder.itemView.item_home_name.text = list[position].name+" "+list[position].lastName
        holder.itemView.item_home_bed_bes.text  = formatNumber(list[position].bedbes!!.toDouble())
    }

    override fun getItemCount(): Int = list.size
}