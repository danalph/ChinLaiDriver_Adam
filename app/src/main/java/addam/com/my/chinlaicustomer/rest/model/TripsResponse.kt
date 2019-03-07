package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class TripsResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("categories")
        val trips: List<Trip>
    ) {
        data class Trip(
            @SerializedName("id")
            val id: String,
            @SerializedName("category_en")
            val categoryEn: String,
            @SerializedName("category_cn")
            val categoryCn: String
        )
    }
}