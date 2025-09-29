package com.example.appinterface.Actividades

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R
import com.example.appinterface.Retrofit.RetrofitInstance
import com.example.appinterface.Categoria
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormCategoriaActivity : AppCompatActivity() {

    private lateinit var edtNombre: EditText
    private lateinit var edtDescripcion: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_categoria)

        edtNombre = findViewById(R.id.edtNombreCategoria)
        edtDescripcion = findViewById(R.id.edtDescripcionCategoria)
        btnGuardar = findViewById(R.id.btnGuardarCategoria)

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
            id = 0,
            nombre = nombre,
            descripcion = descripcion
        )

        RetrofitInstance.apiCategoria.createCategoria(categoria).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@FormCategoriaActivity, "Categoría creada", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@FormCategoriaActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@FormCategoriaActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
