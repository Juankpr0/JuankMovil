package com.example.appinterface.Api

import com.example.appinterfacei.Producto
import retrofit2.Call
import retrofit2.http.*

interface ApiProducto {

    @GET("api/productos")
        fun getProductos(): Call<List<Producto>>

    @GET("api/productos/{id}")
    fun getProductoById(@Path("id") id: Int): Call<Producto>

    @POST("api/productos")
    fun createProducto(@Body producto: Producto): Call<String>

    @PUT("api/productos/{id}")
    fun updateProducto(@Path("id") id: Int, @Body producto: Producto): Call<String>

    @DELETE("api/productos/{id}")
    fun deleteProducto(@Path("id") id: Int): Call<String>
}
