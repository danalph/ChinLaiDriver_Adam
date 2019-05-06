package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import android.arch.lifecycle.ViewModel

/**
 * Created by owner on 06/05/2019
 */
class ItemSalesPriceHistoryViewModel(val schedulerProvider: SchedulerProvider, val appPreference: AppPreference, val generalRepository: GeneralRepository): ViewModel() {
}