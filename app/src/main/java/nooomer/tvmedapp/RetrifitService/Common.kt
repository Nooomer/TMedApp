package nooomer.tvmedapp.RetrifitService

import com.tvmedicine.RetrifitService.RetrofitClient
import nooomer.tvmedapp.API

object Common {
    private const val BASE_URL = "https://2871-85-26-232-96.eu.ngrok.io/"
    val retrofitService: API
        get() = RetrofitClient.getClient(BASE_URL).create(API::class.java)
}