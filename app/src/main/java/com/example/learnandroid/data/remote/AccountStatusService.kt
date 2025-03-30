package com.example.learnandroid.data.remote

import com.example.learnandroid.data.model.AccountStatusDto
import retrofit2.http.GET

interface AccountStatusService {
    @GET("29d002d4-3ccd-4eaa-95eb-a9d1601ce123?account_number=EU67JG7744036080300903")
    suspend fun checkAccountStatus() : AccountStatusDto
}