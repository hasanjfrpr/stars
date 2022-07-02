package com.example.stars

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.example.stars.view.fragment.HomeFragment
import com.example.stars.view.fragment.SettingFragment
import com.example.stars.view.fragment.SignUpFragment
import kotlinx.android.synthetic.main.activity_main.*
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.frame_main, HomeFragment()).commit()
        setNavBtn()

    }


    private fun setNavBtn() {
        var menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_profile,
                R.drawable.avd_profile,
            ),
            CbnMenuItem(
                R.drawable.ic_home,
                R.drawable.avd_home,
            ),
            CbnMenuItem(
                R.drawable.ic_setting, // the icon
                R.drawable.avd_settings, // the AVD that will be shown in FAB
                // optional if you use Jetpack Navigation
            ),



            )

        nav_view.setMenuItems(menuItems, 1)
        nav_view.setOnMenuItemClickListener { cbnMenuItem, index ->
            when(index){
                0->{supportFragmentManager.beginTransaction().replace(R.id.frame_main , SignUpFragment()).commit()}
                1->{supportFragmentManager.beginTransaction().replace(R.id.frame_main , HomeFragment()).commit()}
                2->{supportFragmentManager.beginTransaction().replace(R.id.frame_main , SettingFragment()).commit()}

            }
        }
    }
}