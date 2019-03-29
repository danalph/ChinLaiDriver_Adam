package addam.com.my.chinlaicustomer.utilities.model

import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.observables.ObservableBackground
import android.view.View

data class ToolbarWithBackButtonModel(val title: String = "",
                                      val hasBack: Boolean = true,
                                      val hasButton: Boolean = false,
                                      val imageDrawable: Int,
                                      val btnCallback: () -> Unit = {},
                                      val callback: () -> Unit = {}) {

    val image = ObservableBackground()
    val txt = ObservableString("")

    init {
        image.setDrawableResource(imageDrawable)
        txt.set(title)
    }

    fun getButtonVisibility(): Int = if (hasButton) View.VISIBLE else View.GONE

    fun getBackVisibility(): Int = if (hasBack) View.VISIBLE else View.GONE
}