package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 22/03/2019
 */
data class SalesLoginResponse (
    @SerializedName("status")
    @Expose
    var status: Boolean,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("data")
    @Expose
    var data: SalesData
)

data class SalesData(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("firstname")
    @Expose
    var firstName: String,

    @SerializedName("lastname")
    @Expose
    var lastName: String? = "",

    @SerializedName("contact")
    @Expose
    var contact: String? = "",

    @SerializedName("address")
    @Expose
    var address: String
)