package addam.com.my.chinlaicustomer.feature.imageviewer

import dagger.Module
import dagger.Provides

@Module
class ViewImageActivityModule{
    @Provides
    fun provideViewModel() = ViewImageViewModel()
}