package com.example.stars.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.stars.R
import com.example.stars.base.BaseActivity
import com.example.stars.db.local.User
import com.example.stars.view.fragment.PersonInfoFragment
import kotlinx.android.synthetic.main.activity_person_info.*
import kotlin.math.log

class PersonInfoActivity : BaseActivity() {

lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_info)
        user = intent.extras?.get("userInfo") as User

        supportFragmentManager.beginTransaction().replace(R.id.Frame_personInfo , PersonInfoFragment(user)).commit()

    }


    override fun onBackPressed() {
        if(supportFragmentManager.findFragmentByTag("editFragment") != null){
          supportFragmentManager.beginTransaction().replace(R.id.Frame_personInfo , PersonInfoFragment(user)).commit()

        }else{
            super.onBackPressed()
        }

    }
}
