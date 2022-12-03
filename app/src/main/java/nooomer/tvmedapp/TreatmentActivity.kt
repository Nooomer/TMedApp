package nooomer.tvmedapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.*
import nooomer.tvmedapp.RetrifitService.SessionManager
import nooomer.tvmedapp.interfaces.PreferenceDataType
import nooomer.tvmedapp.interfaces.RetrorfitFun
import nooomer.tvmedapp.models.UserModel
import nooomer.tvmedapp.models.TreatmentModel

class TreatmentActivity : AppCompatActivity(), PreferenceDataType, RetrorfitFun {
    lateinit var ssm: SessionManager
    lateinit var indicator: LinearProgressIndicator
    lateinit var recyclerView: RecyclerView
    private var viewSize: Int = 0
    var data = mutableListOf<MutableList<String?>>()

    private fun <T> CoroutineScope.asyncIO(ioFun: () -> T) = async(Dispatchers.IO) { ioFun() }
    private var result: List<TreatmentModel?>? = null
    private var result2: List<UserModel?>? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment)
        ssm = SessionManager(this)
        recyclerView = findViewById(R.id.rv_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        indicator = findViewById(R.id.ProgressIndicator)
        indicator.showAnimationBehavior = BaseProgressIndicator.SHOW_OUTWARD
        indicator.hideAnimationBehavior = BaseProgressIndicator.HIDE_OUTWARD
        indicator.show()
        load()
       val logoutBtn = findViewById<FloatingActionButton>(R.id.out_btn)
        logoutBtn.setOnClickListener{
            ssm.clearSession()
            val intent = Intent(
                applicationContext,
                MainActivity::class.java
            )
            finish()
            startActivity(intent)
        }
    }


    private fun load() {
         when (ssm.fetch(USER_TYPE)) {
            "doctor" ->
            {
                scope.launch {
                        val def = scope.asyncIO {
                            result =
                                get(
                                    "all",
                                    "treatment","Bearer "+ssm.fetch(USER_TOKEN)
                                ) as List<TreatmentModel?>?
                        }
                            def.await()
                    try {
                        viewSize = result!!.size
                        for (i in 0 until viewSize) {
                            data.add(
                                mutableListOf<String?>(
                                    result!![i]?.patientSurename,
                                    result!![i]?.doctorSurname,
                                    result!![i]?.startdate
                                )
                            )
                            recyclerView.adapter = RvAdapter(data, viewSize)
                        }
                    }
                    catch (e: NullPointerException){
                        val toast = Toast.makeText(
                            applicationContext,
                            "Данные не загружены",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                        indicator.hide()
                    }
            }
            "patient"->{
                scope.launch {
                    val def = scope.asyncIO {
                        result =
                            get(
                                "filtered",
                                "treatment","Bearer "+ssm.fetch(USER_TOKEN), ssm.fetch(USER_ID)!!
                            ) as List<TreatmentModel?>?
                    }
                    def.await()
                    try {
                        viewSize = result!!.size
                    }
                    catch (e: NullPointerException){
                        val toast = Toast.makeText(
                            applicationContext,
                            "Данные не загружены",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                    for (i in 0 until viewSize) {
                        data.add(
                            mutableListOf<String?>(
                                result!![i]?.patientSurename,
                                result!![i]?.doctorSurname,
                                result!![i]?.startdate
                            )
                        )
                        recyclerView.adapter = RvAdapter(data,viewSize)
                    }
                    indicator.hide()
                }
            }
        }
    }
    override fun onRestart() {
        super.onRestart()
        indicator = findViewById(R.id.ProgressIndicator)
        indicator.showAnimationBehavior = BaseProgressIndicator.SHOW_OUTWARD
        indicator.hideAnimationBehavior = BaseProgressIndicator.HIDE_OUTWARD
        indicator.show()
        //result = null
        load()
    }
}
