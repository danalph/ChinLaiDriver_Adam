package addam.com.my.chinlaicustomer.feature.salesorder

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

@Module
class SalesOrderActivityModule{
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, databaseRepository: DatabaseRepository, appPreference: AppPreference, generalRepository: GeneralRepository)
            = SalesOrderViewModel(schedulerProvider, databaseRepository, appPreference, generalRepository)
}