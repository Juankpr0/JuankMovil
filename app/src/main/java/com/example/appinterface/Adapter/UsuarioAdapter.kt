package com.example.appinterface

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter(
    private var usuarios: List<Usuario>,
    private val listener: OnUsuarioClickListener
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    interface OnUsuarioClickListener {
        fun onEditarClick(usuario: Usuario)
        fun onEliminarClick(usuario: Usuario)
    }

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.txtNombreUsuario)
        val email: TextView = itemView.findViewById(R.id.txtEmailUsuario)
        val rol: TextView = itemView.findViewById(R.id.txtRolUsuario)
        val activo: TextView = itemView.findViewById(R.id.txtActivoUsuario)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditarUsuario)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarUsuario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]

        holder.nombre.text = usuario.nombre
        holder.email.text = "Email: ${usuario.email}"
        holder.rol.text = "Rol: ${usuario.rol}"
        holder.activo.text = if (usuario.activo) "Activo" else "Inactivo"

        holder.btnEditar.setOnClickListener { listener.onEditarClick(usuario) }
        holder.btnEliminar.setOnClickListener { listener.onEliminarClick(usuario) }
    }

    override fun getItemCount(): Int = usuarios.size

    // Método para actualizar la lista cuando cambian los datos
    fun actualizarLista(nuevaLista: List<Usuario>) {
        usuarios = nuevaLista
        notifyDataSetChanged()
    }
}
