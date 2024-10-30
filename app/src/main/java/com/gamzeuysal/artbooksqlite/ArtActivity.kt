package com.gamzeuysal.artbooksqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gamzeuysal.artbooksqlite.databinding.ActivityArtBinding

class ArtActivity : AppCompatActivity() {

    //View Binding
    private lateinit var  binding : ActivityArtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)
        
    }
    fun save (view : View)
    {

    }
    fun selectImage(view : View)
    {

    }
}