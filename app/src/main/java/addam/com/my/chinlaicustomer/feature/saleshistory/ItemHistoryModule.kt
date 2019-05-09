package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by owner on 10/05/2019
 */
@Module
class ItemHistoryModule {
    @Provides
    fun provideViewModel(appPreference: AppPreference, schedulerProvider: SchedulerProvider, generalRepository: GeneralRepository) =
            ItemHistoryViewModel(appPreference, schedulerProvider, generalRepository)
}