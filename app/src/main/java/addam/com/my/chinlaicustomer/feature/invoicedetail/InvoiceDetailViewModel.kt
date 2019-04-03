package addam.com.my.chinlaicustomer.feature.invoicedetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.InvoiceData
import addam.com.my.chinlaicustomer.rest.model.InvoiceDetailsResponse
import addam.com.my.chinlaicustomer.rest.model.InvoiceItem
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.Validator
import addam.com.my.chinlaicustomer.utilities.observe
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.github.ajalt.timberkt.Timber
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

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
    var items = mutableListOf<InvoiceItem>()
    var isPaid = ObservableString("1")

    lateinit var callback: InvoiceDetailCallback

    init {
        isPaid.observe().map { Validator.StatusValidator.isPaid(it) }.subscribe {
            status.set(it)
        }
    }

    fun callInvoiceDetails(id: String): Single<InvoiceDetailsResponse>{
        return generalRepository.getInvoiceDetails(id).compose(schedulerProvider.getSchedulersForSingle())
    }

    fun getInvoiceDetails(id: String){
        callInvoiceDetails(id).subscribeBy(onSuccess = {
            if(it.status){
                setData(it.data)
            }
        }, onError = {
            Timber.e { it.message.toString() }
        })
    }

    private fun setData(data: InvoiceData) {
        val invoice = data.invoiceDetails
        invoiceNo.set(invoice.docNum)
        date.set(invoice.date)
        companyName.set(invoice.company)
        roc.set(invoice.roc)
        account.set(invoice.code)
        salesPerson.set(invoice.salesperson)
        doNo.set(invoice.doNum)
        term.set(invoice.creditTerm)
        billingAddress.set(invoice.billingAddress)
        total.set(invoice.totalAmount)
        isPaid.set(invoice.status)

        items.addAll(data.items)
        callback.updateUI()
    }


//    fun getDummy(): MutableList<ItemInvoiceDetail> {
//        var item = ItemInvoiceDetail("Power Drill (4500)", "%&^%& (4500)",
//            "600.00", "0", "3", "1,800", "https://image.shutterstock.com/z/stock-photo-view-of-modern-and-powerful-battery-drill-on-a-white-background-1263309601.jpg")
//
//        val items = mutableListOf<ItemInvoiceDetail>()
//        items.add(item)
//        return items
//    }

 interface InvoiceDetailCallback{
     fun updateUI()
 }
}
