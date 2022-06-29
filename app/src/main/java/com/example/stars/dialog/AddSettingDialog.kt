package com.example.stars.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.stars.R
import com.example.stars.base.formatNumber
import com.google.android.material.textfield.TextInputEditText

class AddSettingDialog : DialogFragment() {

lateinit var ok:TextView
lateinit var cancel:TextView
 public lateinit var settingInterFace:SettingDialogInterFace
 lateinit var title:TextInputEditText
 lateinit var price:TextInputEditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder:AlertDialog.Builder = AlertDialog.Builder(context)
        val view:View = LayoutInflater.from(context).inflate(R.layout.setting_dialog,null)
        builder.setView(view)
        ok = view.findViewById(R.id.TV_setting_dialog_ok)
        cancel = view.findViewById(R.id.Tv_setting_dialog_cancel)
        title = view.findViewById(R.id.ET_setting_dialog_title)
        price = view.findViewById(R.id.ET_setting_dialog_price)



        cancel.setOnClickListener { dismiss() }
        ok.setOnClickListener {
            if (price.text.isNullOrEmpty()){
                price.error="قیمت وارد شود"
            }else if(title.text.isNullOrEmpty()){
                title.error = "عنوان وارد شود"
            } else {
                settingInterFace.onOkClick(
                    title.text.toString().trim(),
                    price.text.toString().trim()
                )
                dismiss()

            }
        }


        return builder.create()
    }

    public interface SettingDialogInterFace{
        fun onOkClick(title:String? , price:String?)
    }
}