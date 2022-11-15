package nooomer.tvmedapp.RetrifitService

import android.content.Context
import android.content.SharedPreferences
import nooomer.tvmedapp.R
import nooomer.tvmedapp.interfaces.PreferenceDataType
import nooomer.tvmedapp.interfaces.TSessionManager

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager(context: Context): TSessionManager {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    private var token: String?
        get() = prefs.getString(USER_TOKEN, null)
        set(value) {
            editor.putString(USER_TOKEN, value)
        }
    private var tokenLifetime: String?
        get() = prefs.getString(TOKEN_LIFETIME, null)
        set(value) {
            editor.putString(TOKEN_LIFETIME, value)
        }

    private var userType: String?
        get() = prefs.getString(USER_TYPE, null)
        set(value) {
            editor.putString(USER_TYPE, value)
        }
    private var userId: String?
        get() = prefs.getString(USER_ID, null)
        set(value) {
            editor.putString(USER_ID, value)
        }

    init{
        if(fetch(USER_TOKEN)==null) {
            tokenLifetime = ""
            token = ""
        }
    }

    override fun validation(): Boolean {
        return fetch(USER_TOKEN) != ""
    }

    override fun fetch(param: String):String? {
        when(param) {
            USER_TOKEN -> return token
            TOKEN_LIFETIME -> return tokenLifetime
            USER_TYPE -> return userType
            USER_ID -> return userId
        }
        return "Not Found params"
    }

    override fun save(param: String, data: String?) {
        val editor = prefs.edit()
        when(param) {
            USER_TOKEN -> token = data
            TOKEN_LIFETIME -> tokenLifetime = data
            USER_TYPE -> userType = data
            USER_ID -> userId = data
        }
        editor.apply()
    }

    override fun clearSession() {
        editor.clear()
        editor.apply()
    }
}