package com.walker.modulo3projetoicontatos.welcomescreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.walker.modulo3projetoicontatos.mainscreen.MainActivity
import com.walker.modulo3projetoicontatos.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (isAlreadyWelcomed()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        val fragmentList = arrayListOf(Welcome1Fragment(), Welcome2Fragment())

        val viewPagerAdapter = WelcomeAdapter(supportFragmentManager, lifecycle, fragmentList)

        binding.welcomeViewPager2.adapter = viewPagerAdapter

        TabLayoutMediator(binding.welcomeTabLayout, binding.welcomeViewPager2) { tab, position ->

        }.attach()

    }

    private fun isAlreadyWelcomed(): Boolean {
        val sharedPreference = getSharedPreferences(welcome_file_key, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(already_welcomeded_key, false)
    }
}