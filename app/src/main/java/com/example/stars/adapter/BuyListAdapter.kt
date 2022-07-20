package com.example.stars.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.models.setting.SettingModel.SettingModel
import kotlinx.android.synthetic.main.item_buy.view.*

class BuyListAdapter(var list:MutableList<SettingModel>) : RecyclerView.Adapter<BuyListAdapter.BuyListViewHolder>() {

    var listOfBuy : MutableList<SettingModel > = ArrayList()
    var count : MutableList<Int> = ArrayList()




    class BuyListViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyListViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_buy,parent,false)

        return  BuyListViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BuyListViewHolder, @SuppressLint("RecyclerView") position: Int) {


        for (i  in 0 until list.size){
            count.add(1)
        }



        holder.itemView.checkbox_item_buy.text=list[position].title
        holder.itemView.IV_add.setOnClickListener {
            count[position] += 1
            holder.itemView.TV_count_buy.text = "${count[position]}"
        }


        holder.itemView.IV_minus.setOnClickListener {
          count[position] -= 1
            if(count[position]<1){
                count[position] = 1
            }
            holder.itemView.TV_count_buy.text = "${count[position]}"
        }

        holder.itemView.checkbox_item_buy.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    holder.itemView.item_linear.visibility = View.VISIBLE
                        listOfBuy.add(list[position])
                }else{
                    holder.itemView.item_linear.visibility = View.INVISIBLE
                    listOfBuy.remove(list[position])
                }
            }

        })

    }

    override fun getItemCount(): Int  = list.size

    fun getListBuy():MutableList<SettingModel > = listOfBuy
    fun getListBuys():MutableList<Int > = count
}