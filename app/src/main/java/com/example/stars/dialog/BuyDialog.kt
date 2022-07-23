package com.example.stars.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.adapter.BuyListAdapter
import com.example.stars.models.setting.SettingModel.SettingModel
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.buy_list_layout.*
import org.w3c.dom.Text

class BuyDialog(var list: MutableList<SettingModel>) : DialogFragment() {

lateinit var rv:RecyclerView
lateinit var cancel : TextView
lateinit var ok : TextView
lateinit var adapter:BuyListAdapter
lateinit var onon:onOkClicke

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(requireContext())
        var view = LayoutInflater.from(requireContext()).inflate(R.layout.buy_list_layout,null)

        cancel = view.findViewById(R.id.Tv_Buydialog_cancel)
        cancel.setOnClickListener { dismiss() }
        ok = view.findViewById(R.id.Tv_Buydialog_ok)
        var empty = view.findViewById<LinearLayout>(R.id.empty_text_buy)
        rv = view.findViewById(R.id.RV_buyList)

        if (list.isEmpty() || list.size<2){
            rv.visibility = View.GONE
            empty.visibility = View.VISIBLE
        }else{
            rv.visibility = View.VISIBLE
            empty.visibility = View.GONE
        }

        for(i in 0 until list.size) {
            if (list[i].title == "شهریه"){
                list.removeAt(i)
                break
            }
        }

        adapter = BuyListAdapter(list)
        rv.adapter = adapter
        rv.layoutManager= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL ,false)
        builder.setView(view)
        ok.setOnClickListener {
            onon.onClick(adapter.getListBuy() , adapter.getListBuys())

            dismiss()
        }



        return builder.create()
    }

    interface  onOkClicke{
        fun onClick(list:MutableList<SettingModel> , counts:MutableList<Int>)

    }
}