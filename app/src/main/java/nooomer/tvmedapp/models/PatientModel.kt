package nooomer.tvmedapp.models
/**Patient response model */
data class PatientModel(
        val id : Int,
        val surename : String,
        val name : String,
        val s_name : String,
        val phone_number : String,
        val insurance_number : String,
        val password : String
        )
