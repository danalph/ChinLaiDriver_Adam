package addam.com.my.chinlaicustomer.utilities.model

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.observables.ObservableBackground
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.ObservableBoolean

/**
 * Created by Addam on 19/1/2019.
 */
data class DestinationItemModel(
    var itemId: ObservableString = ObservableString(""),
    var isComplete: ObservableBoolean = ObservableBoolean(false),
    var date: ObservableString = ObservableString(""),
    var type: ObservableString = ObservableString("DO")
){
    val statusName = ObservableString("")
    val typeIcon = ObservableBackground()
    lateinit var listener: DestinationItemListener

    init {
        isComplete.observe().map { isComplete.get() }.subscribe{
            if(it) statusName.set("COMPLETE")
            else statusName.set("INCOMPLETE")
        }

        type.observe().map { type.get()=="DO" }.subscribe {
            if (it) typeIcon.setDrawableResource(R.drawable.ic_do)
            else typeIcon.setDrawableResource(R.drawable.ic_gt)
        }
    }

    fun onMarkCompletedClicked(){
        listener.onMarkClicked()
    }

    fun onMapViewClicked(){
        listener.onMapClicked()
    }

    fun onUploadPhotoClicked(){
        listener.onUploadClicked()
    }

    interface DestinationItemListener{
        fun onMarkClicked()
        fun onMapClicked()
        fun onUploadClicked()
    }
}