package com.example.stars.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.stars.MainActivity
import com.example.stars.R
import com.example.stars.base.BaseFragment
import com.example.stars.base.formatNumber
import com.example.stars.db.local.User
import kotlinx.android.synthetic.main.fragment_person_info.*
import kotlinx.android.synthetic.main.item_home_rv.view.*

class PersonInfoFragment(var user:User) : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_info, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /////initialize text
        TV_bedBes_userInfo.text = formatNumber(user.bedbes!!.toDouble());
        if (user.bedbes!!.toDouble() < 0){textView6.text = "طلبکاری"
            TV_bedBes_userInfo.text  = formatNumber(Math.abs(user.bedbes!!.toDouble()));
            TV_bedBes_userInfo.setTextColor(ContextCompat.getColor(requireContext() , R.color.green))
        }
        else if (user.bedbes!!.toDouble() > 0){textView6.text = "بدهکاری"
            TV_bedBes_userInfo.text  = formatNumber(user.bedbes!!.toDouble());
            TV_bedBes_userInfo.setTextColor(ContextCompat.getColor(requireContext() , R.color.red))
        }else{
            TV_bedBes_userInfo.text  = formatNumber(0.0)
            TV_bedBes_userInfo.setTextColor(ContextCompat.getColor(requireContext() , R.color.black))
        }
        TV_phoneNumber_userInfo.text = user.phoneNumber
        TV_startDate_userInfo.text = user.signUpDate
        TV_name_userInfo.text = user.name+" "+user.lastName
        if(user.note.isNullOrEmpty()){
            showEmptyNote.visibility = View.VISIBLE
            Tv_note_edit.visibility = View.GONE
        }else{
            showEmptyNote.visibility = View.GONE
            Tv_note_edit.visibility = View.VISIBLE
            Tv_note_edit.text = user.note
        }

        TV_renew_userInfo.text = user.renew
        TV_last_renew_count_userInfo.text = user.renewCount



        ///event

        back_personInfo.setOnClickListener {
            var intent = Intent(requireActivity() , MainActivity::class.java)
           startActivity(intent)
            requireActivity().finish()

        }

        TV_phoneNumber_userInfo.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${user.phoneNumber}")
            startActivity(intent)
        }

        btn_edit_userInfo.setOnClickListener{
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.Frame_personInfo , EditFragment(user),"editFragment")
                .commit()
        }
    }




}