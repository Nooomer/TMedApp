package nooomer.tvmedapp.RetrifitService

import android.content.Context
import android.content.SharedPreferences
import nooomer.tvmedapp.R

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val TOKEN_LIFETIME = "token_lifetime"
        const val USER_TYPE = "user_type"
    }

    init{
        if(fetchAuthToken()==null) {
            saveLifeTime("")
            saveAuthToken("")
        }
    }

fun valid():Boolean{
    return fetchAuthToken() != ""
}



    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }
    fun saveLifeTime(lifetime: String?){
        val editor = prefs.edit()
        editor.putString(TOKEN_LIFETIME, lifetime)
        editor.apply()
    }
    fun saveUserType(user_type: String?){
        val editor = prefs.edit()
        editor.putString(USER_TYPE, user_type)
        editor.apply()
    }
fun deleteAll(){
    val editor = prefs.edit()
    editor.clear()
    editor.apply()
}
    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
    fun fetchTokenLifeTime(): String? {
        return prefs.getString(TOKEN_LIFETIME, null)
    }
    fun fetchUserType(): String? {
        return prefs.getString(USER_TYPE, null)
    }
}