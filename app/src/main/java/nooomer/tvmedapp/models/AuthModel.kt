package nooomer.tvmedapp.models
/**Authorization response model. Return [token]
 * @param [token] May be "true" or "false" but it not [Boolean]. Return type - [String]*/
data class AuthModel(
    var token:String? = null
)
