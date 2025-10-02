package com.example.appinterface.Actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.Retrofit.RetrofitInstance
import com.example.appinterfacei.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoActivity : AppCompatActivity(), ProductoAdapter.OnProductoClickListener {

    private lateinit var btnAgregarProducto: Button
    private lateinit var recyclerProductos: RecyclerView
    private lateinit var productoAdapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        btnAgregarProducto = findViewById(R.id.btnAgregarProducto)
        recyclerProductos = findViewById(R.id.recyclerProductos)

        recyclerProductos.layoutManager = LinearLayoutManager(this)
        productoAdapter = ProductoAdapter(listOf(), this)
        recyclerProductos.adapter = productoAdapter

        btnAgregarProducto.setOnClickListener {
            val intent = Intent(this, FormProductoActivity::class.java)
            startActivity(intent)
        }

        cargarProductos()
    }

    private fun cargarProductos() {
        RetrofitInstance.apiProducto.getProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    productoAdapter.actualizarLista(lista)
                } else {
                    Toast.makeText(this@ProductoActivity, "Error al cargar productos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(this@ProductoActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // ----------------------------
    // MÉTODOS DEL LISTENER
    // ----------------------------

    override fun onEditarClick(producto: Producto) {
        val intent = Intent(this, FormProductoActivity::class.java).apply {
            putExtra("producto_id", producto.id ?: -1L)
            putExtra("nombre", producto.nombre)
            putExtra("precio", producto.precio)
            putExtra("stock", producto.stock)
            putExtra("categoriaId", producto.categoriaId)
            putExtra("imagenUrl", producto.imagenUrl ?: "")
        }
        startActivity(intent)
    }

    override fun onEliminarClick(producto: Producto) {
        val id = producto.id
        if (id != null) {
            RetrofitInstance.apiProducto.deleteProducto(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ProductoActivity, "Producto eliminado", Toast.LENGTH_SHORT).show()
                        cargarProductos()
                    } else {
                        Toast.makeText(this@ProductoActivity, "Error al eliminar producto", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@ProductoActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
        }
    }
}
