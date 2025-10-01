package com.example.appinterface.Actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.Retrofit.RetrofitInstance
import com.example.appinterface.Usuario
import com.example.appinterface.UsuarioAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioActivity : AppCompatActivity(), UsuarioAdapter.OnUsuarioClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var usuarioAdapter: UsuarioAdapter
    private lateinit var btnAgregarUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)

        recyclerView = findViewById(R.id.recyclerUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario)
        btnAgregarUsuario.setOnClickListener {
            startActivity(Intent(this, FormUsuarioActivity::class.java))
        }

        obtenerUsuarios()
    }

    private fun obtenerUsuarios() {
        RetrofitInstance.apiUsuario.getUsers().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    val usuarios = response.body() ?: emptyList()
                    usuarioAdapter = UsuarioAdapter(usuarios, this@UsuarioActivity)
                    recyclerView.adapter = usuarioAdapter
                } else {
                    Toast.makeText(this@UsuarioActivity, "Error al obtener usuarios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                Toast.makeText(this@UsuarioActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onEditarClick(usuario: Usuario) {
        val intent = Intent(this, FormUsuarioActivity::class.java)
        intent.putExtra("usuario_id", usuario.id)
        intent.putExtra("nombre", usuario.nombre)
        intent.putExtra("email", usuario.email)
        intent.putExtra("rol", usuario.rol)
        intent.putExtra("activo", usuario.activo)
        startActivity(intent)
    }

    override fun onEliminarClick(usuario: Usuario) {
        RetrofitInstance.apiUsuario.deleteUser(usuario.id!!).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UsuarioActivity, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                    obtenerUsuarios()
                } else {
                    Toast.makeText(this@UsuarioActivity, "Error al eliminar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UsuarioActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        obtenerUsuarios() // 🔄 recargar lista al volver del formulario
    }
}
