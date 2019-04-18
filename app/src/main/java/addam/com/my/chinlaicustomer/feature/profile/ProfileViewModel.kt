package addam.com.my.chinlaicustomer.feature.profile

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean

/**
 * Created by Addam on 14/1/2019.
 */
class ProfileViewModel(
    private val databaseRepository: DatabaseRepository,
    private val appPreference: AppPreference) : ViewModel(){


    var name = ObservableString("")
    var contact = ObservableString("")
    var address = ObservableString("")
    var password = ObservableString("********")

    var isSalesPerson = ObservableBoolean(false)

    val startActivityEvent: StartActivityEvent = StartActivityEvent()

    fun onEditPasswordClicked(){
        startActivityEvent.value = StartActivityModel(Router.Destination.RESET_PASSWORD, hasResults = false, clearHistory = false)
    }

}