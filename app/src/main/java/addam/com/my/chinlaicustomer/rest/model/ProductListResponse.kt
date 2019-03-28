package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("products")
        val products: List<Product>,
        @SerializedName("total")
        val total: Int
    ) {
        data class Product(
            @SerializedName("description_1")
            val description1: String,
            @SerializedName("description_2")
            val description2: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("product_images")
            val productImages: List<String>,
            @SerializedName("ref_price")
            val refPrice: String
        )
    }
}