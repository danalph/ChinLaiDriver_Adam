package addam.com.my.chinlaicustomer.feature.salescustomer

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.CustomerListResponse
import addam.com.my.chinlaicustomer.rest.model.Customers
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.github.ajalt.timberkt.Timber
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by owner on 23/03/2019
 */
class CustomerListViewModel(private val schedulerProvider: SchedulerProvider, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    var name = ObservableString("")
    var isLoading = ObservableBoolean(true)
    lateinit var callback: CustomerListViewModelCallback

    private var originalList = listOf<Customers>()

    val filteredList: MutableList<Customers> = mutableListOf()
    val oldFilteredList: MutableList<Customers> = mutableListOf()


    init {
        name.set(appPreference.getUser().name)
        getList()
    }

    private fun getList(){
        isLoading.set(true)
        callCustomerList().subscribeBy(onSuccess = {
            if (it.status){
                originalList = it.data.customers
                oldFilteredList.addAll(originalList)
                callback.updateUI()
                isLoading.set(false)
            }else isLoading.set(false)
        }, onError = {
            isLoading.set(false)
            Timber.e{it.message.toString()}
        })
    }

    private fun callCustomerList(): Single<CustomerListResponse> {
        return generalRepository.getCustomerList(appPreference.getSalesId())
            .compose(schedulerProvider.getSchedulersForSingle())
    }

    fun search(query: String): Completable = Completable.create{
        val wanted = originalList.filter {
            it.companyName.contains(query.toUpperCase()) || it.roc.contains(query)
        }.toList()

        filteredList.clear()
        filteredList.addAll(wanted)
        it.onComplete()
    }

    interface CustomerListViewModelCallback{
        fun updateUI()
    }
}