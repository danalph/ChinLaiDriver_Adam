package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.Invoices
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel

/**
 * Created by owner on 25/03/2019
 */
class InvoiceListViewModel(private val schedulerProvider: SchedulerProvider, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    var name = ObservableString("")

    init {
        name.set(appPreference.getUser().name)
    }

    fun dummyData(): MutableList<InvoiceMonthModel>{
        val models = mutableListOf<Invoices>()
        val marchItem = Invoices("INV 2001", "2018-03-11", "2000", "1")
        val marchItemUnpaid = Invoices("INV 1001", "2018-03-12", "9000", "2")
        val aprilItem = Invoices("INV 2002", "2018-04-11", "13000", "1")
        val aprilItemUnpaid = Invoices("INV 1002", "2018-04-12", "9000", "2")

        models.add(marchItem)
        models.add(aprilItem)
        models.add(marchItemUnpaid)
        models.add(aprilItemUnpaid)

        val month = InvoiceMonthModel("March", models)
        val collection = mutableListOf<InvoiceMonthModel>()
        collection.add(month)
        return collection
    }

}