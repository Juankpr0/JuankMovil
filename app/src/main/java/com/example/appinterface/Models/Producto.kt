package com.example.appinterfacei

import java.io.Serializable

data class Producto(
    var id: Long? = null,
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val categoriaId: Int = 0,
    val imagenUrl: String? = null,
    val vendidos: Int = 0
) : Serializable
