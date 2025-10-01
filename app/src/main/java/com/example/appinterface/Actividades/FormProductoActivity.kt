package com.example.appinterface.Actividades

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R
import com.example.appinterface.Retrofit.RetrofitInstance
import com.example.appinterfacei.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class FormProductoActivity : AppCompatActivity() {

    private lateinit var edtNombre: EditText
    private lateinit var edtPrecio: EditText
    private lateinit var edtCantidad: EditText
    private lateinit var edtUrlImagen: EditText
    private lateinit var spinnerCategoria: Spinner
    private lateinit var btnGuardar: Button
    private lateinit var progressBar: ProgressBar

    private var productoId: Long = -1L
    private var categoriaIdSeleccionada: Int = 0

    private val categorias = listOf(
        Pair(0, "Sin categoría"),
        Pair(1, "Electrónica"),
        Pair(2, "Ropa"),
        Pair(3, "Hogar")
        // Agrega más categorías según tu base de datos
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_producto)

        edtNombre = findViewById(R.id.edtNombreProducto)
        edtPrecio = findViewById(R.id.edtPrecioProducto)
        edtCantidad = findViewById(R.id.edtStockProducto)
        edtUrlImagen = findViewById(R.id.edtUrlImagen)
        spinnerCategoria = findViewById(R.id.spinnerCategoria)
        btnGuardar = findViewById(R.id.btnGuardarProducto)
        progressBar = findViewById(R.id.progressBar)

        // Configurar Spinner de categorías
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias.map { it.second })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapter
        spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categoriaIdSeleccionada = categorias[position].first
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Verificar si es modo edición
        productoId = intent.getLongExtra("producto_id", -1L)
        if (productoId != -1L) {
            edtNombre.setText(intent.getStringExtra("nombre"))
            edtPrecio.setText(intent.getDoubleExtra("precio", 0.0).toString())
            edtCantidad.setText(intent.getIntExtra("stock", 0).toString())
            edtUrlImagen.setText(intent.getStringExtra("imagenUrl") ?: "")
            val catId = intent.getIntExtra("categoriaId", 0)
            val index = categorias.indexOfFirst { it.first == catId }
            if (index >= 0) spinnerCategoria.setSelection(index)
            btnGuardar.text = "Actualizar Producto"
        }

        btnGuardar.setOnClickListener { guardarOActualizarProducto() }
    }

    private fun guardarOActualizarProducto() {
        val nombre = edtNombre.text.toString().trim()
        val precio = edtPrecio.text.toString().toDoubleOrNull()
        val cantidad = edtCantidad.text.toString().toIntOrNull()
        val imagenUrl = edtUrlImagen.text.toString().takeIf { it.isNotEmpty() }

        if (nombre.isEmpty()) {
            edtNombre.error = "Ingrese el nombre"
            return
        }
        if (precio == null) {
            edtPrecio.error = "Ingrese un precio válido"
            return
        }
        if (cantidad == null) {
            edtCantidad.error = "Ingrese la cantidad"
            return
        }

        val producto = Producto(
            id = if (productoId == -1L) null else productoId,
            nombre = nombre,
            precio = precio,
            stock = cantidad,
            categoriaId = categoriaIdSeleccionada,
            imagenUrl = imagenUrl,
            vendidos = 0
        )

        Log.d("FormProducto", "Producto a enviar: $producto")

        progressBar.visibility = View.VISIBLE
        btnGuardar.isEnabled = false

        val call: Call<Producto> = if (productoId == -1L) {
            RetrofitInstance.apiProducto.createProducto(producto)
        } else {
            RetrofitInstance.apiProducto.updateProducto(productoId, producto)
        }

        call.enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                progressBar.visibility = View.GONE
                btnGuardar.isEnabled = true
                Log.d("FormProducto", "Response code: ${response.code()}, body: ${response.body()}")
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@FormProductoActivity,
                        if (productoId == -1L) "Producto agregado" else "Producto actualizado",
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@FormProductoActivity, "Error en la operación", Toast.LENGTH_SHORT).show()
                    Log.e("FormProducto", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                progressBar.visibility = View.GONE
                btnGuardar.isEnabled = true
                Toast.makeText(this@FormProductoActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("FormProducto", "onFailure: ${t.message}")
            }
        })
    }
}
