package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.InvoiceListResponse
import addam.com.my.chinlaicustomer.rest.model.Invoices
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.github.ajalt.timberkt.Timber
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by owner on 25/03/2019
 */
class InvoiceListViewModel(private val schedulerProvider: SchedulerProvider, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    var name = ObservableString("")
    var isLoading = ObservableBoolean(false)

    var originalList = mutableListOf<Invoices>()
    val filteredList: MutableList<Invoices> = mutableListOf()
    val oldFilteredList: MutableList<Invoices> = mutableListOf()

    lateinit var callback: InvoiceCallback

    var startActivityEvent = StartActivityEvent()
    init {
        name.set(appPreference.getUser().name)

//        originalList = dummySearchData()
//        oldFilteredList.addAll(originalList)

        getList()
    }


    fun callList(): Single<InvoiceListResponse>{
        return generalRepository.getInvoiceList(appPreference.getUser().id).compose(schedulerProvider.getSchedulersForSingle())
    }

    fun getList(){
        isLoading.set(true)
        callList().subscribeBy(onSuccess = {
            if(it.status){
                setData(it.data.INVs)
            }else isLoading.set(false)
        }, onError = {
            Timber.e{it.message.toString()}
            isLoading.set(false)
        })
    }

    private fun setData(list: List<Invoices>) {
//        This for sorted static list
        val monthSortedList = sortMonth(list)

        originalList = list.toMutableList()
        oldFilteredList.clear()
        oldFilteredList.addAll(originalList)
        callback.updateUI()
        isLoading.set(false)
    }

    private fun sortMonth(list: List<Invoices>): Map<Int, List<Invoices>> {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val calendar = GregorianCalendar.getInstance()

        val groups = list.groupBy { item->
            val date = format.parse(item.date)
            calendar.time = date
            calendar.get(Calendar.MONTH)
        }


        return groups
    }

    fun dummyData(): MutableList<InvoiceMonthModel>{
        val models = mutableListOf<Invoices>()
        val marchItem = Invoices("1","INV 1000", "2018-03-11 10:30:22", "2000", "1")
        val marchItemUnpaid = Invoices("2","INV 567", "2018-03-12 10:30:22", "9000", "2")
        val aprilItem = Invoices("3","INV 2345", "2018-04-11 10:30:22", "13000", "1")
        val aprilItemUnpaid = Invoices("4","INV 789", "2018-04-12 10:30:22", "9000", "2")
        val mayItem = Invoices("5","INV 2325", "2018-05-11 10:30:22", "13000", "1")
        val mayItemUnpaid = Invoices("6","INV 589", "2018-05-12 10:30:22", "9000", "2")

        models.add(marchItem)
        models.add(aprilItem)
        models.add(marchItemUnpaid)
        models.add(aprilItemUnpaid)
        models.add(mayItem)
        models.add(mayItemUnpaid)

        Timber.d{sortMonth(models).toString()}

        val month = InvoiceMonthModel("March", models)
        val collection = mutableListOf<InvoiceMonthModel>()
        collection.add(month)
        return collection
    }

    fun dummySearchData(): MutableList<Invoices>{
        val models = mutableListOf<Invoices>()
        val marchItem = Invoices("1","INV 1000", "2018-03-11 10:30:22", "2000", "1")
        val marchItemUnpaid = Invoices("2","INV 567", "2018-03-12 10:30:22", "9000", "2")
        val aprilItem = Invoices("3","INV 2345", "2018-04-11 10:30:22", "13000", "1")
        val aprilItemUnpaid = Invoices("4","INV 789", "2018-04-12 10:30:22", "9000", "2")

        models.add(marchItem)
        models.add(aprilItem)
        models.add(marchItemUnpaid)
        models.add(aprilItemUnpaid)

        return models
    }

    fun search(query: String): Completable = Completable.create {
        val wanted = originalList.filter { invoices ->
            invoices.docNum.contains(query.toUpperCase())
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
            Pair(Router.Parameter.ITEM_NUM, item.docNum),
            Pair(Router.Parameter.ITEM_DATE, item.date),
            Pair(Router.Parameter.ITEM_AMOUNT, item.amount),
            Pair(Router.Parameter.ITEM_STATUS, item.status)
        ), hasResults = false, clearHistory = false)
    }

    interface InvoiceCallback{
        fun updateUI()
    }
}