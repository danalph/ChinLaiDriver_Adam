package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.Invoices
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel
import io.reactivex.Completable

/**
 * Created by owner on 25/03/2019
 */
class InvoiceListViewModel(private val schedulerProvider: SchedulerProvider, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    var name = ObservableString("")

    var originalList = mutableListOf<Invoices>()
    val filteredList: MutableList<Invoices> = mutableListOf()
    val oldFilteredList: MutableList<Invoices> = mutableListOf()

    init {
        name.set(appPreference.getUser().name)

        originalList = dummySearchData()
        oldFilteredList.addAll(originalList)
    }

    fun dummyData(): MutableList<InvoiceMonthModel>{
        val models = mutableListOf<Invoices>()
        val marchItem = Invoices("INV 1000", "2018-03-11", "2000", "1")
        val marchItemUnpaid = Invoices("INV 567", "2018-03-12", "9000", "2")
        val aprilItem = Invoices("INV 2345", "2018-04-11", "13000", "1")
        val aprilItemUnpaid = Invoices("INV 789", "2018-04-12", "9000", "2")

        models.add(marchItem)
        models.add(aprilItem)
        models.add(marchItemUnpaid)
        models.add(aprilItemUnpaid)

        val month = InvoiceMonthModel("March", models)
        val collection = mutableListOf<InvoiceMonthModel>()
        collection.add(month)
        return collection
    }

    fun dummySearchData(): MutableList<Invoices>{
        val models = mutableListOf<Invoices>()
        val marchItem = Invoices("INV 1000", "2018-03-11", "2000", "1")
        val marchItemUnpaid = Invoices("INV 567", "2018-03-12", "9000", "2")
        val aprilItem = Invoices("INV 2345", "2018-04-11", "13000", "1")
        val aprilItemUnpaid = Invoices("INV 789", "2018-04-12", "9000", "2")

        models.add(marchItem)
        models.add(aprilItem)
        models.add(marchItemUnpaid)
        models.add(aprilItemUnpaid)

        return models
    }

    fun search(query: String): Completable = Completable.create {
        val wanted = originalList.filter { invoices ->
            invoices.id.contains(query.toUpperCase())
        }.toList()
        filteredList.clear()
        filteredList.addAll(wanted)
        it.onComplete()
    }

}