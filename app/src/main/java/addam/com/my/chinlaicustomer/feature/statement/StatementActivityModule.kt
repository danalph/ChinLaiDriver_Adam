package addam.com.my.chinlaicustomer.feature.statement

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Addam on 19/03/2019
 */
@Module
class StatementActivityModule {
    @Provides
    fun provideViewModel(  schedulerProvider: SchedulerProvider,   databaseRepository: DatabaseRepository,   appPreference: AppPreference,   generalRepository: GeneralRepository) 
            = StatementViewModel(schedulerProvider,databaseRepository,appPreference,generalRepository)
}