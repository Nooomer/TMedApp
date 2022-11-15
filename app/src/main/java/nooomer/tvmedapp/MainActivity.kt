package nooomer.tvmedapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tvmedicine.models.User
import kotlinx.coroutines.*
import nooomer.tvmedapp.RetrifitService.Common
import nooomer.tvmedapp.RetrifitService.SessionManager
import nooomer.tvmedapp.interfaces.PreferenceDataType
import nooomer.tvmedapp.interfaces.TSessionManager
import nooomer.tvmedapp.models.AuthModel
import nooomer.tvmedapp.models.PatientModel

class MainActivity : AppCompatActivity(), PreferenceDataType {
    private fun <T> CoroutineScope.asyncIO(ioFun: () -> T) = async(Dispatchers.IO) { ioFun() }
    lateinit var ssm:SessionManager
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var result: AuthModel? = null
    private var result2: List<PatientModel?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mService2 = Common.retrofitService
        ssm = SessionManager(this)
        if(ssm.validation()) {
            if ((ssm.fetch(TOKEN_LIFETIME)?.toLong()!! < (ssm.fetch(TOKEN_LIFETIME)?.toLong()
                    ?.plus(60000000000)!!))
            ) {
                val intent = Intent(
                    applicationContext,
                    TreatmentActivity::class.java
                )
                startActivity(intent)
            }
        }
            //load()
    }

    fun load(){
        scope.launch {
            val def = scope.asyncIO { getAllPatient("Bearer "+ssm.fetch(TOKEN_LIFETIME)) }
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
        val login_text = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        val password_text = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
        if(!ssm.validation() and (login_text!="")) {
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
                    ssm.save(USER_TOKEN, result?.token)
                    ssm.save(TOKEN_LIFETIME, System.currentTimeMillis().toString())
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
        ssm.clearSession()
    }
    private fun auth(login: String?, password: String?) {
        val mService = Common.retrofitService
        val call = mService.auth("login", User(login, password))
        result = call?.execute()?.body()
    }
}