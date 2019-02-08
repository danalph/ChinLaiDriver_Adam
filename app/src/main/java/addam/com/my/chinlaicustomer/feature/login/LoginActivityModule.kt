package addam.com.my.chinlaicustomer.feature.login

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Addam on 7/1/2019.
 */
@Module
class LoginActivityModule {
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, generalRepository: GeneralRepository, appPreference: AppPreference)
    = LoginViewModel(schedulerProvider,generalRepository,appPreference)
}