package nooomer.tvmedapp.interfaces

interface TSessionManager: PreferenceDataType {
    fun fetch(param: String):String?
    fun save(param: String, data: String?)
    fun clearSession()
    fun validation():Boolean
}