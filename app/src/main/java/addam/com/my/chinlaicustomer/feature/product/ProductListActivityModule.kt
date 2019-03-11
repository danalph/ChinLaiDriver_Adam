package addam.com.my.chinlaicustomer.feature.product

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

@Module
class ProductListActivityModule{
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, databaseRepository: DatabaseRepository, appPreference: AppPreference, generalRepository: GeneralRepository) = ProductListViewModel(schedulerProvider, databaseRepository, appPreference, generalRepository)
}