package com.example.appinterface.Actividades

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.CategoriaAdapter
import com.example.appinterface.Retrofit.RetrofitInstance
import com.example.appinterface.Categoria
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerCategorias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar adapter vacío
        recyclerView.adapter = CategoriaAdapter(emptyList())

        // Llamada a la API para obtener categorías
        RetrofitInstance.ApiCategoria.getCategorias().enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (response.isSuccessful) {
                    val categorias = response.body()
                    if (!categorias.isNullOrEmpty()) {
                        recyclerView.adapter = CategoriaAdapter(categorias)
                    } else {
                        Toast.makeText(this@CategoriaActivity, "No hay categorías disponibles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@CategoriaActivity, "Error en la API: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                Toast.makeText(this@CategoriaActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
