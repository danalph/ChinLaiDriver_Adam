package addam.com.my.chinlaicustomer.rest

import addam.com.my.chinlaicustomer.rest.model.*
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Addam on 7/1/2019.
 */
@Singleton
class GeneralRepository @Inject constructor(private val api: GeneralService){

    fun getPasswordEncrypt(password: String): Single<PasswordEncryptResponse> =
            api.getPasswordEncrypt(password)

    fun getLogin(userLoginRequest: UserLoginRequest): Single<UserLoginResponse> =
            api.getLogin(userLoginRequest)

    fun getChangePassword(id: String, password: ChangePasswordRequest): Single<ChangePasswordResponse> =
            api.getChangePassword(id, password)

    fun getSalesLogin(userLoginRequest: UserLoginRequest): Single<SalesLoginResponse> =
            api.getSalesLogin(userLoginRequest)

    fun getCustomerList(id: String): Single<CustomerListResponse> =
            api.getSalesCustomerList(id)

    fun getTrips(driverId: String, offset: String, limit: String, status: String): Single<TripsResponse> =
            api.getTrips(driverId, offset, limit, status)

    fun getSingleTrip(driverId: String, tripId: String, offset: String, limit: String, status: String): Single<ViewDeliveryTripResponse> =
            api.getSingleTrip(driverId, tripId, offset, limit, status)

    fun getDestination(driverID: String, tripID: String, type: String, docID: String): Single<DestinationResponse> =
            api.getDestination(driverID,tripID,type,docID)

    fun updateDocument(driverID: String, tripID: String, type: String, docID: UpdateDocumentRequest): Single<UpdateDocumentResponse> =
            api.updateDocumentTrip(driverID,tripID,type,docID)

    fun uploadPhoto(driverID: String, tripID: String, type: String, uploadPhoto: UploadPhotoRequest): Single<UploadPhotoResponse> =
            api.uploadPhoto(driverID, tripID, type, uploadPhoto)
}