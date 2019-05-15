package addam.com.my.chinlaicustomer.rest.model


import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class StatementListResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
) {
    data class Data(
        @SerializedName("statement")
        var statement: List<Statement>
    ) {
        data class Statement(
            @SerializedName("dateFrom")
            var dateFrom: String?,
            @SerializedName("dateTo")
            var dateTo: String?,
            @SerializedName("id")
            var id: String?,
            @SerializedName("newBalance")
            var newBalance: String?
        ){
            fun getMonth(): String{
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = sdf.parse(dateFrom)
                return android.text.format.DateFormat.format("MMMM", date) as String
            }
        }

    }
}