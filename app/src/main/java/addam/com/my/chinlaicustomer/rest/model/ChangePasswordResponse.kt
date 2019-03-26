package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 24/03/2019
 */
data class ChangePasswordResponse (
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: Customers
)