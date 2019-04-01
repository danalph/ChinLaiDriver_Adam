package addam.com.my.chinlaicustomer.feature.invoicedetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.Validator
import addam.com.my.chinlaicustomer.utilities.observe
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean

/**
 * Created by Addam on 31/03/2019
 */
class InvoiceDetailViewModel(private val schedulerProvider: SchedulerProvider, private val generalRepository: GeneralRepository, private val appPreference: AppPreference): ViewModel() {
    var invoiceNo = ObservableString("")

    var date = ObservableString("")
    var status = ObservableBoolean(false)
    var companyName = ObservableString("")
    var roc = ObservableString("")
    var account = ObservableString("")
    var salesPerson = ObservableString("")
    var doNo = ObservableString("")
    var term = ObservableString("")
    var billingAddress = ObservableString("")
    var total = ObservableString("")
    var items = mutableListOf<ItemInvoiceDetail>()

    var isPaid = ObservableString("1")

    init {
        isPaid.observe().map { Validator.StatusValidator.isPaid(it) }.subscribe {
            status.set(it)
        }
    }

    fun getDummy(): MutableList<ItemInvoiceDetail> {
        var item = ItemInvoiceDetail("Power Drill (4500)", "%&^%& (4500)",
            "600.00", "0", "3", "1,800", "https://image.shutterstock.com/z/stock-photo-view-of-modern-and-powerful-battery-drill-on-a-white-background-1263309601.jpg")

        val items = mutableListOf<ItemInvoiceDetail>()
        items.add(item)
        items.add(item)
        return items
    }


}
