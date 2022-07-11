package com.example.stars.view.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.stars.R
import com.example.stars.base.BaseFragment
import com.example.stars.base.formatNumber
import com.example.stars.db.local.AppDataBase
import com.example.stars.db.local.User
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_person_info.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.lang.Exception

class EditFragment(var user:User) : BaseFragment() {


    var totalbed : String = "0"
    var totalbedPlus : String = "0"
    var ha : Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        totalbed = user.bedbes!!
        TIET_name_edit.append(user.name)
        TIET_family_edit.append(user.lastName)
        TIET_phone_number_edit.append(user.phoneNumber)
        mbtn_date_edit.text = user.signUpDate
        mbtn_renew_edit.text = user.renew
        ET_note.text.append(user.note.toString())
        if(totalbed.toDouble() < 0){
            TV_tasvie.text = formatNumber(Math.abs(totalbed.toDouble()))+"  تومان طلبکار"
            TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))

        }else if(totalbed.toDouble() > 0){
            TV_tasvie.text = formatNumber(Math.abs(totalbed.toDouble()))+" تومان بدهکار"
            TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.red))
        }else{
            TV_tasvie.text ="تسویه شده"
        }

        ET_totalPayment_edite.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(!p0!!.isNullOrEmpty()){

                try {
                    if (totalbed.toDouble() < 0 ){
                        totalbedPlus = (totalbed.toDouble() + p0.toString().toDouble()).toString()

                    }else{
                        totalbedPlus = (totalbed.toDouble() - p0.toString().toDouble()).toString()

                    }

                    if (totalbedPlus.toDouble() < 0){
                        TV_tasvie.text = formatNumber(Math.abs(totalbedPlus.toDouble()))+" تومان طلبکار"
                           TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))
                    }else{
                        TV_tasvie.text = formatNumber(Math.abs(totalbedPlus.toDouble()))+" تومان بدهکار"
                            TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.red))
                    }

                }catch (e:Exception){}

                }
            }

        })
        mbtn_renew_edit.setOnClickListener {
            var picker = PersianDatePickerDialog(requireContext())
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setListener(object : Listener {
                    override fun onDateSelected(persianCalendar: PersianCalendar) {
                        mbtn_renew_edit.text =
                            persianCalendar.persianYear.toString() + "/" + persianCalendar.persianMonth + "/" + persianCalendar.persianDay
                    }

                    override fun onDismissed() {}
                })

            picker.show()
        }
        mbtn_date_edit.setOnClickListener {
            var picker = PersianDatePickerDialog(requireContext())
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setListener(object : Listener {
                    override fun onDateSelected(persianCalendar: PersianCalendar) {
                        mbtn_date_edit.text =
                            persianCalendar.persianYear.toString() + "/" + persianCalendar.persianMonth + "/" + persianCalendar.persianDay
                    }

                    override fun onDismissed() {}
                })

            picker.show()
        }


        mbtn_save_edit.setOnClickListener {
//            AppDataBase.getInstance(requireContext()).getDao().insertUser(User( TIET_name_edit.text.toString().trim() ,  TIET_family_edit.text.toString().trim(),
//            TIET_phone_number_edit.text.toString().trim() , mbtn_date_edit.text.toString().trim() ,user.periodPrice,user.buy , ))
        }



    }
}