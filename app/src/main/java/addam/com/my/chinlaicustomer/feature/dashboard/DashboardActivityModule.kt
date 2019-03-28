package addam.com.my.chinlaicustomer.feature.dashboard

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Firdaus on 14/1/2019.
 */

@Module
class DashboardActivityModule{
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, databaseRepository: DatabaseRepository, appPreference: AppPreference,generalRepository: GeneralRepository) = DashboardViewModel(schedulerProvider, databaseRepository, appPreference, generalRepository)
}