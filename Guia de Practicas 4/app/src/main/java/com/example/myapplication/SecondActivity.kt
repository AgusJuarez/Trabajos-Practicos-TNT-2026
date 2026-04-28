package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Recepción de datos
        val nombreRecibido = intent.getStringExtra("EXTRA_NOMBRE") ?: ""
        binding.tvGreeting.text = "Bienvenido, $nombreRecibido"
        binding.etEditName.setText(nombreRecibido)

        // 2. Retorno bidireccional
        binding.btnReturn.setOnClickListener {
            val nombreEditado = binding.etEditName.text.toString()

            val resultIntent = Intent().apply {
                putExtra("EXTRA_NUEVO_NOMBRE", nombreEditado)
            }

            // Establecemos el resultado como OK y pasamos el intent con los datos
            setResult(RESULT_OK, resultIntent)

            // Cerramos esta actividad para volver a la anterior
            finish()
        }
    }
}