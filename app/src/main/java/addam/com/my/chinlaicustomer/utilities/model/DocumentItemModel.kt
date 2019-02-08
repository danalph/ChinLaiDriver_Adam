package addam.com.my.chinlaicustomer.utilities.model

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.observables.ObservableBackground
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.ObservableBoolean

/**
 * Created by Addam on 16/1/2019.
 */
data class DocumentItemModel(
    var itemId:ObservableString = ObservableString(""),
    var isComplete:ObservableBoolean = ObservableBoolean(false),
    var address: ObservableString = ObservableString(""),
    var type: ObservableString = ObservableString("")
){
    val statusBackground = ObservableBackground()
    val statusName = ObservableString("")
    val typeIcon = ObservableBackground()

    init {
        if(isComplete.get()){
            statusBackground.setColorResource(R.color.colorGreen)
            statusName.set("COMPLETE")
        }else {
            statusBackground.setColorResource(R.color.colorRed)
            statusName.set("INCOMPLETE")
        }

        isComplete.observe().map { isComplete.get() }.subscribe{
            if(it){
                statusBackground.setColorResource(R.color.colorGreen)
                statusName.set("COMPLETE")
            }else{
                statusBackground.setColorResource(R.color.colorRed)
                statusName.set("INCOMPLETE")
            }
        }

        type.observe().map { type.get()=="DO" }.subscribe {
            if(it)typeIcon.setDrawableResource(R.drawable.ic_do)
            else typeIcon.setDrawableResource(R.drawable.ic_gt)
        }
    }
}