package addam.com.my.chinlaicustomer.rest

import addam.com.my.chinlaicustomer.rest.model.*
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDeliveryDetailResponse
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDeliveryStatusResponse
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDriverDetailResponse
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.ProductHistoryRequest
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.ProductHistoryResponse
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.SearchProductHistoryResponse
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by Addam on 7/1/2019.
 */
interface GeneralService {
    @GET("auth/encryption/{password}")
    fun getPasswordEncrypt(@Path("password") password: String): Single<PasswordEncryptResponse>

    @POST("mobile/auth/customer/login")
    fun getLogin(@Body userLoginRequest: UserLoginRequest): Single<UserLoginResponse>

    @POST("mobile/auth/salesperson/login")
    fun getSalesLogin(@Body userLoginRequest: UserLoginRequest): Single<SalesLoginResponse>

    @GET("mobile/supplier/{id}/customers")
    fun getSalesCustomerList(@Path("id") id: String): Single<CustomerListResponse>

    @GET("mobile/customer/{id}")
    fun getCustomerDetails(@Path("id") id: String): Single<CustomerDetailResponse>

    @PUT("mobile/customer/{id}/password")
    fun getChangePassword(@Path("id") id: String, @Body changePasswordRequest: ChangePasswordRequest): Single<ChangePasswordResponse>

    @GET("mobile/category")
    fun getCategoryList(): Single<CategoryListResponse>

    @GET("mobile/products/{id}/")
    fun getProductList(@Path("id") id: String,
                      @Query("offset") offset: String,
                      @Query("limit") limit: String,
                      @Query("field") field: String,
                      @Query("sortby") sortby: String,
                      @Query("filters") filters: String): Single<ProductListResponse>

    @GET("mobile/product/{id}")
    fun getProductDetail(@Path("id") id: String ): Single<ProductDetailsResponse>

    @GET("mobile/customer/{id}/invoices")
    fun getInvoiceList(@Path("id") id: String): Single<InvoiceListResponse>

    @GET("mobile/invoice/{id}")
    fun getInvoiceDetails(@Path("id") id: String): Single<InvoiceDetailsResponse>

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

    @GET("mobile/customer/{customerId}/branches")
    fun getBranches(@Path("customerId")customerId: String): Single<BranchesResponse>

    @POST("mobile/order")
    fun createOrder(@Body createOrderRequest: CreateOrderRequest): Single<CreateOrderResponse>

    @GET("mobile/customer/{customerId}/orders/")
    fun getOrder(@Path("customerId") customerId: String,
                 @Query("offset") offset: String,
                 @Query("limit") limit: String,
                 @Query("status") status: String): Single<MyOrderResponse>

    @GET("mobile/deliver_order/{orderId}")
    fun getOrderDetail(@Path("orderId") orderId: String): Single<OrderDeliveryDetailResponse>

    @GET("mobile/deliver_order/{orderId}/driver")
    fun getOrderDriverDetail(@Path("orderId") orderId: String): Single<OrderDriverDetailResponse>

    @GET("mobile/deliver_order/{orderId}/pod")
    fun getOrderDeliveryStatus(@Path("orderId") orderId: String): Single<OrderDeliveryStatusResponse>

    @GET("mobile/order/{orderId}")
    fun getSalesOrderDetail(@Path("orderId") orderId: String): Single<SalesOrderDetailResponse>

    @GET("mobile/products/")
    fun getSearchProduct(
        @Query("filters") filter: String
    ): Single<SearchProductHistoryResponse>

    @POST("mobile/product_history")
    fun getProductHistory(@Body productHistoryRequest: ProductHistoryRequest): Single<ProductHistoryResponse>

    @GET("mobile/customer/{customerId}/statements")
    fun getStatementList(@Path("customerId") customerId: String): Single<StatementListResponse>

    @GET("mobile/statement/{statementId}")
    fun getStatement(@Path("statementId") statementId: String): Single<StatementResponse>
}