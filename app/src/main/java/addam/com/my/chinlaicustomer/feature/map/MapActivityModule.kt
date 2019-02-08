package addam.com.my.chinlaicustomer.feature.map

import android.arch.lifecycle.ViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by Addam on 15/1/2019.
 */
@Module
class MapActivityModule: ViewModel() {
    @Provides
    fun providesViewModel() = MapViewModel()
}