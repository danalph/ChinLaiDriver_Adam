package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class SalesOrderDetailResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
) {
    data class Data(
        @SerializedName("SO")
        var sO: SO?,
        @SerializedName("items")
        var items: ArrayList<Item>
    ) {
        data class SO(
            @SerializedName("billing_address")
            var billingAddress: String?,
            @SerializedName("company_name")
            var companyName: String?,
            @SerializedName("customer_code")
            var customerCode: String?,
            @SerializedName("customer_credit_term")
            var customerCreditTerm: String?,
            @SerializedName("do_no")
            var doNo: String?,
            @SerializedName("docNum")
            var docNum: String?,
            @SerializedName("id")
            var id: String?,
            @SerializedName("roc")
            var roc: String?,
            @SerializedName("salesperson")
            var salesperson: String?,
            @SerializedName("status")
            var status: String?,
            @SerializedName("total_amount")
            var totalAmount: String?
        )

        data class Item(
            @SerializedName("amount")
            var amount: Double?,
            @SerializedName("discount")
            var discount: String?,
            @SerializedName("id")
            var id: String?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("item_desc1")
            var itemDesc1: String?,
            @SerializedName("item_desc2")
            var itemDesc2: String?,
            @SerializedName("unit")
            var unit: String?,
            @SerializedName("unit_price")
            var unitPrice: String?,
            @SerializedName("uom")
            var uom: String?
        ){
            fun formatPrice(): String{
                val format = DecimalFormat("#0.00")
                return format.format(unitPrice?.toDouble())
            }

            fun formatAmount(): String{
                val format = DecimalFormat("#0.00")
                return format.format(amount)
            }
        }
    }
}