package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class BranchesResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("customer_branches")
        val customerBranches: List<CustomerBranches>,
        @SerializedName("total")
        val total: Int
    ) {
        data class CustomerBranches(
            @SerializedName("address_1")
            val address1: String,
            @SerializedName("address_2")
            val address2: String,
            @SerializedName("address_3")
            val address3: String,
            @SerializedName("areaName")
            val areaName: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("postcode")
            val postcode: String,
            @SerializedName("stateName")
            val stateName: String
        )
    }
}