package com.example.stars.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.widget.TextView
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(requireContext())
        var view = LayoutInflater.from(requireContext()).inflate(R.layout.buy_list_layout,null)
        builder.setView(view)
        cancel = view.findViewById(R.id.Tv_Buydialog_cancel)
        cancel.setOnClickListener { dismiss() }
        ok = view.findViewById(R.id.Tv_Buydialog_ok)
        ok.setOnClickListener {

        }
        rv = view.findViewById(R.id.RV_buyList)
        rv.adapter = BuyListAdapter(list)
        rv.layoutManager= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL ,false)





        return builder.create()
    }
}