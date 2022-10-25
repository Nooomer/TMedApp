package nooomer.tvmedapp.models
/**Authorization response model. Return [response]
 * @param [response] May be "true" or "false" but it not [Boolean]. Return type - [String]*/
data class AuthModel(
    var response:String? = null
)
