package addam.com.my.chinlaicustomer.feature.salesperson

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by owner on 22/03/2019
 */
@Module
class SalesLoginModule {
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, generalRepository: GeneralRepository, appPreference: AppPreference)
            = SalesLoginViewModel(schedulerProvider,generalRepository,appPreference)
}