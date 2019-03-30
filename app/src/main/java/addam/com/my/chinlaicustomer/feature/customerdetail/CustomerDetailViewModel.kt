package addam.com.my.chinlaicustomer.feature.customerdetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.Constants
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.CustomerDetailResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by owner on 29/03/2019
 */
class CustomerDetailViewModel(private val schedulerProvider: SchedulerProvider, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {
    var name = ObservableString("Something")

    var roc =  ObservableString("(255234-A")
    var contact = ObservableString("0121111111")
    var email = ObservableString("herpderp@gmail.com")
    var address = ObservableString("alskajdsaldkasdjadskaldaksdjsadkalsdkadj")

    fun getCustomerDetails(id: String) =
        generalRepository.getCustomerDetails(id).compose(schedulerProvider.getSchedulersForSingle())


    fun getCustomer(id: String, roc: String) {
        this.roc.set(roc)

        getCustomerDetails(id).subscribeBy(onSuccess = {
            if (it.status){
                name.set(it.data.customer.customer_company_name)
                setCustomerBranches(it.data.customerBranches)
            }
        }, onError = {
            Timber.e{it.message.toString()}
        })
    }

    private fun setCustomerBranches(customerBranches: List<CustomerDetailResponse.Data.CustomerDetailBranches>) {
        for (branch in customerBranches){
            if (branch.name.equals("Default", true)){
                contact.set(branch.contact)
                address.set(Constants.setAddress(
                    branch.address1,
                    branch.address2,
                    branch.address3,
                    branch.stateName,
                    branch.postcode,
                    branch.areaName
                ))
            }
        }
    }


}
