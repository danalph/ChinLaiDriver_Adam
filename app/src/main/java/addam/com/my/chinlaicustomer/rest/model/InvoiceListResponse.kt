package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 25/03/2019
 */
data class InvoiceListResponse(
    @SerializedName("status")
    @Expose
    var status: Boolean,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("data")
    @Expose
    var data: Invs
)

data class Invs(
    @SerializedName("INVs")
    @Expose
    var INVs: List<Invoices>
)

data class Invoices(
    @SerializedName("id")
    @Expose
    var id: String = "",

    @SerializedName("docNum")
    @Expose
    var docNum: String,

    @SerializedName("created")
    @Expose
    var date: String = "",

    @SerializedName("total_amount")
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