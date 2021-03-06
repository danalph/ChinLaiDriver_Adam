package addam.com.my.chinlaicustomer.core

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.util.PermissionHelper
import addam.com.my.chinlaicustomer.feature.dashboard.DashboardActivity
import addam.com.my.chinlaicustomer.feature.invoice.InvoiceListActivity
import addam.com.my.chinlaicustomer.feature.myorderlist.MyOrderListActivity
import addam.com.my.chinlaicustomer.feature.profile.ProfileActivity
import addam.com.my.chinlaicustomer.feature.salescustomer.CustomerListActivity
import addam.com.my.chinlaicustomer.feature.saleshistory.ItemSalesPriceHistoryActivity
import addam.com.my.chinlaicustomer.feature.statement.StatementActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

/**
 * Created by Addam on 7/1/2019.
 */
open class BaseActivity: AppCompatActivity(), PermissionHelper.PermissionSuccessCallback {
    private val permissionHelper = PermissionHelper(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

//        if (toolbar == null) {
//            Timber.e { "Toolbar is null" }
//        }
//        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupListeners()
    }

    private fun setupListeners() {
//        applicationContext.registerReceiver(connectionReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
//        connectionReceiver.setListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun startActivity(from: Context, to: Class<*>, parameters: HashMap<Router.Parameter, Any?> = hashMapOf(), clearHistory: Boolean = false, singleTask: Boolean = false) {
        val intent = Intent(from, to)
        if (singleTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
        if (clearHistory) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        if (parameters.isNotEmpty()) {
            intent.putExtras(parameters.bundle)
        }
        startActivity(intent)
    }

    fun startActivity(from: Context, to: Class<*>, parameters: HashMap<Router.Parameter, Any?> = hashMapOf(), clearHistory: Boolean = false, singleTask: Boolean = false, transition: Bundle) {
        val intent = Intent(from, to)
        if (singleTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
        if (clearHistory) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        if (parameters.isNotEmpty()) {
            intent.putExtras(parameters.bundle)
        }
        if (transition != Bundle.EMPTY) {
            startActivity(intent, transition)
        } else {
            startActivity(intent)
        }
    }

    fun startActivityForResult(from: Context, to: Class<*>, parameters: HashMap<Router.Parameter, Any?> = hashMapOf(), requestCode: Int, singleTask: Boolean = false) {
        val intent = Intent(from, to)
        if (singleTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
        if (parameters.isNotEmpty()) {
            intent.putExtras(parameters.bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    fun startActivityForResult(from: Context, to: Class<*>, parameters: HashMap<Router.Parameter, Any?> = hashMapOf(), requestCode: Int, singleTask: Boolean = false, transition: Bundle) {
        val intent = Intent(from, to)
        if (singleTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
        if (parameters.isNotEmpty()) {
            intent.putExtras(parameters.bundle)
        }
        if (transition != Bundle.EMPTY) {
            startActivityForResult(intent, requestCode, transition)
        } else {
            startActivityForResult(intent, requestCode)
        }
    }

    // use this to check for permission
    protected fun getPermission(type: PermissionType, requestCode: Int) {
        when (type) {
            PermissionType.CAMERA -> {
                if (!permissionHelper.checkPermission(PermissionHelper.PICTURE_PERMISSIONS)) {
                    permissionHelper.requestPermission(requestCode)
                } else {
                    runOperation(requestCode)
                }
            }
            PermissionType.MAPS -> {
                if (!permissionHelper.checkPermission(PermissionHelper.MAPS_PERMISSION)) {
                    permissionHelper.requestPermission(requestCode)
                } else {
                    runOperation(requestCode)
                }
            }
            PermissionType.PHONE_CALL -> {
                if (!permissionHelper.checkPermission(PermissionHelper.PHONE_CALL_PERMISSIONS)) {
                    permissionHelper.requestPermission(requestCode)
                } else {
                    runOperation(requestCode)
                }
            }
            PermissionType.GALLERY -> {
                if (!permissionHelper.checkPermission(PermissionHelper.GALLERY_PERMISSION)) {
                    permissionHelper.requestPermission(requestCode)
                } else {
                    runOperation(requestCode)
                }
            }
        }
    }

    override fun runOperation(requestCode: Int) {

    }

    protected enum class PermissionType {
        PHONE_CALL,
        MAPS,
        CAMERA,
        GALLERY
    }

//    protected fun showLoading(isShown: Boolean?, string: Any = R.string.progress_bar_message) {
//        if (!isFinishing) {
//            if (isShown!!) {
//                if (progressBarDialog == null) {
//                    createProgressBar(string)
//                }
//                progressBarDialog?.show()
//            } else {
//                if (progressBarDialog == null) {
//                    createProgressBar(string)
//                }
//                progressBarDialog?.dismiss()
//            }
//        }
//    }

    fun setNavigation(item: MenuItem, appPreference: AppPreference, className: String){
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.btn_browse_product, R.id.sales_products -> {
                if(className != DashboardActivity::class.java.simpleName){
                    startActivity(this, Router.getClass(Router.Destination.DASHBOARD),clearHistory = true)
                    overridePendingTransition(0,0)
                }
            }
            R.id.btn_my_order, R.id.customer_order -> {
                if(className != MyOrderListActivity::class.java.simpleName){
                    startActivity(this, Router.getClass(Router.Destination.MY_ORDER), clearHistory = true)
                    overridePendingTransition(0,0)
                }
            }
            R.id.btn_my_invoice, R.id.customer_invoices -> {
                if(className != InvoiceListActivity::class.java.simpleName){
                    startActivity(this, Router.getClass(Router.Destination.INVOICE), clearHistory = true)
                    overridePendingTransition(0,0)
                }
            }
            R.id.btn_my_statement, R.id.customer_statement -> {
                if(className != StatementActivity::class.java.simpleName){
                    startActivity(this, Router.getClass(Router.Destination.STATEMENT), clearHistory = true)
                    overridePendingTransition(0,0)
                }
            }
            R.id.profile, R.id.sales_profile -> {
                if(className != ProfileActivity::class.java.simpleName){
                    startActivity(this, Router.getClass(Router.Destination.PROFILE))
                    overridePendingTransition(0,0)
                }
            }
            R.id.btn_item_sales_price_history, R.id.customer_item_sales_price_history ->{
                if(className != ItemSalesPriceHistoryActivity::class.java.simpleName){
                    startActivity(this, Router.getClass(Router.Destination.SEARCH_PRODUCT))
                    overridePendingTransition(0,0)
                }
            }
            R.id.sales_customer ->{
                if(className != CustomerListActivity::class.java.simpleName){
                    startActivity(this, Router.getClass(Router.Destination.CUSTOMER_LIST), clearHistory = true)
                    overridePendingTransition(0,0)
                }
            }
            R.id.logout, R.id.sales_logout -> {
                appPreference.setLoggedIn(false)
                appPreference.logout()
                appPreference.resetSales()
                startActivity(this, Router.getClass(Router.Destination.LOGIN), clearHistory = true)
            }
        }
    }

    fun setupNavigationLayout(view: NavigationView, appPreference: AppPreference){
        if(appPreference.getSalesId() != "0"){
//            view.menu.findItem(R.id.customers).isVisible = true
            view.menu.setGroupVisible(R.id.menu_customer, false)
            view.menu.setGroupVisible(R.id.menu_sales, true)

            if(appPreference.getCustomerName().isNotEmpty()){
                view.menu.findItem(R.id.customer_name).title = appPreference.getCustomerName()
            }

        }else{
            view.menu.setGroupVisible(R.id.menu_customer, true)
            view.menu.setGroupVisible(R.id.menu_sales, false)
        }
    }
}