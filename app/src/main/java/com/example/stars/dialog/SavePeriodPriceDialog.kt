package com.example.stars.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.stars.R
import com.google.android.material.textfield.TextInputEditText

class SavePeriodPriceDialog : DialogFragment(){

    lateinit var ok: TextView
    lateinit var cancel: TextView
    public lateinit var oc: onClickOkPric
    lateinit var price: TextInputEditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.save_period_price_dialog,null)
        builder.setView(view)
        ok = view.findViewById(R.id.TV_setting_dialog_ok_period)
        cancel = view.findViewById(R.id.Tv_setting_dialog_cancel_period)
        price = view.findViewById(R.id.ET_setting_dialog_price_period)


        cancel.setOnClickListener {
            dismiss()
        }
        ok.setOnClickListener {
            if(price.text.isNullOrEmpty()) price.error="قیمت وارد شود"
            else{
            oc.onClick(price.text.toString().trim())
                dismiss()
            }
        }

        return builder.create()

    }

    public interface onClickOkPric{
        fun onClick(price:String?)
    }

}