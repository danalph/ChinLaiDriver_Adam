package addam.com.my.chinlaicustomer.feature.myorderdetail

import addam.com.my.chinlaicustomer.AppPreference
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

    val isLoading = ObservableBoolean(false)
    var orderId = ""
    val orderDriverDetail = MutableLiveData<OrderDriverDetailResponse>()
    val orderDeliveryStatus = MutableLiveData<OrderDeliveryStatusResponse>()
    val orderDetail = MutableLiveData<OrderDeliveryDetailResponse>()
    var headerItem = ViewDeliveryHeaderModel()


    fun getDriverDetail(){
        generalRepository.getOrderDriverDetail(orderId).compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {
                    orderDriverDetail.postValue(it)
                },
                onError = {
                    isLoading.set(false)
                }
            )
    }

    fun getDeliveryStatus(){
        generalRepository.getOrderDeliveryStatus(orderId).compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {
                    headerItem.itemId.set(it.data?.dO?.docNum)
                    headerItem.date.set(it.data?.dO?.date)
                    headerItem.status.set(it.data?.dO?.status)
                    orderDeliveryStatus.postValue(it)
                    isLoading.set(false)
                },
                onError = {
                    isLoading.set(false)
                }
            )
    }

    fun getOrderDetail(){
        isLoading.set(true)
        generalRepository.getOrderDetail(orderId).compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {
                    orderDetail.postValue(it)
                },
                onError = {
                    isLoading.set(false)
                }
            )
    }
}