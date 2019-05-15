package addam.com.my.chinlaicustomer.rest.model


import com.google.gson.annotations.SerializedName

data class StatementResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
) {
    data class Data(
        @SerializedName("statement")
        var statement: Statement?
    ) {
        data class Statement(
            @SerializedName("id")
            var id: String?,
            @SerializedName("pdf")
            var pdf: String?
        )
    }
}