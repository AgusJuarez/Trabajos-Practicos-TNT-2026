package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import androidx.core.net.toUri


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        binding.btnShare.setOnClickListener {
            val textToShare = binding.etContent.text.toString()
            if (textToShare.trim().isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un texto", Toast.LENGTH_SHORT).show()
            } else {
                shareText(textToShare)
            }

        }

        binding.btnNavegador.setOnClickListener {
            var url = binding.etContentNavegador.text.toString()
            if (url.trim().isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa una URI o URL", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }

            openWebPage(url)
        }
    }

    private fun shareText(message: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Compartir usando...")

        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(shareIntent)
        } else {
            Toast.makeText(this, "No hay aplicaciones disponibles para compartir", Toast.LENGTH_LONG).show()
        }
    }

    fun openWebPage(url: String) {
        val webpage: Uri = url.toUri()
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}