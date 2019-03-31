package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 25/03/2019
 */
data class InvoiceListResponse(
    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("data")
    @Expose
    var data: List<Invoices>
)

data class Invoices(
    @SerializedName("id")
    @Expose
    var id: String = "",

    @SerializedName("date")
    @Expose
    var date: String = "",

    @SerializedName("amount")
    @Expose
    var amount: String = "",

    @SerializedName("status")
    @Expose
    var status: String = ""
){
    fun isPaid(): Boolean{
        return status=="1"
    }
}