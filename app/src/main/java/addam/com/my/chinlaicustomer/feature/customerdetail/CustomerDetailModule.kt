package addam.com.my.chinlaicustomer.feature.customerdetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by owner on 29/03/2019
 */
@Module
class CustomerDetailModule {
    @Provides
    fun provideViewModel(  schedulerProvider: SchedulerProvider,   appPreference: AppPreference,   generalRepository: GeneralRepository) =
            CustomerDetailViewModel(schedulerProvider, appPreference, generalRepository)
}