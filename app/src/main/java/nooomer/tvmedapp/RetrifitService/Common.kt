package nooomer.tvmedapp.RetrifitService

import com.tvmedicine.RetrifitService.RetrofitClient
import nooomer.tvmedapp.API

object Common {
    private const val BASE_URL = "http://www.u1554079.isp.regruhosting.ru"
    val retrofitService: API
        get() = RetrofitClient.getClient(BASE_URL).create(API::class.java)
}