package com.example.appinterface

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterfacei.Producto
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class ProductoAdapter(
    private val productos: List<Producto>,
    private val categorias: Map<Int, String> // Mapa categoriaId -> nombreCategoria
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.txtNombreProducto)
        val categoria: TextView = itemView.findViewById(R.id.txtCategoriaProducto)
        val precio: TextView = itemView.findViewById(R.id.txtPrecioProducto)
        val stock: TextView = itemView.findViewById(R.id.txtStockProducto)
        val imagen: ImageView = itemView.findViewById(R.id.imgProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.nombre.text = producto.nombre
        holder.categoria.text = "Categoría: ${categorias[producto.categoriaId] ?: "Desconocida"}"

        // Formatear precio a moneda local
        val formatoPrecio = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
        holder.precio.text = "${formatoPrecio.format(producto.precio)} | Vendidos: ${producto.vendidos}"

        holder.stock.text = "Stock: ${producto.stock}"

        // Cargar imagen con Picasso
        if (!producto.imagenUrl.isNullOrEmpty()) {
            Picasso.get()
                .load(producto.imagenUrl)
                .placeholder(R.mipmap.marquez) // imagen por defecto mientras carga
                .error(R.mipmap.marquez)       // imagen por defecto si falla la carga
                .into(holder.imagen)
        } else {
            holder.imagen.setImageResource(R.mipmap.marquez)
        }
    }

    override fun getItemCount(): Int = productos.size
}
