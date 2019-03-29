package addam.com.my.chinlaicustomer.feature.productlist

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.ProductListResponse
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.rxkotlin.subscribeBy

class ProductListViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){
    val startActivityEvent: StartActivityEvent = StartActivityEvent()
    val productsResponse = MutableLiveData<ProductListResponse>()
    var isLoading = ObservableBoolean(false)

    fun getItem(id: String){
        isLoading.set(true)
        generalRepository.getProductList(id, "0", "5", "id", "DESC", "[{\"field\":\"name\",\"operator\":\"%\",\"value\":\"\"}]")
            .compose(schedulerProvider.getSchedulersForSingle()).subscribeBy(
                onSuccess = {
                    productsResponse.postValue(it)
                    isLoading.set(false)
                },
                onError = {
                    isLoading.set(false)
                }
            )
    }

    fun onItemSelected(id: String){
        startActivityEvent.value = StartActivityModel(Router.Destination.PRODUCT_DETAIL,
            hashMapOf(Pair(Router.Parameter.ITEM_ID, id)) , hasResults = false, clearHistory = false)
    }

    fun onOpenCart(){
        startActivityEvent.value = StartActivityModel(Router.Destination.CART, hasResults = false, clearHistory = false)
    }

}