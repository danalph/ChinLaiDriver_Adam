package addam.com.my.chinlaicustomer.rest.model.deliverydetails

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class OrderDeliveryDetailResponse(
    @SerializedName("data")
    var data: OrderDeliveryData,
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Boolean
){
    @Parcelize
    data class OrderDeliveryData(
        @SerializedName("DO")
        var dO: DO?
    ) : Parcelable {
        @Parcelize
        data class DO(
            @SerializedName("cbranch_address")
            var cbranchAddress: String,
            @SerializedName("customer_contact")
            var customerContact: String,
            @SerializedName("customer_name")
            var customerName: String,
            @SerializedName("date")
            var date: String,
            @SerializedName("docNum")
            var docNum: String,
            @SerializedName("id")
            var id: String,
            @SerializedName("status")
            var status: String,
            @SerializedName("total_items")
            var totalItems: String,
            @SerializedName("type")
            var type: String
        ) : Parcelable
    }
}

