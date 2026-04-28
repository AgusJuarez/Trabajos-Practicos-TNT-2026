package com.example.myapplication



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentListaBinding

class ListaFragment : Fragment() {

    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Datos de prueba (Hardcoded como sugiere la práctica)
        val datosPrueba = listOf(
            Partido("Cancha Rayo Vallecano", "20:00", 2),
            Partido("Predio Fútbol 5", "21:30", 4),
            Partido("Estadio Municipal", "18:00", 1),
            Partido("La Canchita de la Esquina", "23:00", 3)
        )

        // 2. Configuración del RecyclerView
        binding.recyclerViewPartidos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPartidos.adapter = PartidoAdapter(datosPrueba)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Importante para evitar memory leaks
    }
}