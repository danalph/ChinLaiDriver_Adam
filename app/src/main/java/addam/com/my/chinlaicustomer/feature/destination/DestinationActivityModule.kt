package addam.com.my.chinlaicustomer.feature.destination

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Addam on 19/1/2019.
 */
@Module
class DestinationActivityModule {
    @Provides
    fun provideViewModel(appPreference: AppPreference, generalRepository: GeneralRepository, schedulerProvider: SchedulerProvider)
    = DestinationViewModel(appPreference, generalRepository, schedulerProvider)
}