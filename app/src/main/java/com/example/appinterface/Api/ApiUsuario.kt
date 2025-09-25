package com.example.appinterface.Api

import com.example.appinterface.Usuario
import retrofit2.Call
import retrofit2.http.*

interface ApiUsuario {

    @GET("api/users")
    fun getUsers(): Call<List<Usuario>>

    @GET("api/users/{id}")
    fun getUserById(@Path("id") id: Long): Call<Usuario>

    @POST("api/users")
    fun createUser(@Body usuario: Usuario): Call<Void>

    @PUT("api/users/{id}")
    fun updateUser(@Path("id") id: Long, @Body usuario: Usuario): Call<Void>

    @DELETE("api/users/{id}")
    fun deleteUser(@Path("id") id: Long): Call<Void>
}
