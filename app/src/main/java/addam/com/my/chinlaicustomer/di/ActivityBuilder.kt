package addam.com.my.chinlaicustomer.di

import addam.com.my.chinlaicustomer.feature.delivery.DashboardActivity
import addam.com.my.chinlaicustomer.feature.delivery.DashboardActivityModule
import addam.com.my.chinlaicustomer.feature.delivery.DeliveryDetailsActivity
import addam.com.my.chinlaicustomer.feature.delivery.DeliveryDetailsActivityModule
import addam.com.my.chinlaicustomer.feature.destination.DestinationActivity
import addam.com.my.chinlaicustomer.feature.destination.DestinationActivityModule
import addam.com.my.chinlaicustomer.feature.login.LoginActivity
import addam.com.my.chinlaicustomer.feature.login.LoginActivityModule
import addam.com.my.chinlaicustomer.feature.map.MapActivity
import addam.com.my.chinlaicustomer.feature.map.MapActivityModule
import addam.com.my.chinlaicustomer.feature.password.ResetPasswordActivity
import addam.com.my.chinlaicustomer.feature.password.ResetPasswordModule
import addam.com.my.chinlaicustomer.feature.profile.ProfileActivity
import addam.com.my.chinlaicustomer.feature.profile.ProfileActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Addam on 7/1/2019.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(DashboardActivityModule::class)])
    abstract fun bindDashboardActivity() : DashboardActivity

    @ContributesAndroidInjector(modules = [(ProfileActivityModule::class)])
    abstract fun bindProfileActivity() : ProfileActivity

    @ContributesAndroidInjector(modules = [(ResetPasswordModule::class)])
    abstract fun bindResetPasswordActivity() : ResetPasswordActivity

    @ContributesAndroidInjector(modules = [(MapActivityModule::class)])
    abstract fun bindMapActivity() : MapActivity

    @ContributesAndroidInjector(modules = [(DeliveryDetailsActivityModule::class)])
    abstract fun bindDeliveryDetailsActivity() : DeliveryDetailsActivity

    @ContributesAndroidInjector(modules = [(DestinationActivityModule::class)])
    abstract fun bindDestinationActivity(): DestinationActivity

}