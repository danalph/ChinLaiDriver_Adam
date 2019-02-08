package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by Addam on 21/1/2019.
 */
data class UpdateDocumentResponse (
    @SerializedName("status")
    @Expose
    val status: Boolean? = null,
    @SerializedName("message")
    @Expose
    val message: String? = null,
    @SerializedName("data")
    @Expose
    val data: DocumentData? = null
)

data class DocumentData(
    @SerializedName("info")
    @Expose
    val info: DocumentInfo? = null
)

data class DocumentInfo(
    @SerializedName("id")
    @Expose
    val id: String? = null,
    @SerializedName("type")
    @Expose
    val type: String? = null,
    @SerializedName("docNum")
    @Expose
    val docNum: String? = null,
    @SerializedName("date")
    @Expose
    val date: String? = null,
    @SerializedName("company")
    @Expose
    val company: String? = null,
    @SerializedName("address")
    @Expose
    val address: String? = null,
    @SerializedName("state")
    @Expose
    val state: String? = null,
    @SerializedName("area")
    @Expose
    val area: String? = null,
    @SerializedName("postcode")
    @Expose
    val postcode: String? = null,
    @SerializedName("person_in_charge")
    @Expose
    val personInCharge: String? = null,
    @SerializedName("person_contact")
    @Expose
    val personContact: String? = null,
    @SerializedName("latitude")
    @Expose
    val latitude: String? = null,
    @SerializedName("longitude")
    @Expose
    val longitude: String? = null,
    @SerializedName("total_items")
    @Expose
    val totalItems: String? = null,
    @SerializedName("total_price")
    @Expose
    val totalPrice: String? = null,
    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("sortby")
    @Expose
    val sortby: String? = null,
    @SerializedName("create_on")
    @Expose
    val createOn: String? = null
)

