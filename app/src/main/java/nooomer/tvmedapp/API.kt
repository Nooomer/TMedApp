package nooomer.tvmedapp

import com.tvmedicine.models.LoginData
import nooomer.tvmedapp.models.*
import retrofit2.Call
import retrofit2.http.*

/**Implementation of api methods and their parameters. Any method expects the name of the api method to be passed in the form method_name.php*/
interface API {

    /**Auth methods.
     * @param methodName it method name which need use. Type - [String]
     * @param phoneNumber it phone number which use like login. Type - [String]
     * @param password it password for auth. Type - [String]
     *@return [AuthModel] */
    @POST("/{method}")
    fun auth(
        @Path("method") methodName: String?,
        @Body body: LoginData?
    ): Call<AuthModel?>?
    @GET("/{method}")
            /**Get all patient methods.
             * @return [UserModel]*/
    fun getAllUser(
            @Path("method") methodName: String?,
            @Header("Authorization") token: String?
    ): Call<List<UserModel?>?>?
    @GET("/{method}")
            /**Get patient by ID methods
             * @param id Patient id information about which need get. Type - [String]
             * @return [UserModel]*/
    fun getPatientFromId(
        @Path("method") methodName: String?,
        @Query("id") id:Int?
    ): Call<List<UserModel?>?>?
    @GET("/{method}")
            /**Get all treatment methods.
             * @return [TreatmentModel]*/
    fun getAllTreatment(
        @Path("method") methodName: String?,
        @Header("Authorization") token: String?
    ): Call<List<TreatmentModel?>?>?
    @GET("/{method}/patient/{id}")
            /**Get treatment by ID methods.
             *  @param id Treatment id information about which need get. Type - [String]
             *  @return [TreatmentModel]*/
    fun getTreatmentFromPatientId(
        @Path("method") methodName: String?,
        @Path("id") id:String?,
        @Header("Authorization") token: String?
    ): Call<List<TreatmentModel?>?>?
    @GET("/{method}")
            /**Get treatment for User by phone number.
             * @param phoneNumber it Param use for get Treatment for user
             * @return [TreatmentModel]*/
    fun getTreatmentByUser(
        @Path("method") methodName: String?,
        @Query("phone_number") phoneNumber: String?
    ): Call<List<TreatmentModel?>?>?
    @GET("/{method}")
            /**Get all doctors methods.
             * @return [DoctorModel]*/
    fun getAllDoctor(
        @Path("method") methodName: String?,
    ): Call<List<DoctorModel?>?>?
    @GET("/{method}")
            /**Get doctor by ID methods.
             * @param id Get Doctor info y ID
             * @return [DoctorModel]*/
    fun getDoctorFromId(
        @Path("method") methodName: String?,
        @Query("id") id:Int?
    ): Call<List<DoctorModel?>?>?
    @GET("/{method}")
            /**Get symptoms methods.
             * @return [SymptomsModel]*/
    fun getAllSymptoms(
        @Path("method") methodName: String?,
    ): Call<List<SymptomsModel?>?>?
    @GET("/{method}")
            /**Add new treatment method.
             * @param phoneNumber use for get user ID
             * @param startDate Date of start treatment
             * @param symptomsId User choice him symptoms
             * @param soundServerLinkId User record him sound*
             * @return [AuthModel]*/
    fun addTreatment(
        @Path("method") methodName: String?,
        @Query("phone_number") phoneNumber: String?,
        @Query("start_date") startDate:String?,
        @Query("symptoms_id") symptomsId:Int?,
        @Query("sound_server_link_id") soundServerLinkId: Any
    ): Call<List<AuthModel?>?>?
    /**Add conclusion methods.
     * @param treatId Treatment ID where need add conclusion
     * @param concText Conclusion text for treatment
     * @param phoneNumber Phone number for get patient ID
     * @return [AuthModel] */
    @GET("/{method}")
    fun addConclusion(
        @Path("method") methodName: String?,
        @Query("treat_id") treatId: Int?,
        @Query("conc_text") concText: String?,
        @Query("phone_number") phoneNumber: String?
    ): Call<List<AuthModel?>?>?
    /**Delete treatment methods.
     * @param treatId Treatment ID which need delete
     * @return [AuthModel]*/
    @GET("/{method}")
    fun deleteTreatment(
        @Path("method") methodName: String?,
        @Query("treat_id") treatId: Int?,
        @Query("phone_number") phoneNumber: String?
    ): Call<List<AuthModel?>?>?
    @GET("/{method}")
            /**Get symptoms methods.
             * @return [SymptomsModel]*/
    fun getSymptomsForUser(
        @Path("method") methodName: String?,
        @Query("treat_id") treatId: Int?
    ): Call<List<SymptomsModel?>?>?

    @GET("/{method}")
            /**Get all message from treatment chat methods.
             * @return [MessagesModel]*/
    fun getMessages(
        @Path("method") methodName: String?,
        @Query("treatment_id") treatId: Int?
    ): Call<List<MessagesModel?>?>?

    @GET("/{method}")
            /**Send Message.
             * @param treatId Id of open treatment
             * @param text Message text
             * @param messageDateTime Date and time when message be send
             * @param userType Type of user who sending message
             * @return [MessagesModel]*/
    fun sendMessages(
        @Path("method") methodName: String?,
        @Query("treatment_id") treatId: Int?,
        @Query("text") text: String?,
        @Query("link") sound_server_link: String?,
        @Query("message_datetime")  messageDateTime: String?,
        @Query("user_type") userType: String?
    ): Call<List<AuthModel?>?>?
}