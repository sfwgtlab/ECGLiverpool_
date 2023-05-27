package com.example.ecgliverpool.apiService

import com.example.ecgliverpool.models.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getProducts(@Url url: String): Response<ProductsResponse>
}