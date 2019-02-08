package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Addam on 12/1/2019.
 */
data class UserLoginResponse (
    @SerializedName("status")
    @Expose
    var status: Boolean,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("data")
    @Expose
    var data: UserData
)

data class UserData(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("contact")
    @Expose
    var contact: String,

    @SerializedName("identity")
    @Expose
    var identity: String,

    @SerializedName("address")
    @Expose
    var address: String,

    @SerializedName("remark")
    @Expose
    var remark: String,

    @SerializedName("status")
    @Expose
    var status: String
)