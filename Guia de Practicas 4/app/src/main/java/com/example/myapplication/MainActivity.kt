package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Void?>

    // 1. Definimos el launcher para recibir el dato de vuelta
    private val secondActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val nuevoNombre = data?.getStringExtra("EXTRA_NUEVO_NOMBRE")
            // Actualizamos el campo con el nombre editado que volvió
            binding.etName.setText(nuevoNombre)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        registerPhotoLauncher()

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

        binding.btnOpenMap.setOnClickListener {
            val query = binding.etLocation.text.toString()
            if (query.trim().isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa una dirección", Toast.LENGTH_SHORT).show()
            } else {
                // geo:0,0?q= query permite buscar por nombre de lugar
                val uri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
                openMap(uri)
            }
        }

        binding.btnDial.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            if (phone.isNotBlank()) {
                /* Seguridad: ACTION_DIAL no requiere permisos CALL_PHONE porque
                   solo "prepara" el número en el teclado; el usuario decide si llama.
                   ACTION_CALL llamaría directo, pero es riesgoso y requiere permiso explícito.
                */
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Por favor, ingresa un número de teléfono", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnTakePhoto.setOnClickListener {
            // Mostrar el indicador de carga ANTES de lanzar la cámara
            binding.pbLoadingPhoto.visibility = View.VISIBLE

            // Lanzar el contrato. Como PicturePreview no requiere input, pasamos null.
            takePhotoLauncher.launch(null)
        }

        binding.btnNext.setOnClickListener {
            val nombre = binding.etName.text.toString()

            // Intent Explícito
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("EXTRA_NOMBRE", nombre)
            }

            // Lanzamos la actividad esperando un resultado
            secondActivityLauncher.launch(intent)
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
            Toast.makeText(
                this,
                "No hay aplicaciones disponibles para compartir",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun openWebPage(url: String) {
        val webpage: Uri = url.toUri()
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun openMap(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun registerPhotoLauncher() {
        // ActivityResultContracts.TakePicturePreview() nos devuelve un Bitmap (miniatura)
        // sin necesidad de gestionar archivos locales. Es ideal para previsualizaciones.
        takePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
                // Usamos lifecycleScope para no bloquear la app mientras esperamos
                lifecycleScope.launch {
                    if (bitmap != null) {
                        // Hacemos que "piense" por 2 segundos (2000 ms)
                        delay(5000)

                        binding.pbLoadingPhoto.visibility = View.GONE
                        binding.ivPhotoPreview.setImageBitmap(bitmap)
                        Toast.makeText(this@MainActivity, "Foto procesada!", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.pbLoadingPhoto.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "Captura cancelada", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}