package com.example.aboutme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.doneButton.setOnClickListener {
            binding.nicknameTv.text = binding.nicknameEt.text
            binding.doneButton.visibility = View.GONE
            binding.nicknameEt.visibility = View.GONE
            binding.nicknameTv.visibility = View.VISIBLE
        }
    }
}