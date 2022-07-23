package com.example.stars.view.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import saman.zamani.persiandate.PersianDate
import java.lang.Exception
import kotlin.math.roundToInt

class SignUpFragment() : BaseFragment(), BuyDialog.onOkClicke {



    var isChecked: Boolean = true;
    var price: String? = "0"
    var totalBedBes: String? = "0"
    var totalBuy: String? = "0"
    var note: String? = ""
     var list : MutableList<SettingModel> = ArrayList()

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

        PersianDate().apply {
            mbtn_date.text = "${this.shYear}/${this.shMonth}/${this.shDay}"
        }
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

    private fun signup() {
        ET_note_signUp.setOnClickListener {
            var dialog = AlertDialog.Builder(requireContext()).create()
            var view = layoutInflater.inflate(R.layout.dialog_note , null)
            var ok = view.findViewById<TextView>(R.id.TV_setting_dialog_ok_note)
            var cancel = view.findViewById<TextView>(R.id.Tv_setting_dialog_cancel_note)
            var editText = view.findViewById<EditText>(R.id.ET_note)

            cancel.setOnClickListener { dialog.dismiss() }
            ok.setOnClickListener {
                note = editText.text.toString().trim()
                dialog.dismiss()
                ET_note_signUp.setBackgroundColor(ContextCompat.getColor(requireContext() , R.color.green))
                ET_note_signUp.text="یادداشت ثبت شد"

            }
            dialog.setView(view)
            dialog.show()

        }

        price = AppDataBase.getInstance(requireContext()).getDao().getPeroidPrice()

        checkbox_periodPrice.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    isChecked = true
                    ET_periodPrice.visibility = View.INVISIBLE


                } else {
                    isChecked = false
                    ET_periodPrice.visibility = View.VISIBLE

                }
            }
        })
        AppDataBase.getInstance(requireContext()).getDao() .getAllSettingModel().observe(viewLifecycleOwner){
            list  = it as MutableList<SettingModel>
        }
        mbtn_signUp_selectBuy.setOnClickListener {
//            var list: MutableList<com.example.stars.models.setting.SettingModel.SettingModel> =
//                AppDataBase.getInstance(requireContext()).getDao()
//                    .getAllSettingModel() as MutableList<SettingModel>

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


        mbtn_save_register.setOnClickListener {
            if (!isChecked) {
                price = ET_periodPrice.text.toString().trim()
                if (TIET_name.text.isNullOrEmpty()) TIET_name.error = "نام وارد شود"
                if (TIET_family.text.isNullOrEmpty()) TIET_family.error = "نام خانوادگی وارد شود"
                if (ET_totalPayment.text.isNullOrEmpty()) ET_totalPayment.error = "مبلغ دریافتی وارد شود"
                else {
                    AppDataBase.getInstance(requireContext()).getDao().insertUser(
                        User(
                            TIET_name.text.toString().trim(),
                            TIET_family.text.toString().trim(),
                            TIET_phone_number.text.toString().trim(),
                            mbtn_date.text.toString().trim(),
                            price,
                            totalBuy,
                            totalBedBes.toString().trim(),
                            mbtn_date.text.toString().trim(),
                            "0",
                            note,

                        )
                    )
                    Toast.makeText(requireContext(), "کاربر ذخیره شد", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame_main , SignUpFragment()).commit()
                }
            } else {
                price = AppDataBase.getInstance(requireContext()).getDao().getPeroidPrice()
                if (TIET_name.text.isNullOrEmpty()) TIET_name.error = "نام وارد شود"
                if (TIET_family.text.isNullOrEmpty()) TIET_family.error = "نام خانوادگی وارد شود"
                if (ET_totalPayment.text.isNullOrEmpty()) ET_totalPayment.error = "مبلغ دریافتی وارد شود"
                else {
                    AppDataBase.getInstance(requireContext()).getDao().insertUser(
                        User(
                            TIET_name.text.toString().trim(),
                            TIET_family.text.toString().trim(),
                            TIET_phone_number.text.toString().trim(),
                            mbtn_date.text.toString().trim(),
                            price,
                            totalBuy,
                            totalBedBes.toString().trim(),
                            mbtn_date.text.toString().trim(),
                            "0",
                            note
                        )
                    )
                    Toast.makeText(requireContext(), "کاربر ذخیره شد", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame_main , SignUpFragment()).commit()
                }
            }




        }


        ET_totalPayment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (p0!!.isNotEmpty()) {

                        price = AppDataBase.getInstance(requireContext()).getDao().getPeroidPrice()
                        try {
                            totalBedBes = ((price!!.toDouble() + totalBuy!!
                                .toDouble()) - p0.toString().toDouble()).toString()

                        } catch (e: Exception) {
                        }


                }
                try {


                if (totalBedBes!!.toDouble().roundToInt() > 0) {
                    TV_totalBedBes.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green
                        )

                    )
                    TV_totalBedBes.text =
                        formatNumber(totalBedBes!!.toDouble()) + " " + "تومان"+" "+"بدهکار"
                } else {
                    TV_totalBedBes.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    TV_totalBedBes.text =
                        formatNumber(totalBedBes!!.toDouble()) + " " + "تومان"+" "+"طلبکار"
                }
                }catch (e:Exception){}

            }

            @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    override fun onClick(list: MutableList<SettingModel> , count:MutableList<Int>) {
        var sum: Int = 0
        for (i in 0 until list.size) {
            sum += list[i].price!!.toInt()*count[i]
        }
        totalBuy = sum.toString()
        TV_signUp_totalBuy.text = formatNumber(totalBuy!!.toDouble()) + " " + "تومان"


    }



}