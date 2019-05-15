package addam.com.my.chinlaicustomer.rest.model.salesitemhistory

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 07/05/2019
 */
data class SearchProductHistoryResponse (
    @SerializedName("status")
    @Expose
    var status: Boolean,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("data")
    @Expose
    var data: Data
)

data class Data(
    @SerializedName("products")
    @Expose
    var products: List<Product>,
    
    @SerializedName("filters")
    @Expose
    var filters: String,
    
    @SerializedName("total")
    @Expose
    var total: Int
)

data class Product(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("code")
    @Expose
    var code: String,

    @SerializedName("description_1")
    @Expose
    var description_1: String,

    @SerializedName("product_images")
    @Expose
    var product_images: List<String>
)