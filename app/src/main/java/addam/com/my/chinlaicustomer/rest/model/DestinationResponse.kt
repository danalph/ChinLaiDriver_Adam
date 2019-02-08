package addam.com.my.chinlaicustomer.rest.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by Addam on 20/1/2019.
 */
data class DestinationResponse (
    @SerializedName("status")
    @Expose
    val status: Boolean? = null,
    @SerializedName("message")
    @Expose
    val message: String? = null,
    @SerializedName("data")
    @Expose
    val data: DestinationData? = null
)

data class DestinationData(
    @SerializedName("info")
    @Expose
    val info: Info? = null,
    @SerializedName("particulars")
    @Expose
    val particulars: List<Particular>? = null,
    @SerializedName("images")
    @Expose
    val images: List<Image>? = null
)

@Parcelize
data class Info(
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
) : Parcelable

@Parcelize
data class Particular(
    @SerializedName("goodLoose")
    @Expose
    val goodLoose: String? = null,
    @SerializedName("goodCTN")
    @Expose
    val goodCTN: String? = null,
    @SerializedName("code")
    @Expose
    val code: String? = null,
    @SerializedName("description_1")
    @Expose
    val description1: String? = null,
    @SerializedName("description_2")
    @Expose
    val description2: String? = null,
    @SerializedName("remark_1")
    @Expose
    val remark1: String? = null,
    @SerializedName("sales_price")
    @Expose
    val salesPrice: String? = null,
    @SerializedName("quantity")
    @Expose
    val quantity: String? = null,
    @SerializedName("discount")
    @Expose
    val discount: String? = null,
    @SerializedName("uom")
    @Expose
    val uom: String? = null,
    @SerializedName("total_prices")
    @Expose
    val totalPrice: Double? = null
) : Parcelable

@Parcelize
data class Image(
    @SerializedName("path")
    @Expose
    val path: String? = null,
    @SerializedName("create_on")
    @Expose
    val createOn: String? = null
) : Parcelable

@Parcelize
data class ListParticulars(
    val list: List<Particular>
): Parcelable

@Parcelize
data class ListImages(
    val list: List<Image>
): Parcelable
