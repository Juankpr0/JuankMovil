package com.example.appinterface

import java.io.Serializable

data class Categoria(
    var id: Long? = null,
    val nombre: String,
    val descripcion: String? = null
) : Serializable
