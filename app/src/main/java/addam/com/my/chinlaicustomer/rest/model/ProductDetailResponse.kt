package addam.com.my.chinlaicustomer.rest.model

import android.graphics.drawable.Drawable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("status")
    @Expose
    val status: Boolean = false,
    @SerializedName("message")
    @Expose
    val message: String? = null,
    @SerializedName("data")
    @Expose
    val data: ProductDetailData? = null
)

data class ProductDetailData(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("productName")
    @Expose
    val productName: String,
    @SerializedName("productDescription")
    @Expose
    val productDescription: String,
    @SerializedName("price")
    @Expose
    val price: String,
    @SerializedName("image")
    @Expose
    val images: ArrayList<String>?
)
