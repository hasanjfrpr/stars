package com.example.stars.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.stars.R
import com.example.stars.base.BaseFragment
import com.example.stars.base.formatNumber
import com.example.stars.db.local.AppDataBase
import com.example.stars.db.local.User
import com.example.stars.dialog.BuyDialog
import com.example.stars.models.setting.SettingModel.SettingModel
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : BaseFragment() {

    var isChecked:Boolean = true;
    var price:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate()
        signup()
    }

    private fun setDate() {
        mbtn_date.setOnClickListener {
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
                        mbtn_date.text =
                            persianCalendar.persianYear.toString() + "/" + persianCalendar.persianMonth + "/" + persianCalendar.persianDay
                    }

                    override fun onDismissed() {}
                })

            picker.show()
        }
    }
    private fun signup(){

        price = AppDataBase.getInstance(requireContext()).getDao().getPeroidPrice()

       checkbox_periodPrice.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
           override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
               if(p1){
                   isChecked = true
                   ET_periodPrice.visibility = View.INVISIBLE
               }else {
                   isChecked = false
                   ET_periodPrice.visibility = View.VISIBLE
               }
           }
       })

        mbtn_signUp_selectBuy.setOnClickListener {
        var list:MutableList<com.example.stars.models.setting.SettingModel.SettingModel> =
            AppDataBase.getInstance(requireContext()).getDao().getAllSettingModel() as MutableList<SettingModel>

            list.remove(SettingModel("شهریه" , AppDataBase.getInstance(requireContext()).getDao().getPeroidPrice()))

            var ss = BuyDialog(list)
            ss.show(requireActivity().supportFragmentManager , "")
            ss.isCancelable = false


        }


        mbtn_save_register.setOnClickListener {
            if(!isChecked){
                price = ET_periodPrice.text.toString().trim()
                if (TIET_family.text.isNullOrEmpty()) TIET_family.error = "نام وارد شود"
                if (TIET_family.text.isNullOrEmpty()) TIET_family.error = "نام خانوادگی وارد شود"
                AppDataBase.getInstance(requireContext()).getDao().insertUser(User(TIET_name.text.toString().trim(),
                    TIET_family.text.toString().trim() , TIET_phone_number.text.toString().trim() , mbtn_date.text.toString().trim(),
                    price
                ))
            }else{
                price = AppDataBase.getInstance(requireContext()).getDao().getPeroidPrice()
                if (TIET_family.text.isNullOrEmpty()) TIET_family.error = "نام وارد شود"
                if (TIET_family.text.isNullOrEmpty()) TIET_family.error = "نام خانوادگی وارد شود"
                AppDataBase.getInstance(requireContext()).getDao().insertUser(User(TIET_name.text.toString().trim(),
                    TIET_family.text.toString().trim() , TIET_phone_number.text.toString().trim() , mbtn_date.text.toString().trim(),
                    price
                ))
            }




        }
    }

}