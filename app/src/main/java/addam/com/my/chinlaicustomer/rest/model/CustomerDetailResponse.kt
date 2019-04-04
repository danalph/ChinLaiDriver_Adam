package addam.com.my.chinlaicustomer.rest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Addam on 30/03/2019
 */
data class CustomerDetailResponse (
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val `data`: Data
){
    data class Data(
        @SerializedName("customer")
        val customer: Customer,

        @SerializedName("customer_branches")
        val customerBranches: List<CustomerDetailBranches>
    ){
        data class Customer(
          @SerializedName("customer_id")
          @Expose
          var id: String,

          @SerializedName("customer_company_name")
          @Expose
          var customer_company_name: String,

          @SerializedName("customer_code")
          @Expose
          var customer_code: String,

          @SerializedName("customer_credit_term")
          @Expose
          var customer_credit_term: String,

          @SerializedName("customer_credit_limit")
          @Expose
          var customer_credit_limit: String,

          @SerializedName("salesperson_id")
          @Expose
          var salesperson_id: String,

          @SerializedName("user_firstname")
          @Expose
          var user_firstname: String,

          @SerializedName("user_lastname")
          @Expose
          var user_lastname: String,

          @SerializedName("user_contact")
          @Expose
          var user_contact: String
        )

        data class CustomerDetailBranches (
            @SerializedName("id")
            @Expose
            var id: String,

            @SerializedName("name")
            @Expose
            var name: String,

            @SerializedName("address_1")
            @Expose
            var address1: String,

            @SerializedName("address_2")
            @Expose
            var address2: String,

            @SerializedName("address_3")
            @Expose
            var address3: String,

            @SerializedName("stateID")
            @Expose
            var stateID: String,

            @SerializedName("stateName")
            @Expose
            var stateName: String,

            @SerializedName("areaID")
            @Expose
            var areaID: String,

            @SerializedName("areaName")
            @Expose
            var areaName: String,

            @SerializedName("postcode")
            @Expose
            var postcode: String,

            @SerializedName("contact")
            @Expose
            var contact: String = ""

        )

    }
}