package com.example.stars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            Toast.makeText(this, index.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}