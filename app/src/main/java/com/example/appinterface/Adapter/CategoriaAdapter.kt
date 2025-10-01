package com.example.appinterface

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriaAdapter(
    private var categorias: List<Categoria>,
    private val listener: OnCategoriaClickListener
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    interface OnCategoriaClickListener {
        fun onEditarClick(categoria: Categoria)
        fun onEliminarClick(categoria: Categoria)
    }


    class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.txtNombreCategoria)
        val descripcion: TextView = itemView.findViewById(R.id.txtDescripcionCategoria)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditarItem)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.nombre.text = categoria.nombre
        holder.descripcion.text = categoria.descripcion ?: "Sin descripción"

        holder.btnEditar.setOnClickListener { listener.onEditarClick(categoria) }
        holder.btnEliminar.setOnClickListener { listener.onEliminarClick(categoria) }
    }

    override fun getItemCount(): Int = categorias.size

    fun actualizarLista(nuevaLista: List<Categoria>) {
        categorias = nuevaLista
        notifyDataSetChanged()
    }
}
