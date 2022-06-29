package com.example.stars.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.adapter.BuyListAdapter
import com.example.stars.models.setting.SettingModel.SettingModel
import kotlinx.android.synthetic.main.buy_list_layout.*

class BuyDialog(var list: MutableList<SettingModel>) : DialogFragment() {

lateinit var rv:RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(requireContext())
        var view = LayoutInflater.from(requireContext()).inflate(R.layout.buy_list_layout,null)
        builder.setView(view)
        rv = view.findViewById(R.id.RV_buyList)
        rv.adapter = BuyListAdapter(list)
        rv.layoutManager= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL ,false)

        Tv_Buydialog_cancel.setOnClickListener { dismiss() }

        Tv_Buydialog_ok.setOnClickListener {

        }





        return builder.create()
    }
}