package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.Item
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.ProductHistoryRequest
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.ProductHistoryResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by owner on 10/05/2019
 */
class ItemHistoryViewModel(val appPreference: AppPreference, val schedulerProvider: SchedulerProvider, val generalRepository: GeneralRepository): ViewModel() {

    var id = ObservableString("")
    var start = ObservableString("")
    var end = ObservableString("")
    val items = MutableLiveData<List<Item>>()

    private fun callItemHistory(): Single<ProductHistoryResponse> {
        return generalRepository.getProductHistory(
            ProductHistoryRequest(
                id.get()!!.toInt(),
                start.get().toString(),
                end.get().toString()))
            .compose(schedulerProvider.getSchedulersForSingle())
    }

    fun getItemHistory(){
        callItemHistory().subscribeBy(onSuccess = {
            if(it.status){
                items.postValue(it.data.items)
            }
        }, onError = {

        })
    }
}