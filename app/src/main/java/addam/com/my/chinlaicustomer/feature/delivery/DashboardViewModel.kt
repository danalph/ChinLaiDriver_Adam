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
        generalRepository.getTrips(appPreference.getUser().id,"0", "20", "all")
            .compose(schedulerProvider.getSchedulersForSingle()).subscribeBy(
                onSuccess = {
                    if (it.status){
                        tripResponse.postValue(it)
                    }else{
                        errorResponse.postValue(R.string.error_getting_response)
                    }
                }, onError = {
                    errorResponse.postValue(R.string.error_getting_response)
                })
    }

    fun startDeliveryDetailActivity(tripId: String){
        startActivityEvent.value = StartActivityModel(Router.Destination.DELIVERY_DETAIL,
                hashMapOf(Pair(Router.Parameter.TRIP_ID, tripId)), hasResults = false, clearHistory = false)
    }

    fun getSortedItem(text: CharSequence?, trips: ArrayList<TripsResponse.Data.Trip>): ArrayList<TripsResponse.Data.Trip> {
        var filteredItem = ArrayList<TripsResponse.Data.Trip>()
        when(text){
            "all" ->{
                filteredItem = trips
            }
            "pending" ->{
                for (trip in trips){
                    if(trip.status == "2"){
                        filteredItem.add(trip)
                    }
                }
            }
            "completed" ->{
                for (trip in trips){
                    if(trip.status == "1"){
                        filteredItem.add(trip)
                    }
                }
            }
        }
        return filteredItem
    }
}