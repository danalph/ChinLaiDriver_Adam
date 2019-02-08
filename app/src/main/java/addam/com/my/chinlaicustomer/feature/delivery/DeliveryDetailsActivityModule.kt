package addam.com.my.chinlaicustomer.feature.delivery

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Firdaus on 22/1/2019.
 */
@Module
class DeliveryDetailsActivityModule{
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, databaseRepository: DatabaseRepository, appPreference: AppPreference, generalRepository: GeneralRepository) = DeliveryDetailsViewModel(schedulerProvider, databaseRepository, appPreference, generalRepository)
}