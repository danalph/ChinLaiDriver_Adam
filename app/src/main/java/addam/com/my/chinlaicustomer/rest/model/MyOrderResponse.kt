package addam.com.my.chinlaicustomer.rest.model

import addam.com.my.chinlaicustomer.R
import com.google.gson.annotations.SerializedName

data class MyOrderResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("SOs")
        val sOs: ArrayList<SO>,
        @SerializedName("total")
        val total: Int
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
        ) {
            fun getStatusText(): String{
                return when(status){
                    "1" -> "Pending"
                    "2" -> "Confirmed"
                    "3" -> "Processing"
                    "4" -> "Delivering"
                    "5" -> "Completed"
                    else -> "Unknown"
                }
            }

            fun getStatusColor(): Int{
                return when(status){
                    "1" -> R.color.grey
                    "2" -> R.color.colorYellow
                    "3" -> R.color.colorRed
                    "4" -> R.color.colorBlue
                    "5" -> R.color.colorGreen
                    else -> R.color.colorRed
                }
            }
        }
    }
}

