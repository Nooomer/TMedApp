package nooomer.tvmedapp.models

import java.util.*

data class MessagesModel(
    val message_id: Int,
    val chat_id: Int,
    val at_User_id: Int,
    val user_type: String,
    val text: String,
    val sound_server_link: String,
    val message_datetime: String
    )


