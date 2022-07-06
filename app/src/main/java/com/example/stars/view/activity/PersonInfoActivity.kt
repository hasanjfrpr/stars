package com.example.stars.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stars.R
import com.example.stars.db.local.User
import kotlinx.android.synthetic.main.activity_person_info.*

class PersonInfoActivity : AppCompatActivity() {

    var user:User = intent.extras!!.get("userInfo") as User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_info)


    }
}