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
            fun getStatusColor(): Int{
                return when(status){
                    "pending" -> R.color.grey
                    "confirmed" -> R.color.colorYellow
                    "processing" -> R.color.colorLightRed
                    "delivering" -> R.color.colorLightBlue
                    "completed" -> R.color.colorBlue
                    "unknown" -> R.color.colorGreen
                    else -> R.color.colorRed
                }
            }
        }
    }
}

