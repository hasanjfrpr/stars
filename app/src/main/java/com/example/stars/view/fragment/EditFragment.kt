package com.example.stars.view.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.BoringLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_person_info.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.lang.Exception
import kotlin.math.log

class EditFragment(var user:User) : BaseFragment() , BuyDialog.onOkClicke {


    var totalbed : String = "0"
    var totalbedPlus : String = "0"
    var ha : Boolean? = null
    var totalBuy: String? = "0"
    var sumAllBedBes:String? = "0.0"
    var finalBed:String = ""
    var isCheck : Boolean = true;
    var periodPrice : Double = 0.0
    var periodPrice_manual : Double? = 0.0
    var result = "null"
    var isFirst = true;



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
        sumAllBedBes = totalbed
        TIET_name_edit.append(user.name)
        TIET_family_edit.append(user.lastName)
        TIET_phone_number_edit.append(user.phoneNumber)
        mbtn_date_edit.text = user.signUpDate
        mbtn_renew_edit.text = user.renew
        ET_note.text.append(user.note.toString())
        if(totalbed.toDouble() < 0){
         //   TV_tasvie.text = formatNumber(Math.abs(totalbed.toDouble()))+"  تومان طلبکار"
            TV_tasvie.text = formatNumber(Math.abs(totalbed.toDouble()))
            finalBed = totalbed
            TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))

        }else if(totalbed.toDouble() > 0){
           // TV_tasvie.text = formatNumber(Math.abs(totalbed.toDouble()))+" تومان بدهکار"
            TV_tasvie.text = formatNumber(Math.abs(totalbed.toDouble()))
            finalBed = totalbed
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
               if(p0.isNullOrEmpty() || p0.isNullOrEmpty()){
                   p0?.append("0")
               }

                try {
                    if (sumAllBedBes!!.toDouble() < 0 ){
                        totalbedPlus = (sumAllBedBes!!  + p0.toString().toDouble())

                    }else{
                        totalbedPlus = (sumAllBedBes!!.toDouble() - p0.toString().toDouble()).toString()

                    }

                    if (totalbedPlus.toDouble() < 0){
                        TV_tasvie.text = formatNumber(Math.abs(totalbedPlus.toDouble()))+" تومان طلبکار"
                      finalBed = totalbedPlus
                           TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))
                    }else{
                        TV_tasvie.text = formatNumber(Math.abs(totalbedPlus.toDouble()))+" تومان بدهکار"
                        finalBed = totalbedPlus
                            TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.red))
                    }

                }catch (e:Exception){}


            }

        })

        checkbox_periodPrice_edit.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

                if(p1){
                    ET_periodPrice_edit.visibility = View.INVISIBLE
                    isCheck = true
                    if(result != "null"){
                        sumAllBedBes = (sumAllBedBes!!.toDouble() - result.toDouble()).toString()
                        result = "null"
                    }
                    sumAllBedBes = (sumAllBedBes!!.toDouble()+periodPrice).toString()



                    if (sumAllBedBes!!.toDouble() < 0){
                        TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان طلبکار"
                        finalBed = sumAllBedBes!!.toString()
                        TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))
                    }else{
                        TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان بدهکار"
                        finalBed = sumAllBedBes!!.toString()
                        TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.red))
                    }
                }else{
                    ET_periodPrice_edit.visibility = View.VISIBLE
                    isCheck = false
                    sumAllBedBes = (sumAllBedBes!!.toDouble()-periodPrice).toString()
                    if (sumAllBedBes!!.toDouble() < 0){
                        TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان طلبکار"
                        finalBed = sumAllBedBes!!.toString()
                        TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))
                    }else{
                        TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان بدهکار"
                        finalBed = sumAllBedBes!!.toString()
                        TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.red))
                    }
//                        ET_periodPrice_edit.addTextChangedListener(object : TextWatcher{
//                            override fun beforeTextChanged(
//                                p0: CharSequence?,
//                                p1: Int,
//                                p2: Int,
//                                p3: Int
//                            ) {
//
//                            }
//
//                            override fun onTextChanged(
//                                p0: CharSequence?,
//                                p1: Int,
//                                p2: Int,
//                                p3: Int
//                            ) {
//
//                            }
//
//                            override fun afterTextChanged(p0: Editable?) {
//                                if(p0.isNullOrEmpty() || p0.isNullOrBlank()){
//                                    p0?.append("0")
//                                }
//                                periodPrice_manual = p0.toString().toDouble()
//                                sumAllBedBes = p0.toString().toDouble()+sumAllBedBes!!.toDouble()
//                                if (sumAllBedBes!! < 0){
//                                    TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان طلبکار"
//                                    finalBed = sumAllBedBes!!.toString()
//                                    TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))
//                                }else{
//                                    TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان بدهکار"
//                                    finalBed = sumAllBedBes!!.toString()
//                                    TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.red))
//                                }
//
//                          }
                //                       })
                }
            }

        })


        ET_periodPrice_edit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun afterTextChanged(p0: Editable?) {

                result = p0.toString()

                if(result.isNullOrEmpty() || result.isNullOrBlank()){
                    result = "0"
                }

if (result.length >=1){
                var remind = result.substring(0,result.length-1)
    if(remind == "") remind = "0"
    sumAllBedBes = (sumAllBedBes!!.toDouble() - remind.toDouble()).toString()
}


                sumAllBedBes = (sumAllBedBes!!.toDouble()+result.toDouble()).toString()
              //  sumAllBedBes = (sumAllBedBes!!.toDouble() + result.toDouble()).toString()
                Log.i("useruseruser", "onTextChanged: "+ result.toDouble())
                Log.i("useruseruser", "onTextChanged: "+ sumAllBedBes!!.toDouble())
                if (sumAllBedBes!!.toDouble() < 0){
                    TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان طلبکار"
                    finalBed = sumAllBedBes!!.toString()
                    TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))
                }else{
                    TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان بدهکار"
                    finalBed = sumAllBedBes!!.toString()
                    TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.red))
                }

            }

        })


        back_edit.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.Frame_personInfo , PersonInfoFragment(user)).commit()
        }

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
                        ssll.visibility = View.VISIBLE
                        periodPrice = AppDataBase.getInstance(requireContext()).getDao().getPeroidPrice().toDouble()

                        if(isCheck  && ssll.isVisible && isFirst){
                            sumAllBedBes =( periodPrice+sumAllBedBes!!.toDouble()).toString()
                            if (sumAllBedBes!!.toDouble() < 0){
                                TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان طلبکار"
                                finalBed = sumAllBedBes!!.toString()
                                TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))
                            }else{
                                TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان بدهکار"
                                finalBed = sumAllBedBes!!.toString()
                                TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.red))
                            }
                            isFirst = false
                        }
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




        mbtn_edit_selectBuy.setOnClickListener {
            var list: MutableList<com.example.stars.models.setting.SettingModel.SettingModel> =
                AppDataBase.getInstance(requireContext()).getDao()
                    .getAllSettingModel() as MutableList<SettingModel>

            list.remove(
                SettingModel(
                    "شهریه",
                    AppDataBase.getInstance(requireContext()).getDao().getPeroidPrice()
                )
            )

            var ss = BuyDialog(list)
            ss.show(requireActivity().supportFragmentManager, "")
            ss.onon = this
            ss.isCancelable = false


        }


        mbtn_save_edit.setOnClickListener {
           AppDataBase.getInstance(requireContext()).getDao().updateUser(TIET_name_edit.text.toString().trim(),TIET_family_edit.text.toString().trim(),
           TIET_phone_number_edit.text.toString().trim() , mbtn_date_edit.text.toString().trim(),user.periodPrice , user.buy,finalBed,
               mbtn_renew_edit.text.toString().trim() , (user.renewCount!!.toInt()+1).toString() ,ET_note.text.toString().trim() , user.id.toInt())
            var singleUser = AppDataBase.getInstance(requireContext()).getDao().getUser(user.id.toString())
            Log.i("useruser", "onCreate: "+ singleUser.id)
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.Frame_personInfo , PersonInfoFragment(singleUser)).commit()

        }



    }

    override fun onClick(list: MutableList<SettingModel> , count : MutableList<Int>) {
        var sum: Int = 0
        for (i in 0 until list.size) {
            sum += list[i].price!!.toInt()*count[i]
        }
        totalBuy = sum.toString()
        TV_edit_totalBuy.text = formatNumber(totalBuy!!.toDouble()) + " " + "تومان"


        if (sumAllBedBes!!.toDouble() < 0 ){
            sumAllBedBes = (sumAllBedBes!!.toDouble() + totalBuy.toString().toDouble()).toString()


        }else{
            sumAllBedBes = (sumAllBedBes!!.toDouble() + totalBuy.toString().toDouble()).toString()

        }

        if (sumAllBedBes!!.toDouble() < 0){
            TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان طلبکار"
            finalBed = sumAllBedBes!!.toString()
            TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.green))
        }else{
            TV_tasvie.text = formatNumber(Math.abs(sumAllBedBes!!.toDouble()))+" تومان بدهکار"
            finalBed = sumAllBedBes!!.toString()
            TV_tasvie.setTextColor(ContextCompat.getColor(requireContext() ,R.color.red))
        }

    }
}