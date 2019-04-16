package addam.com.my.chinlaicustomer.feature.myorderlist

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.MyOrderResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import io.reactivex.rxkotlin.subscribeBy

class MyOrderListViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){

    var name = ObservableString("")
    val isLoading = ObservableBoolean(false)
    val totalOrder = ObservableInt(0)
    val orderList = MutableLiveData<ArrayList<MyOrderResponse.Data.SO>>()
    val startActivityEvent = StartActivityEvent()
    var isEmpty = ObservableBoolean(false)

    init {
        name.set(appPreference.getUser().name)
        getOrder(0, 20, "all", true)
    }

    fun getOrder(offset: Int, limit: Int, status: String, isFirstLoad: Boolean){
        isLoading.set(true)
        generalRepository.getOrder(appPreference.getUser().id, offset.toString(), limit.toString(), status)
            .compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {
                    if (it.status){
                        if (it.data.total != 0)
                            orderList.postValue(it.data.sOs)
                        else{
                            if (isFirstLoad){
                                isEmpty.set(true)
                            }
                        }
                    }
                    isLoading.set(false)
                },
                onError = {
                    isLoading.set(false)
                }
            )
    }

    fun viewDetail(orderId: String, selectedTab: Int){
        startActivityEvent.value = StartActivityModel(
            Router.Destination.MY_ORDER_DETAIL,
            hashMapOf(Pair(Router.Parameter.ORDER_ID, orderId), Pair(Router.Parameter.SELECTED_TAB, selectedTab)) , hasResults = false, clearHistory = false)
    }

    fun viewSalesOrderDetail(orderId: String){
        startActivityEvent.value = StartActivityModel(
            Router.Destination.SALES_ORDER,
            hashMapOf(Pair(Router.Parameter.ORDER_ID, orderId)) , hasResults = false, clearHistory = false)
    }

}