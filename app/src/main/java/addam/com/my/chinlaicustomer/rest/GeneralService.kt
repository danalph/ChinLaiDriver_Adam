package addam.com.my.chinlaicustomer.rest

import addam.com.my.chinlaicustomer.rest.model.*
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by Addam on 7/1/2019.
 */
interface GeneralService {
    @GET("auth/encryption/{password}")
    fun getPasswordEncrypt(@Path("password") password: String): Single<PasswordEncryptResponse>

    @POST("auth/customer/login")
    fun getlogin(@Body userLoginRequest: UserLoginRequest): Single<UserLoginResponse>

    @POST("auth/salesperson/login")
    fun getSalesLogin(@Body userLoginRequest: UserLoginRequest): Single<SalesLoginResponse>

    @GET("driver/{driverId}/trips")
    fun getTrips(@Path("driverId") driverId: String, @Query("offset") offset: String,
                 @Query("limit") limit: String,
                 @Query("status") status: String): Single<TripsResponse>

    @GET("driver/{driverId}/trip/{tripId}")
    fun getSingleTrip(@Path("driverId") driverId: String, @Path("tripId") tripId: String,
                      @Query("offset") offset: String,
                      @Query("limit") limit: String,
                      @Query("status") status: String): Single<ViewDeliveryTripResponse>

    @GET("driver/{id}/trip/{pID}/{type}/{docID}")
    fun getDestination(@Path("id") driverID: String,
                       @Path("pID") tripID: String,
                       @Path("type") type: String,
                       @Path("docID")docID: String): Single<DestinationResponse>

    @PUT("driver/{id}/trip/{pID}/document/{type}")
    fun updateDocumentTrip(@Path("id") driverID: String,
                           @Path("pID") tripID: String,
                           @Path("type") type: String,
                           @Body updateDocumentRequest: UpdateDocumentRequest): Single<UpdateDocumentResponse>

    @POST("driver/{id}/trip/{pID}/image/{type}")
    fun uploadPhoto(@Path("id")driverID: String,
                    @Path("pID")tripID: String,
                    @Path("type")type: String,
                    @Body uploadPhotoRequest: UploadPhotoRequest): Single<UploadPhotoResponse>
}