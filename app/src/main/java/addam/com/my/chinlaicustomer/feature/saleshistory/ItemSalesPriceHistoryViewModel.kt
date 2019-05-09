package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
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
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by owner on 06/05/2019
 */
class ItemSalesPriceHistoryViewModel(val schedulerProvider: SchedulerProvider, val appPreference: AppPreference, val generalRepository: GeneralRepository): ViewModel() {

    val hasSearched = ObservableBoolean(false)
    val searchText = ObservableString("")
    val products = MutableLiveData<List<Product>>()

    val startActivityEvent = StartActivityEvent()

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

    fun onDateSelected(list: List<Calendar>, item: Product) {
        val startDate: Calendar = list[0]
        val endDate: Calendar = list[list.lastIndex]

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val start = simpleDateFormat.format(startDate.time)
        val end = simpleDateFormat.format(endDate.time)

        startActivityEvent.value = StartActivityModel(Router.Destination.HISTORY, hashMapOf(
            Pair(Router.Parameter.ITEM_ID, item.id),
            Pair(Router.Parameter.START_DATE, start),
            Pair(Router.Parameter.END_DATE, end)
        ), hasResults = false)
    }
}