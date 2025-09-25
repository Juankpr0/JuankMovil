package com.example.appinterface

import java.io.Serializable

data class Usuario(
    var id: Long? = null,
    var nombre: String,
    var email: String,
    var password: String,
    var rol: String,
    var activo: Boolean = true
) : Serializable
