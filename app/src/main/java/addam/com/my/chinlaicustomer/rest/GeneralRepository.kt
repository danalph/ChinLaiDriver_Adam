package addam.com.my.chinlaicustomer.rest

import addam.com.my.chinlaicustomer.rest.model.*
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDeliveryDetailResponse
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDeliveryStatusResponse
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDriverDetailResponse
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

    fun getCustomerDetails(id: String): Single<CustomerDetailResponse> =
            api.getCustomerDetails(id)

    fun getCategoryList(offset: String, limit: String, field: String, sortBy: String, filter: String): Single<CategoryListResponse> =
            api.getCategoryList(offset, limit, field, sortBy, filter)

    fun getProductList(id: String, offset: String, limit: String, field: String, sortBy: String, filter: String): Single<ProductListResponse> =
            api.getProductList(id, offset, limit, field, sortBy, filter)

    fun getProductDetail(id: String): Single<ProductDetailResponse> = api.getProductDetail(id)

    fun getInvoiceList(id:String): Single<InvoiceListResponse> = api.getInvoiceList(id)

    fun getInvoiceDetails(id: String): Single<InvoiceDetailsResponse> = api.getInvoiceDetails(id)

    fun getDestination(driverID: String, tripID: String, type: String, docID: String): Single<DestinationResponse> =
            api.getDestination(driverID,tripID,type,docID)

    fun updateDocument(driverID: String, tripID: String, type: String, docID: UpdateDocumentRequest): Single<UpdateDocumentResponse> =
            api.updateDocumentTrip(driverID,tripID,type,docID)

    fun uploadPhoto(driverID: String, tripID: String, type: String, uploadPhoto: UploadPhotoRequest): Single<UploadPhotoResponse> =
            api.uploadPhoto(driverID, tripID, type, uploadPhoto)

    fun getBranches(customerId: String): Single<BranchesResponse> = api.getBranches(customerId)

    fun createOrder(createOrderRequest: CreateOrderRequest): Single<CreateOrderResponse> = api.createOrder(createOrderRequest)

    fun getOrder(customerId: String, offset: String, limit: String, field: String, sortBy: String, filter: String): Single<MyOrderResponse> =
            api.getOrder(customerId, offset, limit, field, sortBy, filter)

    fun getOrderDetail(orderId: String): Single<OrderDeliveryDetailResponse> = api.getOrderDetail(orderId)

    fun getOrderDriverDetail(orderId: String): Single<OrderDriverDetailResponse> = api.getOrderDriverDetail(orderId)

    fun getOrderDeliveryStatus(orderId: String): Single<OrderDeliveryStatusResponse> = api.getOrderDeliveryStatus(orderId)
}