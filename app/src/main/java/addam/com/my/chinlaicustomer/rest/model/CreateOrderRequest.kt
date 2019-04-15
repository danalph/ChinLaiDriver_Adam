package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(
    @SerializedName("branch")
    val branch: String,
    @SerializedName("customer")
    val customer: String,
    @SerializedName("goods")
    val goods: List<Good>,
    @SerializedName("salesperson")
    val salesperson: String
) {
    data class Good(
        @SerializedName("good_id")
        val goodId: String,
        @SerializedName("good_quantity")
        val goodQty: String
    )
}