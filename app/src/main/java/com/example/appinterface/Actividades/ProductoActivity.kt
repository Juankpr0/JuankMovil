package com.example.appinterface.Actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.Retrofit.RetrofitInstance
import com.example.appinterfacei.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoActivity : AppCompatActivity() {

    private lateinit var btnAgregarProducto: Button
    private lateinit var recyclerProductos: RecyclerView
    private lateinit var productoAdapter: ProductoAdapter // Necesitarás un adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        btnAgregarProducto = findViewById(R.id.btnAgregarProducto)
        recyclerProductos = findViewById(R.id.recyclerProductos)

        recyclerProductos.layoutManager = LinearLayoutManager(this)
        productoAdapter = ProductoAdapter(listOf()) // Lista inicial vacía
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
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                // Mostrar error si falla la conexión
            }
        })
    }
}
