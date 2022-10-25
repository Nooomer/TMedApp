package nooomer.tvmedapp.models
/**Response model for doctor information
 * @param id it id's from DB. Return type [Int]
 * @param surename it doctor surename from DB. Return type [String]
 * @param name it doctor name from DB. Return type [String]
 * @param s_name it doctor middle name from DB. Return type [String]
 * @param phone_number it doctor phone_number from DB. Return type [String]
 * @param password it doctor password from DB. Return type [String]
 * @param hospital_id it doctor hospital id's from DB. Return type [Int]*/
data class DoctorModel(
        val id : Int,
        val surename : String,
        val name : String,
        val s_name : String,
        val phone_number : String,
        val password : String,
        val hospital_id : Int
        )
