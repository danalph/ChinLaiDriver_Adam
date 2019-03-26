package addam.com.my.chinlaicustomer.feature.password

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import dagger.Module
import dagger.Provides

@Module
class ResetPasswordModule {
    @Provides
fun provideViewModel(schdulerProvider: SchedulerProvider,
                     generalRepository: GeneralRepository, appPreference: AppPreference)
            = ResetPasswordViewModel(schdulerProvider, generalRepository, appPreference)
}