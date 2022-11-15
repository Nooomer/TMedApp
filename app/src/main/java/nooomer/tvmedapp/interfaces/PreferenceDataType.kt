package nooomer.tvmedapp.interfaces

interface PreferenceDataType {
    val USER_TOKEN: String
        get() = "user_token"
    val TOKEN_LIFETIME: String
        get() = "token_lifetime"
    val USER_TYPE: String
        get() = "user_type"
    val USER_ID: String
        get() = "user_id"
}