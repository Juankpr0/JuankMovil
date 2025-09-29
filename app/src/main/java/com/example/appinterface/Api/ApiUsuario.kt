package com.example.appinterface.Api

import com.example.appinterface.Usuario
import retrofit2.Call
import retrofit2.http.*

interface ApiUsuario {
    @GET("api/user")
    fun getUsers(): Call<List<Usuario>>

    @GET("api/user/{id}")
    fun getUser(@Path("id") id: Long): Call<Usuario>

    @POST("api/user")
    fun createUser(@Body usuario: Usuario): Call<Void>

    @PUT("api/user/{id}")
    fun updateUser(@Path("id") id: Long, @Body usuario: Usuario): Call<Void>

    @DELETE("api/user/{id}")
    fun deleteUser(@Path("id") id: Long): Call<Void>
}

