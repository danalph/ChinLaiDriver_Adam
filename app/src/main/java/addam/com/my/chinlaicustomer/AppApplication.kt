package addam.com.my.chinlaicustomer

import addam.com.my.chinlaicustomer.di.DaggerAppComponent
import android.app.Activity
import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.github.ajalt.timberkt.Timber
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Addam on 7/1/2019.
 */
class AppApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        initLogger()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(timber.log.Timber.DebugTree())
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector
}