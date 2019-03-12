package addam.com.my.chinlaicustomer.feature.delivery

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.TripsResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Firdaus on 14/1/2019.
 */

class DashboardViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    val startActivityEvent: StartActivityEvent = StartActivityEvent()
    var tripCount = ObservableInt(0)
    val tripResponse: MutableLiveData<TripsResponse> = MutableLiveData()
    val errorResponse: MutableLiveData<Int>  = MutableLiveData()
    var name = ObservableString("")
    init {
        name.set(appPreference.getUser().name)
        getTrips()
    }

    fun getTrips(){
        /*generalRepository.getTrips(appPreference.getUser().id,"0", "20", "all")
            .compose(schedulerProvider.getSchedulersForSingle()).subscribeBy(
                onSuccess = {
                    if (it.status){
                        tripResponse.postValue(it)
                    }else{
                        errorResponse.postValue(R.string.error_getting_response)
                    }
                }, onError = {
                    errorResponse.postValue(R.string.error_getting_response)
                })*/
        val item = TripsResponse.Data.Trip("1", "Adhesive, Glue & Tape", "胶粘剂，胶水和胶带")
        val categories = arrayListOf<TripsResponse.Data.Trip>()
        for (i in 1..10){
            categories.add(item)
        }

        val data = TripsResponse.Data(categories)
        val trip = TripsResponse(data,"", false)
        tripResponse.postValue(trip)
    }

    fun startProductListActivity(tripId: String){
        startActivityEvent.value = StartActivityModel(Router.Destination.PRODUCT,
                hashMapOf(Pair(Router.Parameter.TRIP_ID, tripId)), hasResults = false, clearHistory = false)
    }
}