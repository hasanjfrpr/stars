package com.example.stars.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filter.FilterResults
import android.widget.Filterable
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.base.formatNumber
import com.example.stars.db.local.AppDataBase
import com.example.stars.db.local.User
import com.example.stars.view.activity.PersonInfoActivity
import kotlinx.android.synthetic.main.item_home_rv.view.*
import saman.zamani.persiandate.PersianDate
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter(var list:MutableList<User> , var context:Activity) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() , Filterable{

    var listItem : MutableList<User> = ArrayList<User>()
    init {
        listItem.addAll(list)
    }


    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv , parent,false)
        return HomeViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
       holder.itemView.item_home_name.text = list[position].name+" "+list[position].lastName

        holder.itemView.root_constraint_recycler_home.setOnLongClickListener {

            var alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage(R.string.deleteMessage)
            alertDialog.setPositiveButton(R.string.positiveButton,{p,s ->
                AppDataBase.getInstance(context).getDao().deleteUser(list[position])
                list.removeAt(position)
                notifyItemRemoved(position)
            })
            alertDialog.setNegativeButton(R.string.negetiveButton,{s,p->
                alertDialog.create().dismiss()
            })
            alertDialog.create().show()


           // notifyDataSetChanged()
            return@setOnLongClickListener true
        }


       // calculateReminderTime(list[position].renew.toString())

        var cals = calculateReminderTime(list[position].renew.toString()).toInt()
        holder.itemView.item_home_date_counter.text = cals.toString()
        if (list[position].bedbes!!.toDouble() < 0){holder.itemView.textView4.text = "طلبکاری"
            holder.itemView.item_home_bed_bes.text  = formatNumber(Math.abs(list[position].bedbes!!.toDouble()))
            holder.itemView.item_home_bed_bes.setTextColor(ContextCompat.getColor(context , R.color.green))
        }
        else if (list[position].bedbes!!.toDouble() > 0){holder.itemView.textView4.text = "بدهکاری"
            holder.itemView.item_home_bed_bes.text  = formatNumber(Math.abs(list[position].bedbes!!.toDouble()))
            holder.itemView.item_home_bed_bes.setTextColor(ContextCompat.getColor(context , R.color.red))
        }else{
            holder.itemView.item_home_bed_bes.text  = formatNumber(0.0)
            holder.itemView.item_home_bed_bes.setTextColor(ContextCompat.getColor(context , R.color.black))
        }
        if (cals>20 && cals <= 31) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.green))
        if (cals>10 && cals <= 20) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.primary))
        if (cals>3 && cals <= 10) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.orange))
        if (cals>0 && cals <= 3) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.red))
        if ( cals <= 0) holder.itemView.materialCard_item.setCardBackgroundColor(ContextCompat.getColor(context,R.color.black))


        holder.itemView.root_constraint_recycler_home.setOnClickListener {
            var intent  = Intent(context , PersonInfoActivity::class.java)
            intent.putExtra("userInfo" ,list[position])
            Log.i("useruser", "onCreate: "+ list[position].id)
            context.startActivity(intent)
            context.finish()

        }

    }

    override fun getItemCount(): Int = list.size


   public fun calculateReminderTime(date:String) :String{

       var pdate : PersianDate = PersianDate( )
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
       when(pdate.shMonth){

           7->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           8->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           9->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           10->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           11->{sumTodayMiliSecond = (pdate.shYear*12*30*24*60*60*1000)+(pdate.shMonth*30*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           12->{sumTodayMiliSecond = (pdate.shYear*12*29*24*60*60*1000)+(pdate.shMonth*29*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}
           else->{sumTodayMiliSecond = (pdate.shYear*12*31*24*60*60*1000)+(pdate.shMonth*31*24*60*60*1000)+(pdate.shDay*24*60*60*1000)}

       }
       var daySumToday = (((sumTodayMiliSecond/1000)/60)/60)/24
       var daySumSignUp = (((sumSignUpDateMiliSecond/1000)/60)/60)/24


       var result = (daySumToday - daySumSignUp)

       return  ((reminderDate.toInt()-Math.abs(result))-1).toString()
    }



    override fun getFilter(): Filter {
        return  filters
    }

    var filters : Filter  = object : Filter(){
        override fun performFiltering(p0: CharSequence?): FilterResults {
           var helpList : MutableList<User> = ArrayList<User>()

            if(p0 == null || p0.isEmpty() || p0.length==0){
                helpList.addAll(listItem)
            }else{
                for( item in list){
                    if(item.name!!.toString().lowercase().contains(p0.toString().lowercase().trim())  || item.lastName!!.toString().lowercase().contains(p0.toString().lowercase().trim())  ){
                        helpList.add(item)
                    }
                }
            }
            var filterResult = FilterResults()
            filterResult.values = helpList


            return  filterResult

        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            list.clear()
            list.addAll(p1!!.values as Collection<User>)
            notifyDataSetChanged()
        }
    }


}