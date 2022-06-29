package com.example.stars.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.models.setting.SettingModel.SettingModel
import kotlinx.android.synthetic.main.item_buy.view.*

class BuyListAdapter(var list:MutableList<SettingModel>) : RecyclerView.Adapter<BuyListAdapter.BuyListViewHolder>() {

    class BuyListViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyListViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_buy,parent,false)
        return  BuyListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuyListViewHolder, position: Int) {
        holder.itemView.checkbox_item_buy.text=list[position].title

    }

    override fun getItemCount(): Int  = list.size
}