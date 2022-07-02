package com.example.stars.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.base.formatNumber
import com.example.stars.db.local.User
import kotlinx.android.synthetic.main.item_home_rv.view.*
import java.util.*
import java.util.logging.Handler
import kotlin.math.log

class HomeAdapter(var list:MutableList<User> , var context:Context) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv , parent,false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
       holder.itemView.item_home_name.text = list[position].name+" "+list[position].lastName
        holder.itemView.item_home_bed_bes.text  = formatNumber(list[position].bedbes!!.toDouble())
        calculateReminderTime(list[position].signUpDate.toString())
        holder.itemView.item_home_date_counter.text = calculateReminderTime(list[position].signUpDate.toString())
        var cals = calculateReminderTime(list[position].signUpDate.toString()).toInt()
        if (cals>20 && cals <= 31) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.green))
        if (cals>10 && cals <= 20) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.primary))
        if (cals>3 && cals <= 10) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.orange))
        if (cals>0 && cals <= 3) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.red))
        if ( cals <= 0) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.black))

    }

    override fun getItemCount(): Int = list.size


   private fun calculateReminderTime(date:String) :String{
        var reminderDate:String = "";
       var arr = date.split("/");
       var year = arr[0]
       var month = arr[1]
       var day = arr[2]
       when(month.toInt()){

           7->{reminderDate = "30"}
           8->{reminderDate = "30"}
           9->{reminderDate = "30"}
           10->{reminderDate = "30"}
           11->{reminderDate = "30"}
           12->{reminderDate = "29"}
           else->{reminderDate = "31"}

       }
       var timer = Timer()
       var handler = android.os.Handler()
       timer.schedule(object : TimerTask(){
           override fun run() {
               handler.post(object  : Runnable{
                   override fun run() {
                      reminderDate =((reminderDate.toInt())-1).toString()
                   }

               })
           }

       } ,0 , 2000 )

       return  reminderDate
    }
}