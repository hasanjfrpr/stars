package com.example.stars.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stars.R
import com.example.stars.base.BaseFragment
import com.example.stars.db.local.User
import kotlinx.android.synthetic.main.fragment_person_info.*

class PersonInfoFragment(var user:User) : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /////initialize text
        TV_bedBes_userInfo.text = user.bedbes;
        TV_phoneNumber_userInfo.text = user.phoneNumber
        TV_startDate_userInfo.text = user.signUpDate
        TV_name_userInfo.text = user.name+" "+user.lastName
        Tv_note_edit.text = user.note


        ///event
        TV_phoneNumber_userInfo.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${user.phoneNumber}")
            startActivity(intent)
        }

        btn_edit_userInfo.setOnClickListener{
            requireActivity().supportFragmentManager
                .beginTransaction()
                .hide(this)
                .addToBackStack("editFragment")
                .add(R.id.Frame_personInfo , EditFragment(user),"editFragment")
                .commit()
        }
    }




}