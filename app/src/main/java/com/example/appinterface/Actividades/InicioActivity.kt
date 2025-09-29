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

        // Ejemplo de botones para abrir secciones
        findViewById<Button>(R.id.btnUsuarios).setOnClickListener {
            startActivity(Intent(this, UsuarioActivity::class.java))
        }

        findViewById<Button>(R.id.btnCategorias).setOnClickListener {
            startActivity(Intent(this, CategoriaActivity::class.java))
        }

        findViewById<Button>(R.id.btnProductos).setOnClickListener {
            startActivity(Intent(this, ProductoActivity::class.java))
        }
    }
}
