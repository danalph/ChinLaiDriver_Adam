package addam.com.my.chinlaicustomer.feature.profile

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel

/**
 * Created by Addam on 14/1/2019.
 */
class ProfileViewModel(
    private val databaseRepository: DatabaseRepository,
    private val appPreference: AppPreference) : ViewModel(){


    var name = ObservableString("BALA")
    var contact = ObservableString("010-471123932")
    var address = ObservableString("18-01, Tower B, Vertical Business Suite, Jalan Kerinchi, Bangsar South, 59200 Kuala Lumpur, Federal Territory of Kuala Lumpur")
    var password = ObservableString("********")
    var salesPerson = ObservableString("")
    var salesPersonContact = ObservableString("")

    val startActivityEvent: StartActivityEvent = StartActivityEvent()

    fun onEditPasswordClicked(){
        startActivityEvent.value = StartActivityModel(Router.Destination.RESET_PASSWORD, hasResults = false, clearHistory = false)
    }

}