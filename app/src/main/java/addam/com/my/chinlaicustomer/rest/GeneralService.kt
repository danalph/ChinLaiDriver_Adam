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
    fun getCategoryList(@Query("offset") offset: String,
                 @Query("limit") limit: String,
                 @Query("field") field: String,
                 @Query("sortby") sortby: String,
                 @Query("filters") filters: String): Single<CategoryListResponse>

    @GET("mobile/products/{id}/")
    fun getProductList(@Path("id") id: String,
                      @Query("offset") offset: String,
                      @Query("limit") limit: String,
                      @Query("field") field: String,
                      @Query("sortby") sortby: String,
                      @Query("filters") filters: String): Single<ProductListResponse>

    @GET("mobile/product/{id}")
    fun getProductDetail(@Path("id") id: String ): Single<ProductDetailResponse>

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

    @GET("mobile/customer/{customer_id}/branches")
    fun getBranches(@Path("customer_id")customerId: String): Single<BranchesResponse>

    @POST("mobile/order")
    fun createOrder(@Body createOrderRequest: CreateOrderRequest): Single<CreateOrderResponse>
}