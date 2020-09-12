package com.daniel.banklist.services

import com.daniel.banklist.models.vos.network.BankListResponse
import retrofit2.Call
import retrofit2.http.GET

interface BankService {
    @GET("/api/banks")
    fun getBanks(): Call<BankListResponse>

}