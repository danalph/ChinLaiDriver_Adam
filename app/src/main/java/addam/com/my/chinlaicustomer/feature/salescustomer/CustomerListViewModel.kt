package addam.com.my.chinlaicustomer.feature.salescustomer

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel

/**
 * Created by owner on 23/03/2019
 */
class CustomerListViewModel(private val schedulerProvider: SchedulerProvider, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    var name = ObservableString("")
    init {
        name.set(appPreference.getUser().name)
    }
}