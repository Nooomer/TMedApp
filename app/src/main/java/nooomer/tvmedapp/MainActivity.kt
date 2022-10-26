package nooomer.tvmedapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tvmedicine.models.User
import nooomer.tvmedapp.RetrifitService.Common
import nooomer.tvmedapp.RetrifitService.SessionManager
import nooomer.tvmedapp.models.AuthModel
import nooomer.tvmedapp.models.PatientModel
import nooomer.tvmedapp.models.TreatmentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Console

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mService2 = Common.retrofitService
        mService2.auth("login", User("test","test"))?.enqueue(object : Callback <List<AuthModel?>?>{
                override fun onResponse(
                    call: Call<List<AuthModel?>?>?,
                    authResponse: Response<List<AuthModel?>?>?
                ) {
                    if (authResponse?.code() == 401) {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Не получилось войти",
                            Toast.LENGTH_SHORT
                        )
                        println(authResponse)
                        toast.show()
                    } else {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Гуд",
                            Toast.LENGTH_SHORT
                        )
                        println(authResponse?.body()?.get(0)?.token)
                        println(authResponse)
                        toast.show()
                    }
                }


                override fun onFailure(call: Call<List<AuthModel?>?>, t: Throwable) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Все сломалось ${t.message}",
                        Toast.LENGTH_SHORT
                    )
                    println(t.message)
                    toast.show()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        val mService2 = Common.retrofitService
        val ssm = SessionManager(this)
        mService2.getAllPatient("patient", token = ssm.fetchAuthToken())?.enqueue(object : Callback <List<PatientModel?>?>{
            override fun onResponse(
                call: Call<List<PatientModel?>?>?,
                authResponse: Response<List<PatientModel?>?>?
            ) {
                if (authResponse?.code() == 401) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Не получилось получить данные",
                        Toast.LENGTH_SHORT
                    )
                    println(authResponse)
                    toast.show()
                } else {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Гуд",
                        Toast.LENGTH_SHORT
                    )
                    println(authResponse)
                    toast.show()
                }
            }


            override fun onFailure(call: Call<List<PatientModel?>?>, t: Throwable) {
                val toast = Toast.makeText(
                    applicationContext,
                    "Все сломалось ${t.message}",
                    Toast.LENGTH_SHORT
                )
                println(t.message)
                toast.show()
            }
        })
    }
}