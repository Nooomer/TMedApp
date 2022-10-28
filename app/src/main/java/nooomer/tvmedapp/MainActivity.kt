package nooomer.tvmedapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tvmedicine.models.User
import kotlinx.coroutines.*
import nooomer.tvmedapp.RetrifitService.Common
import nooomer.tvmedapp.RetrifitService.SessionManager
import nooomer.tvmedapp.models.AuthModel
import nooomer.tvmedapp.models.PatientModel

class MainActivity : AppCompatActivity() {
    private fun <T> CoroutineScope.asyncIO(ioFun: () -> T) = async(Dispatchers.IO) { ioFun() }
    lateinit var ssm:SessionManager
    val scope = CoroutineScope(Dispatchers.Main + Job())
    var result: AuthModel? = null
    var result2: List<PatientModel?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mService2 = Common.retrofitService
        ssm = SessionManager(this)

            //load()
    }

    fun load(){
        println(ssm.fetchAuthToken())
        scope.launch {
            val def = scope.asyncIO { getAllPatient("Bearer "+ssm.fetchAuthToken()) }
            def.await()

            if (result2?.get(0)?.id == null) {
                val toast = Toast.makeText(
                    applicationContext,
                    "Сломано",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
              println(result2?.get(0)?.id)
            }
        }
    }

    fun login_click(view: View){
        val login_text = findViewById<EditText>(R.id.editTextTextPersonName).toString()
        val password_text = findViewById<EditText>(R.id.editTextTextPassword).toString()
        if((ssm.fetchAuthToken()==null) and (login_text!="")) {
            scope.launch {
                val def = scope.asyncIO { auth(login_text, password_text) }
                def.await()

                if (result?.token == null) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Сломано",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    ssm.saveAuthToken(result?.token)
                    val toast = Toast.makeText(
                        applicationContext,
                        "Токен сохранен",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                    println(result?.token)
                }
            }
        }
    }

    private fun getAllPatient(token: String?) {
        val mService = Common.retrofitService
        val call = mService.getAllPatient("patient", token)
        result2 = call?.execute()?.body()
    }
    fun resetToken(view: View) {
        ssm.deleteAuthToken()
    }
    private fun auth(login: String?, password: String?) {
        val mService = Common.retrofitService
        val call = mService.auth("login", User(login, password))
        result = call?.execute()?.body()
    }

    override fun onStart() {
        super.onStart()

    }
}