package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
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

    var startActivityEvent = StartActivityEvent()
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

    fun filterStatus(status: String): Completable = Completable.create{
        val filter = oldFilteredList.filter { invoices ->
            invoices.status.contentEquals(status)
        }.toList()
        filteredList.clear()
        filteredList.addAll(filter)
        it.onComplete()
    }

    fun startActivity(item: Invoices){
        startActivityEvent.value = StartActivityModel(Router.Destination.INVOICE_DETAIL, hashMapOf(
            Pair(Router.Parameter.ITEM_ID, item.id),
            Pair(Router.Parameter.ITEM_DATE, item.date),
            Pair(Router.Parameter.ITEM_AMOUNT, item.amount),
            Pair(Router.Parameter.ITEM_STATUS, item.status)
        ), hasResults = false, clearHistory = false)
    }
}