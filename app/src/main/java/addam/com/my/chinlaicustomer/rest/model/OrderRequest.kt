package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("address")
    val address: String,
    @SerializedName("agent")
    val agent: String,
    @SerializedName("areaID")
    val areaID: String,
    @SerializedName("branchID")
    val branchID: String,
    @SerializedName("branch_address")
    val branchAddress: String,
    @SerializedName("branch_name")
    val branchName: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("create_by")
    val createBy: String,
    @SerializedName("credit_limit")
    val creditLimit: String,
    @SerializedName("customerID")
    val customerID: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("goods")
    val goods: List<Good>,
    @SerializedName("inputpicker-1")
    val inputpicker1: String,
    @SerializedName("inputpicker-2")
    val inputpicker2: String,
    @SerializedName("inputpicker-3")
    val inputpicker3: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("reference")
    val reference: String,
    @SerializedName("salesperson")
    val salesperson: String,
    @SerializedName("soNum")
    val soNum: String,
    @SerializedName("stateID")
    val stateID: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("total_price")
    val totalPrice: String
) {
    data class Good(
        @SerializedName("code")
        val code: String,
        @SerializedName("desc1")
        val desc1: String,
        @SerializedName("desc2")
        val desc2: String,
        @SerializedName("discount")
        val discount: String,
        @SerializedName("good_id")
        val goodId: String,
        @SerializedName("note")
        val note: String,
        @SerializedName("packing_1")
        val packing1: String,
        @SerializedName("packing_2")
        val packing2: String,
        @SerializedName("quantity")
        val quantity: String,
        @SerializedName("remark2")
        val remark2: String,
        @SerializedName("sale_price")
        val salePrice: String,
        @SerializedName("uom")
        val uom: String
    )
}