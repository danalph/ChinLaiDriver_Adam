package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Addam on 21/1/2019.
 */
data class UpdateDocumentRequest (
    @SerializedName("id")
    @Expose
    var id: String
)