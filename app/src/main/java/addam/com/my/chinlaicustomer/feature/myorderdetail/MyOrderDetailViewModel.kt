package addam.com.my.chinlaicustomer.feature.myorderdetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.GenericSingleEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDeliveryDetailResponse
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDeliveryStatusResponse
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDriverDetailResponse
import addam.com.my.chinlaicustomer.utilities.model.ViewDeliveryHeaderModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableParcelable
import io.reactivex.rxkotlin.subscribeBy

class MyOrderDetailViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){


    val startActivityEvent: StartActivityEvent = StartActivityEvent()
    val isLoading = ObservableBoolean(false)
    var orderId = ""
    val orderDriverDetail = MutableLiveData<OrderDriverDetailResponse>()
    val orderDeliveryStatus = MutableLiveData<OrderDeliveryStatusResponse>()
    val orderDetail = MutableLiveData<OrderDeliveryDetailResponse>()
    val errorEvent = GenericSingleEvent()
    var headerItem = ViewDeliveryHeaderModel()

    fun getOrderDetail(){
        isLoading.set(true)
        generalRepository.getOrderDetail(orderId).compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {
                    if (it.status){
                        orderDetail.postValue(it)
                    }else{
                        isLoading.set(false)
                        errorEvent.value = true
                    }
                    getDriverDetail()
                },
                onError = {
                    isLoading.set(false)
                    errorEvent.value = true
                    getDriverDetail()
                }
            )
    }

    fun getDriverDetail(){
        generalRepository.getOrderDriverDetail(orderId).compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {
                    if (it.status!!){
                        orderDriverDetail.postValue(it)
                    }else{
                        isLoading.set(false)
                        errorEvent.value = true
                    }
                    getDeliveryStatus()
                },
                onError = {
                    isLoading.set(false)
                    errorEvent.value = true
                    getDeliveryStatus()
                }
            )
    }

    fun getDeliveryStatus(){
        generalRepository.getOrderDeliveryStatus(orderId).compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {
                    if (it.status!!){
                        headerItem.itemId.set(it.data?.dO?.docNum)
                        headerItem.date.set(it.data?.dO?.date)
                        headerItem.status.set(it.data?.dO?.status)
                        orderDeliveryStatus.postValue(it)
                    }else{
                        errorEvent.value = true
                    }
                    isLoading.set(false)
                },
                onError = {
                    isLoading.set(false)
                    errorEvent.value = true
                }
            )
    }

    fun onError(){
        startActivityEvent.value = StartActivityModel(Router.Destination.ERROR, hasResults = false, clearHistory = true)
    }
}