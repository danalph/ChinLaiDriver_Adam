package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class ViewDeliveryTripResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("documents")
        val documents: List<Document>,
        @SerializedName("trip")
        val trip: Trip
    ) {
        data class Document(
            @SerializedName("address")
            val address: String,
            @SerializedName("area")
            val area: String,
            @SerializedName("company")
            val company: String,
            @SerializedName("create_on")
            val createOn: String,
            @SerializedName("date")
            val date: String,
            @SerializedName("docNum")
            val docNum: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("latitude")
            val latitude: String,
            @SerializedName("longitude")
            val longitude: String,
            @SerializedName("person_contact")
            val personContact: String,
            @SerializedName("person_in_charge")
            val personInCharge: String,
            @SerializedName("postcode")
            val postcode: String,
            @SerializedName("sortby")
            val sortby: String,
            @SerializedName("state")
            val state: String,
            @SerializedName("status")
            val status: String,
            @SerializedName("total_items")
            val totalItems: String,
            @SerializedName("total_price")
            val totalPrice: String,
            @SerializedName("type")
            val type: String,
            var position: String
        )

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