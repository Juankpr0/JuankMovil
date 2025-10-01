package com.example.appinterface.Api

import com.example.appinterface.Categoria
import retrofit2.Call
import retrofit2.http.*

interface ApiCategoria {

    @GET("api/categorias")
    fun getCategorias(): Call<List<Categoria>>

    @POST("api/categorias")
    fun createCategoria(@Body categoria: Categoria): Call<Void>

    @PUT("api/categorias/{id}")
    fun updateCategoria(@Path("id") id: Long, @Body categoria: Categoria): Call<Void>

    @DELETE("api/categorias/{id}")
    fun deleteCategoria(@Path("id") id: Long): Call<Void>
}
