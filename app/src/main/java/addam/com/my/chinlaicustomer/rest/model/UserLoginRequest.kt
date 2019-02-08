package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Addam on 12/1/2019.
 */
data class UserLoginRequest(
    @SerializedName("username")
    @Expose
    var username: String,

    @SerializedName("password")
    @Expose
    var password: String
)