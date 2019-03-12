package addam.com.my.chinlaicustomer.feature.productdetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

@Module
class ProductDetailActivityModule{
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, databaseRepository: DatabaseRepository, appPreference: AppPreference, generalRepository: GeneralRepository) = ProductDetailViewModel(schedulerProvider, databaseRepository, appPreference, generalRepository)
}