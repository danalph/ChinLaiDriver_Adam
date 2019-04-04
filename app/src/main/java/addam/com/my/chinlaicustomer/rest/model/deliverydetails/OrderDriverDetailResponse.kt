package addam.com.my.chinlaicustomer.rest.model.deliverydetails

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class OrderDriverDetailResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
) {
    data class Data(
        @SerializedName("DO")
        var dO: DO?
    ) {
        @Parcelize
        data class DO(
            @SerializedName("date")
            var date: String?,
            @SerializedName("docNum")
            var docNum: String?,
            @SerializedName("driver_address")
            var driverAddress: String?,
            @SerializedName("driver_contact")
            var driverContact: String?,
            @SerializedName("driver_ic")
            var driverIc: String?,
            @SerializedName("driver_name")
            var driverName: String?,
            @SerializedName("id")
            var id: String?,
            @SerializedName("status")
            var status: String?,
            @SerializedName("vehicle_model")
            var vehicleModel: String?,
            @SerializedName("vehicle_plate")
            var vehiclePlate: String?
        ) : Parcelable
    }
}