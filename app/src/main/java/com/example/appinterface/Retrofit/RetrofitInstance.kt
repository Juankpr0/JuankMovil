package com.example.appinterface.Retrofit

import com.example.appinterface.Api.ApiCategoria
import com.example.appinterface.Api.ApiProducto
import com.example.appinterface.Api.ApiUsuario
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8081/"

    val apiProducto: ApiProducto by lazy { create(ApiProducto::class.java) }
    val ApiCategoria: ApiCategoria by lazy { create(ApiCategoria::class.java) }
    val ApiUsuario: ApiUsuario by lazy { create(ApiUsuario::class.java) }

    private fun <T> create(service: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
}
