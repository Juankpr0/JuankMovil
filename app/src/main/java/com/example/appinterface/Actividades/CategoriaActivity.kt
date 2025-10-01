package com.example.appinterface.Actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Categoria
import com.example.appinterface.CategoriaAdapter
import com.example.appinterface.R
import com.example.appinterface.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaActivity : AppCompatActivity(), CategoriaAdapter.OnCategoriaClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarCategoria: Button
    private lateinit var adapter: CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        recyclerView = findViewById(R.id.recyclerCategorias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CategoriaAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria)
        btnAgregarCategoria.setOnClickListener {
            val intent = Intent(this, FormCategoriaActivity::class.java)
            startActivityForResult(intent, 100) // Para refrescar cuando regrese
        }

        cargarCategorias()
    }

    private fun cargarCategorias() {
        RetrofitInstance.apiCategoria.getCategorias().enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (response.isSuccessful) {
                    val categorias = response.body()
                    if (!categorias.isNullOrEmpty()) {
                        adapter.actualizarLista(categorias)
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

    override fun onEditarClick(categoria: Categoria) {
        val intent = Intent(this, FormCategoriaActivity::class.java)
        intent.putExtra("categoria_id", categoria.id)
        intent.putExtra("nombre", categoria.nombre)
        intent.putExtra("descripcion", categoria.descripcion)
        startActivityForResult(intent, 200)
    }

    override fun onEliminarClick(categoria: Categoria) {
        categoria.id?.let { id ->
            RetrofitInstance.apiCategoria.deleteCategoria(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CategoriaActivity, "Categoría eliminada", Toast.LENGTH_SHORT).show()
                        cargarCategorias()
                    } else {
                        Toast.makeText(this@CategoriaActivity, "Error al eliminar: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@CategoriaActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } ?: run {
            Toast.makeText(this, "ID de categoría inválido", Toast.LENGTH_SHORT).show()
        }
    }

    // Refrescar lista al volver de formulario
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && (requestCode == 100 || requestCode == 200)) {
            cargarCategorias()
        }
    }
}
