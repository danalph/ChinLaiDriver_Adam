package addam.com.my.chinlaicustomer.rest.model

import android.graphics.drawable.Drawable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("status")
    @Expose
    val status: Boolean = false,
    @SerializedName("message")
    @Expose
    val message: String? = null,
    @SerializedName("data")
    @Expose
    val data: ProductData? = null
)

data class ProductData(
    @SerializedName("products")
    @Expose
    val productList: List<ProductList>
)

data class ProductList(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("nameEn")
    @Expose
    val nameEn: String,
    @SerializedName("nameCn")
    @Expose
    val nameCn: String,
    @SerializedName("price")
    @Expose
    val price: String,
    @SerializedName("image")
    @Expose
    val image: Drawable?
)