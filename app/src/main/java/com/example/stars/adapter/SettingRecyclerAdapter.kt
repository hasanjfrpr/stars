package com.example.stars.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.base.formatNumber
import com.example.stars.db.local.AppDataBase
import com.example.stars.models.setting.SettingModel.SettingModel
import kotlinx.android.synthetic.main.item_setting.view.*

class SettingRecyclerAdapter(val settingList : MutableList<SettingModel>, private val context:Context) : RecyclerView.Adapter<SettingRecyclerAdapter.SettingAdapterViewHolder>() {


lateinit var eventClickItem: EventClickItem

    class SettingAdapterViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setting,parent,false)
        return SettingAdapterViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SettingAdapterViewHolder, position: Int) {
        holder.itemView.TV_item_setting_title.text = settingList[position].title
        holder.itemView.TV_item_setting_price.text = formatNumber(settingList[position].price!!.toDouble())+" تومان "

        holder.itemView.setOnLongClickListener {
            AppDataBase.getInstance(context).getDao().deleteSettingPrice(settingList[position].title.toString())
           settingList.removeAt(position)
            notifyItemRemoved(position)
            return@setOnLongClickListener true
        }
        holder.itemView.setOnClickListener {
            eventClickItem.onclickItem(settingList[position].title!! , settingList[position].price!!)
        }
    }


    override fun getItemCount(): Int = settingList.size

interface  EventClickItem{
    fun onclickItem(title:String , price:String)
}
}