package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 23/03/2019
 */
data class CustomerListResponse (
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: CustomerListData
)

data class CustomerListData(
    @SerializedName("customers")
    var customers: List<Customers>,

    @SerializedName("total")
    val total: Int
)

data class Customers(
    @SerializedName("id")
    var id: String,

    @SerializedName("company_name")
    var companyName: String,

    @SerializedName("roc")
    var roc: String
)