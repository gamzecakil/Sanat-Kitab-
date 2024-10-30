package com.gamzeuysal.artbooksqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gamzeuysal.artbooksqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //View Binding
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}