package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class CategoryListResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("categories")
        val categories: List<Category>,
        @SerializedName("total")
        val total: Int
    ) {
        data class Category(
            @SerializedName("description")
            val description: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String
        )
    }
}