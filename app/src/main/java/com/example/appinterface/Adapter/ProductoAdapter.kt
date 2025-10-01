package com.example.appinterface.Actividades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterfacei.Producto

class ProductoAdapter(
    private var productos: List<Producto>,
    private val listener: OnProductoClickListener
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    interface OnProductoClickListener {
        fun onEditarClick(producto: Producto)
        fun onEliminarClick(producto: Producto)
    }

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreProducto)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioProducto)
        val tvCantidad: TextView = view.findViewById(R.id.tvCantidadProducto)
        val btnEditar: Button = view.findViewById(R.id.btnEditarProducto)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun getItemCount(): Int = productos.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = "Precio: ${producto.precio}"
        holder.tvCantidad.text = "Stock: ${producto.stock}"

        holder.btnEditar.setOnClickListener {
            listener.onEditarClick(producto)
        }

        holder.btnEliminar.setOnClickListener {
            listener.onEliminarClick(producto)
        }
    }

    fun actualizarLista(lista: List<Producto>) {
        productos = lista
        notifyDataSetChanged()
    }
}
