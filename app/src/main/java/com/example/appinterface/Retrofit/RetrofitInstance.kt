package com.example.appinterface.Retrofit

import com.example.appinterface.Api.ApiCategoria
import com.example.appinterface.Api.ApiProducto
import com.example.appinterface.Api.ApiUsuario
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:8081/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiCategoria: ApiCategoria by lazy {
        retrofit.create(ApiCategoria::class.java)
    }

    val apiProducto: ApiProducto by lazy {
        retrofit.create(ApiProducto::class.java)
    }

    val apiUsuario: ApiUsuario by lazy {
        retrofit.create(ApiUsuario::class.java)
    }
}
