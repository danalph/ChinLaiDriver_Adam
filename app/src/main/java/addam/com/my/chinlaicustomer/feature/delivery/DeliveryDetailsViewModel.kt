package addam.com.my.chinlaicustomer.feature.delivery

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.ViewDeliveryTripResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.observe
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Firdaus on 22/1/2019.
 */
class DeliveryDetailsViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){

    val startActivityEvent : StartActivityEvent = StartActivityEvent()
    val deliveryDetailsResponse : MutableLiveData<ViewDeliveryTripResponse> = MutableLiveData()
    val errorResponse: MutableLiveData<Int> = MutableLiveData()
    val createOn = ObservableString("")
    val id = ObservableString("")
    val status = ObservableString("")
    val totalDestination = ObservableString("")
    val vehicleModel = ObservableString("")
    val vehiclePlate = ObservableString("")

    val isCompleted = ObservableBoolean(false)
    init{
        status.observe().map { status.get().toString()=="2" }.subscribe {
            if(it) isCompleted.set(false)
            else isCompleted.set(true)
        }
    }

    fun getDeliveryDetails(driverId: String, tripId: String){
        generalRepository.getSingleTrip(driverId, tripId, "0", "200", "all")
            .compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy (
                onSuccess = {
                    if (it.status){
                        createOn.set(it.data.trip.createOn)
                        id.set(it.data.trip.id)
                        status.set(it.data.trip.status)
                        totalDestination.set("${it.data.documents.size}")
                        vehicleModel.set(it.data.trip.vehicleModel)
                        vehiclePlate.set(it.data.trip.vehiclePlate)

                        deliveryDetailsResponse.postValue(it)
                    }else{
                        errorResponse.postValue(R.string.error_getting_response)
                    }
                }, onError = {
                    errorResponse.postValue(R.string.error_getting_response)
                }
            )
    }

    fun startActivity(tripId: String, type: String, docId: String){
        startActivityEvent.value = StartActivityModel(Router.Destination.DESTINATION,
            hashMapOf(Pair(Router.Parameter.TRIP_ID, tripId),
                Pair(Router.Parameter.TYPE, type),
                Pair(Router.Parameter.DOC_ID, docId)
            ), hasResults = false, clearHistory = false)
    }

}