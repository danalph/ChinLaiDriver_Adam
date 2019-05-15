package addam.com.my.chinlaicustomer.rest.model.salesitemhistory

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by owner on 07/05/2019
 */
data class ProductHistoryRequest (
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("from")
    @Expose
    var from: String,

    @SerializedName("to")
    @Expose
    var to: String
)