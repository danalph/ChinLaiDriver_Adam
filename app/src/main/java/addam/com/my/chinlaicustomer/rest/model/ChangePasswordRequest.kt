package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 24/03/2019
 */
data class ChangePasswordRequest (
    @SerializedName("password")
    @Expose
    val password: String,

    @SerializedName("confirm_password")
    @Expose
    val confirmPassword: String
)