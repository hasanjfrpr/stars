package com.example.stars.view.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
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
import java.lang.Exception
import kotlin.math.roundToInt

class SignUpFragment : BaseFragment(), BuyDialog.onOkClicke {

    var isChecked: Boolean = true;
    var price: String? = "0"
    var totalBedBes: String? = "0"
    var totalBuy: String? = "0"

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

    private fun signup() {

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

        mbtn_signUp_selectBuy.setOnClickListener {
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


        mbtn_save_register.setOnClickListener {
            if (!isChecked) {
                price = ET_periodPrice.text.toString().trim()
                if (TIET_name.text.isNullOrEmpty()) TIET_name.error = "نام وارد شود"
                if (TIET_family.text.isNullOrEmpty()) TIET_family.error = "نام خانوادگی وارد شود"
                if(!TIET_phone_number.text!!.matches(Regex("(0|\\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}"))) TIET_phone_number.error="شماره تلفن به درستی وارد نشده است"
                else {
                    AppDataBase.getInstance(requireContext()).getDao().insertUser(
                        User(
                            TIET_name.text.toString().trim(),
                            TIET_family.text.toString().trim(),
                            TIET_phone_number.text.toString().trim(),
                            mbtn_date.text.toString().trim(),
                            price,
                            totalBuy,
                            totalBedBes.toString().trim()
                        )
                    )
                }
            } else {
                price = AppDataBase.getInstance(requireContext()).getDao().getPeroidPrice()
                if (TIET_name.text.isNullOrEmpty()) TIET_name.error = "نام وارد شود"
                if (TIET_family.text.isNullOrEmpty()) TIET_family.error = "نام خانوادگی وارد شود"
                if(!TIET_phone_number.text!!.matches(Regex("(0|\\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}"))) TIET_phone_number.error="شماره تلفن به درستی وارد نشده است"

                else {
                    AppDataBase.getInstance(requireContext()).getDao().insertUser(
                        User(
                            TIET_name.text.toString().trim(),
                            TIET_family.text.toString().trim(),
                            TIET_phone_number.text.toString().trim(),
                            mbtn_date.text.toString().trim(),
                            price,
                            totalBuy,
                            totalBedBes.toString().trim()
                        )
                    )
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

    override fun onClick(list: MutableList<SettingModel>) {
        var sum: Int = 0
        for (i in 0 until list.size) {
            sum += list[i].price!!.toInt()
        }
        totalBuy = sum.toString()
        TV_signUp_totalBuy.text = formatNumber(totalBuy!!.toDouble()) + " " + "تومان"


    }

}