package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.Product
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.SearchProductHistoryResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by owner on 06/05/2019
 */
class ItemSalesPriceHistoryViewModel(val schedulerProvider: SchedulerProvider, val appPreference: AppPreference, val generalRepository: GeneralRepository): ViewModel() {

    val hasSearched = ObservableBoolean(false)
    val searchText = ObservableString("")
    val products = MutableLiveData<List<Product>>()

    fun onCalendarClicked(){

    }

    fun onSearchClicked(){
        hasSearched.set(true)
        callProductList().subscribeBy(onSuccess = {
            products.postValue(it.data.products)
        }, onError = {

        })
    }

    private fun callProductList(): Single<SearchProductHistoryResponse> {
        val filter = "[{\"field\":\"description_1\",\"operator\":\"%\",\"value\":\"${searchText.get()}\"}]"
        return generalRepository.getSearchProduct(filter)
            .compose(schedulerProvider.getSchedulersForSingle())
    }
}