package addam.com.my.chinlaicustomer.di

import addam.com.my.chinlaicustomer.feature.cart.CartActivity
import addam.com.my.chinlaicustomer.feature.cart.CartActivityModule
import addam.com.my.chinlaicustomer.feature.customerdetail.CustomerDetailActivity
import addam.com.my.chinlaicustomer.feature.customerdetail.CustomerDetailModule
import addam.com.my.chinlaicustomer.feature.dashboard.DashboardActivity
import addam.com.my.chinlaicustomer.feature.dashboard.DashboardActivityModule
import addam.com.my.chinlaicustomer.feature.destination.DestinationActivity
import addam.com.my.chinlaicustomer.feature.destination.DestinationActivityModule
import addam.com.my.chinlaicustomer.feature.error.ErrorActivity
import addam.com.my.chinlaicustomer.feature.error.ErrorActivityModule
import addam.com.my.chinlaicustomer.feature.imageviewer.ViewImageActivity
import addam.com.my.chinlaicustomer.feature.imageviewer.ViewImageActivityModule
import addam.com.my.chinlaicustomer.feature.invoice.InvoiceListActivity
import addam.com.my.chinlaicustomer.feature.invoice.InvoiceListModule
import addam.com.my.chinlaicustomer.feature.invoicedetail.InvoiceDetailActivity
import addam.com.my.chinlaicustomer.feature.invoicedetail.InvoiceDetailModule
import addam.com.my.chinlaicustomer.feature.login.LoginActivity
import addam.com.my.chinlaicustomer.feature.login.LoginActivityModule
import addam.com.my.chinlaicustomer.feature.map.MapActivity
import addam.com.my.chinlaicustomer.feature.map.MapActivityModule
import addam.com.my.chinlaicustomer.feature.myorderlist.MyOrderListActivity
import addam.com.my.chinlaicustomer.feature.myorderlist.MyOrderListActivityModule
import addam.com.my.chinlaicustomer.feature.myorderdetail.MyOrderDetailActivity
import addam.com.my.chinlaicustomer.feature.myorderdetail.MyOrderDetailActivityModule
import addam.com.my.chinlaicustomer.feature.salesorder.SalesOrderActivity
import addam.com.my.chinlaicustomer.feature.salesorder.SalesOrderActivityModule
import addam.com.my.chinlaicustomer.feature.password.ResetPasswordActivity
import addam.com.my.chinlaicustomer.feature.password.ResetPasswordModule
import addam.com.my.chinlaicustomer.feature.productdetail.ProductDetailActivity
import addam.com.my.chinlaicustomer.feature.productdetail.ProductDetailActivityModule
import addam.com.my.chinlaicustomer.feature.productlist.ProductListActivity
import addam.com.my.chinlaicustomer.feature.productlist.ProductListActivityModule
import addam.com.my.chinlaicustomer.feature.profile.ProfileActivity
import addam.com.my.chinlaicustomer.feature.profile.ProfileActivityModule
import addam.com.my.chinlaicustomer.feature.salescustomer.CustomerListActivity
import addam.com.my.chinlaicustomer.feature.salescustomer.CustomerListModule
import addam.com.my.chinlaicustomer.feature.salesperson.SalesLoginActivity
import addam.com.my.chinlaicustomer.feature.salesperson.SalesLoginModule
import addam.com.my.chinlaicustomer.feature.statement.StatementActivity
import addam.com.my.chinlaicustomer.feature.statement.StatementActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Addam on 7/1/2019.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(SalesLoginModule::class)])
    abstract fun bindSalesLoginActivity(): SalesLoginActivity

    @ContributesAndroidInjector(modules = [(DashboardActivityModule::class)])
    abstract fun bindDashboardActivity() : DashboardActivity

    @ContributesAndroidInjector(modules = [(ProfileActivityModule::class)])
    abstract fun bindProfileActivity() : ProfileActivity

    @ContributesAndroidInjector(modules = [(ResetPasswordModule::class)])
    abstract fun bindResetPasswordActivity() : ResetPasswordActivity

    @ContributesAndroidInjector(modules = [(MapActivityModule::class)])
    abstract fun bindMapActivity() : MapActivity

    @ContributesAndroidInjector(modules = [(DestinationActivityModule::class)])
    abstract fun bindDestinationActivity(): DestinationActivity

    @ContributesAndroidInjector(modules = [(ProductListActivityModule::class)])
    abstract fun bindProductListActivity(): ProductListActivity

    @ContributesAndroidInjector(modules = [(ProductDetailActivityModule::class)])
    abstract fun bindProductDetailActivity(): ProductDetailActivity

    @ContributesAndroidInjector(modules = [(CartActivityModule::class)])
    abstract fun bindCartActivity(): CartActivity

    @ContributesAndroidInjector(modules = [(StatementActivityModule::class)])
    abstract fun bindStatementActivity(): StatementActivity

    @ContributesAndroidInjector(modules = [(CustomerListModule::class)])
    abstract fun bindCustomerListActivity(): CustomerListActivity

    @ContributesAndroidInjector(modules = [(InvoiceListModule::class)])
    abstract fun bindInvoiceListActivity(): InvoiceListActivity

    @ContributesAndroidInjector(modules = [(MyOrderListActivityModule::class)])
    abstract fun bindMyOrderActivity(): MyOrderListActivity

    @ContributesAndroidInjector(modules = [(MyOrderDetailActivityModule::class)])
    abstract fun bindMyOrderDetailActivity(): MyOrderDetailActivity

    @ContributesAndroidInjector(modules = [(CustomerDetailModule::class)])
    abstract fun bindCustomerDetailActivity(): CustomerDetailActivity

    @ContributesAndroidInjector(modules = [(InvoiceDetailModule::class)])
    abstract fun bindInvoiceDetailActivity(): InvoiceDetailActivity

    @ContributesAndroidInjector(modules = [(ErrorActivityModule::class)])
    abstract fun bindErrorActivityActivity(): ErrorActivity

    @ContributesAndroidInjector(modules = [(SalesOrderActivityModule::class)])
    abstract fun bindSalesOrderActivity(): SalesOrderActivity

    @ContributesAndroidInjector(modules = [(ViewImageActivityModule::class)])
    abstract fun bindViewImageActivity(): ViewImageActivity
}