package com.example.appinterface.Actividades

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R
import com.example.appinterface.Retrofit.RetrofitInstance
import com.example.appinterface.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormUsuarioActivity : AppCompatActivity() {

    private lateinit var edtNombre: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtRol: EditText
    private lateinit var edtActivo: EditText
    private lateinit var btnGuardar: Button
    private var usuarioId: Long? = null // Para diferenciar entre creación y edición

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_usuario)

        edtNombre = findViewById(R.id.edtNombreUsuario)
        edtEmail = findViewById(R.id.edtCorreoUsuario)
        edtPassword = findViewById(R.id.edtPasswordUsuario)
        edtRol = findViewById(R.id.edtRolUsuario)
        edtActivo = findViewById(R.id.edtEstadoUsuario)
        btnGuardar = findViewById(R.id.btnGuardarUsuario)

        // Revisar si venimos de edición
        usuarioId = intent.getLongExtra("usuario_id", -1L).takeIf { it != -1L }
        val nombre = intent.getStringExtra("nombre")
        val email = intent.getStringExtra("email")
        val rol = intent.getStringExtra("rol")
        val activo = intent.getBooleanExtra("activo", true)

        if (usuarioId != null) {
            edtNombre.setText(nombre)
            edtEmail.setText(email)
            edtRol.setText(rol)
            edtActivo.setText(activo.toString())
            btnGuardar.text = "Actualizar"
        }

        btnGuardar.setOnClickListener { guardarUsuario() }
    }

    private fun guardarUsuario() {
        val nombre = edtNombre.text.toString()
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        val rol = edtRol.text.toString().ifEmpty { "usuario" }
        val activo = edtActivo.text.toString().toBooleanStrictOrNull() ?: true

        if (nombre.isEmpty() || email.isEmpty() || (usuarioId == null && password.isEmpty())) {
            Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val usuario = Usuario(
            id = usuarioId ?: 0L,
            nombre = nombre,
            email = email,
            password = password,
            rol = rol,
            activo = activo
        )

        if (usuarioId == null) {
            // Crear usuario
            RetrofitInstance.apiUsuario.createUser(usuario).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@FormUsuarioActivity, "Usuario agregado", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this@FormUsuarioActivity, "Error al agregar usuario", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@FormUsuarioActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            // Actualizar usuario
            RetrofitInstance.apiUsuario.updateUser(usuarioId!!, usuario).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@FormUsuarioActivity, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this@FormUsuarioActivity, "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@FormUsuarioActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
