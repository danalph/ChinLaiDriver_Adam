package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 04/04/2019
 */
data class InvoiceDetailsResponse (
    @SerializedName("status")
    @Expose
    var status: Boolean,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("data")
    @Expose
    var data: InvoiceData
)

data class InvoiceData(
    @SerializedName("INV")
    @Expose
    var invoiceDetails: InvoiceDetail,

    @SerializedName("items")
    @Expose
    var items: List<InvoiceItem>
)

data class InvoiceDetail(
    @SerializedName("id")
    @Expose
    var  id: String,
    @SerializedName("docNum")
    @Expose
    var  docNum: String,
    @SerializedName("date")
    @Expose
    var  date: String,
    @SerializedName("status")
    @Expose
    var  status: String,
    @SerializedName("company")
    @Expose
    var  company: String,
    @SerializedName("roc")
    @Expose
    var  roc: String,
    @SerializedName("code")
    @Expose
    var  code: String,
    @SerializedName("salesperson")
    @Expose
    var  salesperson: String,
    @SerializedName("doNum")
    @Expose
    var  doNum: String,
    @SerializedName("credit_term")
    @Expose
    var  creditTerm: String,
    @SerializedName("billing_address")
    @Expose
    var  billingAddress: String,
    @SerializedName("total_amount")
    @Expose
    var  totalAmount: String
)

data class InvoiceItem(
    @SerializedName("id")
    @Expose
    var  id: String,
    @SerializedName("description_1")
    @Expose
    var  description1: String,
    @SerializedName("description_2")
    @Expose
    var  description2: String,
    @SerializedName("unit_price")
    @Expose
    var  unitPrice: String,
    @SerializedName("discount")
    @Expose
    var  discount: String,
    @SerializedName("unit")
    @Expose
    var  unit: String,
    @SerializedName("uom")
    @Expose
    var  uom: String,
    @SerializedName("amount")
    @Expose
    var  amount: String,
    @SerializedName("image")
    @Expose
    var  image: String

)