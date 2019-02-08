package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Addam on 21/1/2019.
 */
data class UploadPhotoResponse (
    @SerializedName("status")
    @Expose
    val status: Boolean,

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("images")
    @Expose
    val images: List<ResponseImage>
)

data class ResponseImage(
    @SerializedName("path")
    @Expose
    val path: String,

    @SerializedName("create_on")
    @Expose
    val createOn: String
)