package addam.com.my.chinlaicustomer.utilities.model

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.StatusHelper
import addam.com.my.chinlaicustomer.utilities.observables.ObservableBackground
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.ObservableBoolean

/**
 * Created by Addam on 19/1/2019.
 */
data class ViewDeliveryHeaderModel(
    var itemId: ObservableString = ObservableString(""),
    var date: ObservableString = ObservableString(""),
    var status: ObservableString = ObservableString("")
){
    fun getStatusName(): String{
        return StatusHelper.getStatus(status.get()!!)
    }

    fun getStatusColor(): Int{
        return StatusHelper.getStatusColor(status.get()!!)
    }
}