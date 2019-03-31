package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by owner on 25/03/2019
 */
@Module
class InvoiceListModule {
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, appPreference: AppPreference, generalRepository: GeneralRepository) =
            InvoiceListViewModel(schedulerProvider, appPreference, generalRepository)
}