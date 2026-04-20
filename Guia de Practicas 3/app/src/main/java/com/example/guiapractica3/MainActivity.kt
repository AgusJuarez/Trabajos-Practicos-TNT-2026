package com.example.guiapractica3

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Ciclo de Vida", "Dentro del onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Ciclo de Vida", "Dentro del onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Ciclo de Vida", "Dentro del onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Ciclo de Vida", "Dentro del onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Ciclo de Vida", "Dentro del onDestroy")
    }
}