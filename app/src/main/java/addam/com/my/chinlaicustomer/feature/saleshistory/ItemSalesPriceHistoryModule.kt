package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by owner on 06/05/2019
 */
@Module
class ItemSalesPriceHistoryModule {
    @Provides
    fun providesViewModel(schedulerProvider: SchedulerProvider, appPreference: AppPreference, generalRepository: GeneralRepository)
    = ItemSalesPriceHistoryViewModel(schedulerProvider, appPreference, generalRepository)
}