package nooomer.tvmedapp.RetrifitService

import com.tvmedicine.RetrifitService.RetrofitClient
import nooomer.tvmedapp.API

object Common {
    private const val BASE_URL = "https://0713-95-105-125-220.eu.ngrok.io"
    val retrofitService: API
        get() = RetrofitClient.getClient(BASE_URL).create(API::class.java)
}