package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
) {
    data class Data(
        @SerializedName("product")
        var product: Product?,
        @SerializedName("product_images")
        var productImages: List<ProductImage?>?,
        @SerializedName("product_other")
        var productOther: ProductOther?
    ) {
        data class Product(
            @SerializedName("description_1")
            var description1: String?,
            @SerializedName("description_2")
            var description2: String?,
            @SerializedName("id")
            var id: String?,
            @SerializedName("ref_price")
            var refPrice: String?
        )

        data class ProductImage(
            @SerializedName("description")
            var description: String?,
            @SerializedName("goodID")
            var goodID: String?,
            @SerializedName("id")
            var id: String?,
            @SerializedName("path")
            var path: String?
        )

        data class ProductOther(
            @SerializedName("description")
            var description: String?
        )
    }
}