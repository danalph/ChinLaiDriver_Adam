package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("product")
        val product: Product,
        @SerializedName("product_images")
        val productImages: List<String>?,
        @SerializedName("product_other")
        val productOther: Any?
    ) {
        data class Product(
            @SerializedName("description_1")
            val description1: String,
            @SerializedName("description_2")
            val description2: String?,
            @SerializedName("id")
            val id: String,
            @SerializedName("ref_price")
            val refPrice: String
        )
    }
}