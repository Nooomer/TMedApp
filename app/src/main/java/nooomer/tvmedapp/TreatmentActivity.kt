package nooomer.tvmedapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.*
import nooomer.tvmedapp.RetrifitService.Common
import nooomer.tvmedapp.RetrifitService.SessionManager
import nooomer.tvmedapp.models.AuthModel
import nooomer.tvmedapp.models.PatientModel
import nooomer.tvmedapp.models.TreatmentModel

class TreatmentActivity : AppCompatActivity() {
    lateinit var ssm: SessionManager
    lateinit var indicator: LinearProgressIndicator
    lateinit var recyclerView: RecyclerView
    private var viewSize: Int = 0
    val data = mutableListOf<MutableList<String?>>()

    private fun <T> CoroutineScope.asyncIO(ioFun: () -> T) = async(Dispatchers.IO) { ioFun() }
    private var result: List<TreatmentModel?>? = null
    private var result2: List<PatientModel?>? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment)
        ssm = SessionManager(this)
        println(ssm.fetchAuthToken())
        println(ssm.fetchTokenLifeTime())
        recyclerView = findViewById(R.id.rv_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        indicator = findViewById(R.id.ProgressIndicator)
        indicator.showAnimationBehavior = BaseProgressIndicator.SHOW_OUTWARD
        indicator.hideAnimationBehavior = BaseProgressIndicator.HIDE_OUTWARD
        indicator.show()
        load()
       val logoutBtn = findViewById<FloatingActionButton>(R.id.out_btn)
        logoutBtn.setOnClickListener{
            ssm.deleteAll()
        }
    }

    private fun getAllTreatment(): List<TreatmentModel?>? {
        val mService = Common.retrofitService
        val call = mService.getAllTreatment("treatment", "Bearer "+ssm.fetchAuthToken())
        result = call?.execute()?.body()
        return result
    }
    private fun load() {
        when (ssm.fetchUserType()) {
            "doctor" ->
            {
                scope.launch {
                    val def = scope.asyncIO { getAllTreatment() }
                    //result1 = patientRequest()
                    def.await()
                    viewSize = result!!.size
                    for (i in 0 until viewSize) {
                        data.add(mutableListOf<String?>(result!![i]?.patientSurename, result!![i]?.doctorSurname, result!![i]?.startdate))
                        recyclerView.adapter = RvAdapter(data, viewSize)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                    indicator.hide()
                }
            }
            "patient"->{

            }
        }
    }
}