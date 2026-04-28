package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPartidoBinding


class PartidoAdapter(private val listaPartidos: List<Partido>) :
    RecyclerView.Adapter<PartidoAdapter.PartidoViewHolder>() {

    // El ViewHolder contiene la referencia a la vista de cada ítem
    class PartidoViewHolder(val binding: ItemPartidoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidoViewHolder {
        val binding = ItemPartidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PartidoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PartidoViewHolder, position: Int) {
        val partido = listaPartidos[position]
        holder.binding.apply {
            txtCancha.text = partido.nombreCancha
            txtHorario.text = "Horario: ${partido.horario}"
            txtJugadoresFaltantes.text = "Faltan ${partido.jugadoresFaltantes} jugadores"
        }
    }

    override fun getItemCount(): Int = listaPartidos.size
}