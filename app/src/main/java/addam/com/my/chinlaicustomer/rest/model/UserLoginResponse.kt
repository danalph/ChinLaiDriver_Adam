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

    @SerializedName("company_name")
    @Expose
    var name: String,

    @SerializedName("person_name")
    @Expose
    var person_name: String,

    @SerializedName("person_contact")
    @Expose
    var person_contact: String,

    @SerializedName("address1")
    @Expose
    var address1: String,

    @SerializedName("address2")
    @Expose
    var address2: String,

    @SerializedName("address3")
    @Expose
    var address3: String,

    @SerializedName("stateName")
    @Expose
    var stateName: String,

    @SerializedName("areaName")
    @Expose
    var areaName: String,

    @SerializedName("postcode")
    @Expose
    var postcode: String,

    @SerializedName("password")
    @Expose
    var password: String
)