package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class CreateOrderResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("SO")
        val sO: SO
    ) {
        data class SO(
            @SerializedName("created")
            val created: String,
            @SerializedName("docNum")
            val docNum: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("status")
            val status: String,
            @SerializedName("total_items")
            val totalItems: String
        )
    }
}