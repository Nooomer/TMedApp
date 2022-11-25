package nooomer.tvmedapp.RetrifitService

import com.tvmedicine.RetrifitService.RetrofitClient
import nooomer.tvmedapp.API

object Common {
    private const val BASE_URL = "https://tvmed.herokuapp.com"
    val retrofitService: API
        get() = RetrofitClient.getClient(BASE_URL).create(API::class.java)
}