package com.yeonkyu.watchaassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yeonkyu.watchaassignment.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation)

        NavigationUI.setupWithNavController(
            bottomNavigationView,
            findNavController(R.id.main_nav_host)
        )//바텀네비게이션 클릭시마다 새로 만들지 않도록 수정해야함

    }
}