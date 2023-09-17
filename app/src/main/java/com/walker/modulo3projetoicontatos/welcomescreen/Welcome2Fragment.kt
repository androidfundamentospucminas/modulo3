package com.walker.modulo3projetoicontatos.welcomescreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.walker.modulo3projetoicontatos.mainscreen.MainActivity
import com.walker.modulo3projetoicontatos.databinding.FragmentWelcome2Binding

const val welcome_file_key = "welcome_file_key"
const val already_welcomeded_key = "already_welcomeded_key"

class Welcome2Fragment : Fragment() {

    private lateinit var binding: FragmentWelcome2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcome2Binding.inflate(inflater, container, false)

        binding.initButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            saveWelcomed()
            activity?.finish()
        }

        return binding.root
    }

    private fun saveWelcomed() {
        val editor = activity?.getSharedPreferences(welcome_file_key, Context.MODE_PRIVATE)?.edit()
        editor?.putBoolean(already_welcomeded_key, true)
        editor?.apply()
    }

}