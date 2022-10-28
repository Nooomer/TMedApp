package nooomer.tvmedapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import nooomer.tvmedapp.RetrifitService.SessionManager

class TreatmentActivity : AppCompatActivity() {
    lateinit var ssm: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment)
        ssm = SessionManager(this)
        findViewById<TextView>(R.id.textView1).text = ssm.fetchAuthToken()
    }
}