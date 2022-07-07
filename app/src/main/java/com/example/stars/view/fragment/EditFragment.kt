package com.example.stars.view.fragment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stars.R
import com.example.stars.base.BaseFragment
import com.example.stars.db.local.User
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_person_info.*

class EditFragment(var user:User) : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TIET_name_edit.append(user.name)
        TIET_family_edit.append(user.lastName)
        TIET_phone_number_edit.append(user.phoneNumber)
        mbtn_date_edit.text = user.signUpDate
        ET_note.text.append(user.note.toString())
    }
}