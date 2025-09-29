package com.example.appinterface

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Actividades.FormProductoActivity
import com.example.appinterface.Actividades.ProductoAdapter
import com.example.appinterface.Retrofit.RetrofitInstance
import com.example.appinterfacei.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregar: Button

    private val agregarProductoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            cargarProductos() // Recarga la lista al regresar
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        recyclerView = findViewById(R.id.recyclerProductos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAgregar = findViewById(R.id.btnAgregarProducto)
        btnAgregar.setOnClickListener {
            val intent = Intent(this, FormProductoActivity::class.java)
            agregarProductoLauncher.launch(intent)
        }

        cargarProductos()
    }

    private fun cargarProductos() {
        RetrofitInstance.apiProducto.getProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val productos = response.body()
                    if (!productos.isNullOrEmpty()) {
                        recyclerView.adapter = ProductoAdapter(productos)
                    } else {
                        Toast.makeText(this@MainActivity, "No hay productos disponibles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error en la API: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
