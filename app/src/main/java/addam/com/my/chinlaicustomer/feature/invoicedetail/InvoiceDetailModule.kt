package addam.com.my.chinlaicustomer.feature.invoicedetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by owner on 31/03/2019
 */
@Module
class InvoiceDetailModule {
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, appPreference: AppPreference, generalRepository: GeneralRepository)
            = InvoiceDetailViewModel(schedulerProvider,generalRepository, appPreference)
}