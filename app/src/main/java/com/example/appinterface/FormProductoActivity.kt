package com.example.appinterface.Actividades

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R
import com.example.appinterface.Retrofit.RetrofitInstance
import com.example.appinterfacei.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormProductoActivity : AppCompatActivity() {

    private lateinit var edtNombre: EditText
    private lateinit var edtPrecio: EditText
    private lateinit var edtStock: EditText
    private lateinit var edtUrlImagen: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_producto)

        // Referencias a los campos
        edtNombre = findViewById(R.id.edtNombreProducto)
        edtPrecio = findViewById(R.id.edtPrecioProducto)
        edtStock = findViewById(R.id.edtStockProducto)
        edtUrlImagen = findViewById(R.id.edtUrlImagen)
        btnGuardar = findViewById(R.id.btnGuardarProducto)

        // Listener del botón Guardar
        btnGuardar.setOnClickListener {
            guardarProducto()
        }
    }

    private fun guardarProducto() {
        val nombre = edtNombre.text.toString().trim()
        val precioText = edtPrecio.text.toString().trim()
        val stockText = edtStock.text.toString().trim()
        val urlImagen = edtUrlImagen.text.toString().trim()

        if (nombre.isEmpty() || precioText.isEmpty() || stockText.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val precio = precioText.toDoubleOrNull()
        val stock = stockText.toIntOrNull()

        if (precio == null || stock == null) {
            Toast.makeText(this, "Precio o stock inválidos", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear objeto Producto
        val producto = Producto(
            id = 0, // tu backend puede generar el ID
            nombre = nombre,
            precio = precio,
            stock = stock,
            imagenUrl = urlImagen,
            categoriaId = 1, // puedes agregar un spinner para elegir la categoría
            vendidos = 0
        )

        // Llamada POST a la API
        RetrofitInstance.apiProducto.createProducto(producto).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@FormProductoActivity, "Producto creado exitosamente", Toast.LENGTH_SHORT).show()
                    finish() // cerrar actividad y volver al listado
                } else {
                    Toast.makeText(this@FormProductoActivity, "Error al crear el producto: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@FormProductoActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
