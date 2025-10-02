package com.example.appinterface

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.appinterface.Actividades.ProductoActivity
import com.example.appinterface.Actividades.UsuarioActivity
import com.example.appinterface.Actividades.CategoriaActivity

class InicioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botones para abrir secciones
        val btnUsuarios: Button = findViewById(R.id.btnUsuarios)
        val btnCategorias: Button = findViewById(R.id.btnCategorias)
        val btnProductos: Button = findViewById(R.id.btnProductos)

        btnUsuarios.setOnClickListener {
            startActivity(Intent(this, UsuarioActivity::class.java))
        }

        btnCategorias.setOnClickListener {
            startActivity(Intent(this, CategoriaActivity::class.java))
        }

        btnProductos.setOnClickListener {
            startActivity(Intent(this, ProductoActivity::class.java))
        }
    }
}
