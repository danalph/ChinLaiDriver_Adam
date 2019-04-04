package addam.com.my.chinlaicustomer.rest.model.deliverydetails

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.utilities.StatusHelper
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class OrderDeliveryStatusResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
) {
    data class Data(
        @SerializedName("DO")
        var dO: DO?,
        @SerializedName("POD")
        var pOD: List<POD>
    ) {
        @Parcelize
        data class POD(
            @SerializedName("action")
            var action: String?,
            @SerializedName("created_on")
            var createdOn: String?,
            @SerializedName("docID")
            var docID: String?,
            @SerializedName("id")
            var id: String?,
            @SerializedName("text")
            var text: String?,
            @SerializedName("userID")
            var userID: String?
        ) : Parcelable{
            fun getStatusRes(): Int{
                return when(action){
                    "create" -> R.drawable.icon_order_create
                    "packing" -> R.drawable.icon_order_packing
                    "trip" -> R.drawable.icon_order_packing
                    "update" -> R.drawable.icon_order_delivered
                    "delivered" -> R.drawable.icon_order_delivered
                    else -> R.drawable.icon_order_delivered
                }
            }
        }

        @Parcelize
        data class DO(
            @SerializedName("date")
            var date: String?,
            @SerializedName("docNum")
            var docNum: String?,
            @SerializedName("id")
            var id: String?,
            @SerializedName("status")
            var status: String?
        ) : Parcelable{

            fun getStatusText():String{
                return StatusHelper.getStatus(status!!)
            }

            fun getStatusColor(): Int{
                return StatusHelper.getStatusColor(status!!)
            }
        }

        @Parcelize
        data class ListPOD(
            var list: List<POD>
        ) : Parcelable
    }
}