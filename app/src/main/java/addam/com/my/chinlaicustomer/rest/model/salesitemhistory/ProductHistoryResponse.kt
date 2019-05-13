package addam.com.my.chinlaicustomer.rest.model.salesitemhistory

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 07/05/2019
 */
data class ProductHistoryResponse (
    @SerializedName("status")
    @Expose
    var status: Boolean,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("data")
    @Expose
    var data: Items
)

data class Items(
    @SerializedName("items")
    @Expose
    var items: List<Item>
)

data class Item(
    @SerializedName("date")
    @Expose
    var date: String,

    @SerializedName("docNum")
    @Expose
    var docNum: String,

    @SerializedName("sales_price")
    @Expose
    var sales_price: String,

    @SerializedName("quantity")
    @Expose
    var quantity: String,

    @SerializedName("discount")
    @Expose
    var discount: String,

    @SerializedName("uom")
    @Expose
    var uom: String,

    @SerializedName("total")
    @Expose
    var total: Double
)