package com.example.appinterface.Actividades

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Categoria
import com.example.appinterface.R
import com.example.appinterface.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormCategoriaActivity : AppCompatActivity() {

    private lateinit var edtNombre: EditText
    private lateinit var edtDescripcion: EditText
    private lateinit var btnGuardar: Button
    private var categoriaId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_categoria)

        edtNombre = findViewById(R.id.edtNombreCategoria)
        edtDescripcion = findViewById(R.id.edtDescripcionCategoria)
        btnGuardar = findViewById(R.id.btnGuardarCategoria)

        categoriaId = intent.getLongExtra("categoria_id", -1L).takeIf { it != -1L }
        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")

        if (categoriaId != null) {
            edtNombre.setText(nombre)
            edtDescripcion.setText(descripcion)
            btnGuardar.text = "Actualizar"
        }

        btnGuardar.setOnClickListener { guardarCategoria() }
    }

    private fun guardarCategoria() {
        val nombre = edtNombre.text.toString().trim()
        val descripcion = edtDescripcion.text.toString().trim()

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val categoria = Categoria(
            id = categoriaId,
            nombre = nombre,
            descripcion = descripcion
        )

        val call: Call<Void> = if (categoriaId == null) {
            RetrofitInstance.apiCategoria.createCategoria(categoria)
        } else {
            RetrofitInstance.apiCategoria.updateCategoria(categoriaId!!, categoria)
        }

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val msg = if (categoriaId == null) "Categoría creada" else "Categoría actualizada"
                    Toast.makeText(this@FormCategoriaActivity, msg, Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error desconocido"
                    Toast.makeText(this@FormCategoriaActivity, "Error: ${response.code()} - $errorMsg", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@FormCategoriaActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
