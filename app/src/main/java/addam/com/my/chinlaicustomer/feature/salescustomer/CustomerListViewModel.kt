package addam.com.my.chinlaicustomer.feature.salescustomer

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.Customers
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel
import io.reactivex.Completable

/**
 * Created by owner on 23/03/2019
 */
class CustomerListViewModel(private val schedulerProvider: SchedulerProvider, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    var name = ObservableString("")

    private val originalList = listOf<Customers>(Customers("2", "ABC Studio", "21313"), Customers("3", "DEF Studio", "414141"))

    val filteredList: MutableList<Customers> = mutableListOf()
    val oldFilteredList: MutableList<Customers> = mutableListOf()


    init {
        name.set(appPreference.getUser().name)
        oldFilteredList.addAll(originalList)
    }

    fun search(query: String): Completable = Completable.create{
        val wanted = originalList.filter {
            it.companyName.contains(query.toUpperCase()) || it.roc.contains(query)
        }.toList()

        filteredList.clear()
        filteredList.addAll(wanted)
        it.onComplete()
    }
}