package addam.com.my.chinlaicustomer.feature.profile

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Addam on 14/1/2019.
 */
@Module
class ProfileActivityModule {
    @Provides
    fun provideViewModel(databaseRepository: DatabaseRepository, appPreference: AppPreference)
            = ProfileViewModel(databaseRepository,appPreference)
}