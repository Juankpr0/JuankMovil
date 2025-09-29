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
    private lateinit var edtCantidad: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_producto)

        edtNombre = findViewById(R.id.edtNombreProducto)
        edtPrecio = findViewById(R.id.edtPrecioProducto)
        edtCantidad = findViewById(R.id.edtStockProducto)
        btnGuardar = findViewById(R.id.btnGuardarProducto)

        btnGuardar.setOnClickListener { guardarProducto() }
    }

    private fun guardarProducto() {
        val nombre = edtNombre.text.toString()
        val precio = edtPrecio.text.toString().toDoubleOrNull()
        val cantidad = edtCantidad.text.toString().toIntOrNull()

        if (nombre.isEmpty() || precio == null || cantidad == null) {
            Toast.makeText(this, "Completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitInstance.apiProducto.createProducto(
            Producto(0, nombre, precio, cantidad)
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@FormProductoActivity, "Producto agregado", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK) // Indica que se agregó correctamente
                    finish()
                } else {
                    Toast.makeText(this@FormProductoActivity, "Error al agregar producto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@FormProductoActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
