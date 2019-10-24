package com.kavak.brastlewark.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kavak.brastlewark.ui.home.HomeFragment
import com.kavak.brastlewark.R

class HostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    HomeFragment.newInstance()
                )
                .commitNow()
        }
    }

}