package addam.com.my.chinlaicustomer.feature.salesorder

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.SalesOrderDetailResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.Validator
import addam.com.my.chinlaicustomer.utilities.observe
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import io.reactivex.rxkotlin.subscribeBy

class SalesOrderViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){
    val isLoading = ObservableBoolean(false)
    val salesOrderId = ObservableString("")
    val status = ObservableString("")
    val companyName = ObservableString("")
    val roc = ObservableString("")
    val customerCode = ObservableString("")
    val customerCreditTerms = ObservableString("")
    val salesPerson = ObservableString("")
    val billingAddress =  ObservableString("")
    val totalAmount = ObservableString("")
    val doNumber = ObservableString("")
    val items =  MutableLiveData<ArrayList<SalesOrderDetailResponse.Data.Item>>()
    val statusColor = ObservableInt(0)

    fun getSalesOrderDetails(id: String){
        generalRepository.getSalesOrderDetail(id).compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {
                    if (it.status!!){
                        val so = it.data?.sO
                        salesOrderId.set(so?.docNum)
                        status.set(so?.status)
                        statusColor.set(when(so?.status){
                            "pending" -> R.color.grey
                            "confirmed" -> R.color.colorYellow
                            "processing" -> R.color.colorLightRed
                            "delivering" -> R.color.colorLightBlue
                            "completed" -> R.color.colorBlue
                            "unknown" -> R.color.colorGreen
                            else -> R.color.colorRed
                        })
                        companyName.set(so?.companyName?.trim())
                        roc.set(so?.roc)
                        customerCode.set(so?.customerCode)
                        customerCreditTerms.set(so?.customerCreditTerm)
                        salesPerson.set(so?.salesperson)
                        billingAddress.set(so?.billingAddress)
                        totalAmount.set(so?.totalAmount)
                        items.postValue(it.data?.items)
                    }
                },
                onError = {

                }
            )
    }
}