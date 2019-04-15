package addam.com.my.chinlaicustomer.feature.dashboard

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.CategoryListResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Firdaus on 14/1/2019.
 */

class DashboardViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    val startActivityEvent: StartActivityEvent = StartActivityEvent()
    var tripCount = ObservableInt(0)
    val tripResponse: MutableLiveData<CategoryListResponse> = MutableLiveData()
    val errorResponse: MutableLiveData<Int>  = MutableLiveData()
    var name = ObservableString("")
    var isLoading = ObservableBoolean(false)

    init {
        name.set(appPreference.getUser().name)
        getCategoryList()
    }

    fun getCategoryList(){
        isLoading.set(true)
        generalRepository.getCategoryList()
            .compose(schedulerProvider.getSchedulersForSingle()).subscribeBy(
                onSuccess = {
                    if (it.status){
                        tripResponse.postValue(it)
                    }else{
                        errorResponse.postValue(R.string.error_getting_response)
                    }
                    isLoading.set(false)
                }, onError = {
                    errorResponse.postValue(R.string.error_getting_response)
                    isLoading.set(false)
                })
    }

    fun startProductListActivity(categoryId: String){
        startActivityEvent.value = StartActivityModel(Router.Destination.PRODUCT,
                hashMapOf(Pair(Router.Parameter.CATEGORY_ID, categoryId)), hasResults = false, clearHistory = false)
    }

    fun onOpenCart(){
        startActivityEvent.value = StartActivityModel(Router.Destination.CART, hasResults = false, clearHistory = false)
    }
}