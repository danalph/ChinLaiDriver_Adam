package addam.com.my.chinlaicustomer.feature.password

import dagger.Module
import dagger.Provides

@Module
class ResetPasswordModule {
    @Provides
    fun provideViewModel()
            = ResetPasswordViewModel()
}