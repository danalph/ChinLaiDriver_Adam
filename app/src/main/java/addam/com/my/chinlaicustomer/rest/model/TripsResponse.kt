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
        @SerializedName("trips")
        val trips: List<Trip>
    ) {
        data class Trip(
            @SerializedName("create_on")
            val createOn: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("status")
            val status: String,
            @SerializedName("total_do")
            val totalDo: String,
            @SerializedName("total_gt")
            val totalGt: String,
            @SerializedName("vehicleModel")
            val vehicleModel: String,
            @SerializedName("vehiclePlate")
            val vehiclePlate: String
        )
    }
}