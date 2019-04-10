package addam.com.my.chinlaicustomer.feature.error

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import android.arch.lifecycle.ViewModel

class ErrorViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository):ViewModel(){

    val startActivityEvent: StartActivityEvent = StartActivityEvent()

    fun onHomeClick(){
        startActivityEvent.value = StartActivityModel(Router.Destination.DASHBOARD, hasResults = false, clearHistory = true)
    }

}