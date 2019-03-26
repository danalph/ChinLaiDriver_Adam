package addam.com.my.chinlaicustomer.feature.salescustomer

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Addam on 23/03/2019
 */
@Module
class CustomerListModule {
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, appPreference: AppPreference,
                           generalRepository: GeneralRepository) =
            CustomerListViewModel(schedulerProvider, appPreference, generalRepository)
}