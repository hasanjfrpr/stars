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
import saman.zamani.persiandate.PersianDate
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

        var cals = calculateReminderTime(list[position].signUpDate.toString()).toInt()
        holder.itemView.item_home_date_counter.text = cals.toString()
        if (cals>20 && cals <= 31) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.green))
        if (cals>10 && cals <= 20) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.primary))
        if (cals>3 && cals <= 10) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.orange))
        if (cals>0 && cals <= 3) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.red))
        if ( cals <= 0) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.black))

    }

    override fun getItemCount(): Int = list.size


   private fun calculateReminderTime(date:String) :String{

       var pdate : PersianDate = PersianDate( )
       Log.i(" persianDate", "calculateReminderTime: " + pdate.shDay+" " + pdate.shMonth + "  "+ pdate.shYear)
       var sumTodayMiliSecond = 0
       var sumSignUpDateMiliSecond = 0

        var reminderDate:String = "";
       var arr = date.split("/");
       var year = arr[0]
       var month = arr[1]
       var day = arr[2]
       when(month.toInt()){

           7->{reminderDate = "30"
               sumSignUpDateMiliSecond = (year.toInt()*12*30*24*60*60*1000)+(month.toInt()*30*24*60*60*1000)+(day.toInt()*24*60*60*1000)
           }
           8->{reminderDate = "30"
               sumSignUpDateMiliSecond = (year.toInt()*12*30*24*60*60*1000)+(month.toInt()*30*24*60*60*1000)+(day.toInt()*24*60*60*1000)
           }
           9->{reminderDate = "30"
               sumSignUpDateMiliSecond = (year.toInt()*12*30*24*60*60*1000)+(month.toInt()*30*24*60*60*1000)+(day.toInt()*24*60*60*1000)
           }
           10->{reminderDate = "30"
               sumSignUpDateMiliSecond = (year.toInt()*12*30*24*60*60*1000)+(month.toInt()*30*24*60*60*1000)+(day.toInt()*24*60*60*1000)
           }
           11->{reminderDate = "30"
               sumSignUpDateMiliSecond = (year.toInt()*12*30*24*60*60*1000)+(month.toInt()*30*24*60*60*1000)+(day.toInt()*24*60*60*1000)
           }
           12->{reminderDate = "29"
               sumSignUpDateMiliSecond = (year.toInt()*12*29*24*60*60*1000)+(month.toInt()*29*24*60*60*1000)+(day.toInt()*24*60*60*1000)
           }
           else->{reminderDate = "31"
               sumSignUpDateMiliSecond = (year.toInt()*12*31*24*60*60*1000)+(month.toInt()*31*24*60*60*1000)+(day.toInt()*24*60*60*1000)
           }

       }
       when(pdate.shMonth.toInt()){

           7->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           8->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           9->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           10->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           11->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           12->{sumTodayMiliSecond = (pdate.shYear*12*29*24*60*60*1000)+(pdate.shMonth*29*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           else->{sumTodayMiliSecond = (pdate.shYear*12*31*24*60*60*1000)+(pdate.shMonth*31*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}

       }

       var result = ((((sumTodayMiliSecond - sumSignUpDateMiliSecond)/1000)/60)/60)/24

       return  (reminderDate.toInt()-result).toString()
    }
}