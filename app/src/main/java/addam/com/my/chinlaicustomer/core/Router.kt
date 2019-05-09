package addam.com.my.chinlaicustomer.core

import addam.com.my.chinlaicustomer.feature.cart.CartActivity
import addam.com.my.chinlaicustomer.feature.customerdetail.CustomerDetailActivity
import addam.com.my.chinlaicustomer.feature.dashboard.DashboardActivity
import addam.com.my.chinlaicustomer.feature.destination.DestinationActivity
import addam.com.my.chinlaicustomer.feature.error.ErrorActivity
import addam.com.my.chinlaicustomer.feature.imageviewer.ViewImageActivity
import addam.com.my.chinlaicustomer.feature.invoice.InvoiceListActivity
import addam.com.my.chinlaicustomer.feature.invoicedetail.InvoiceDetailActivity
import addam.com.my.chinlaicustomer.feature.login.LoginActivity
import addam.com.my.chinlaicustomer.feature.map.MapActivity
import addam.com.my.chinlaicustomer.feature.myorderdetail.MyOrderDetailActivity
import addam.com.my.chinlaicustomer.feature.myorderlist.MyOrderListActivity
import addam.com.my.chinlaicustomer.feature.password.ResetPasswordActivity
import addam.com.my.chinlaicustomer.feature.productdetail.ProductDetailActivity
import addam.com.my.chinlaicustomer.feature.productlist.ProductListActivity
import addam.com.my.chinlaicustomer.feature.profile.ProfileActivity
import addam.com.my.chinlaicustomer.feature.salescustomer.CustomerListActivity
import addam.com.my.chinlaicustomer.feature.saleshistory.ItemSalesPriceHistoryActivity
import addam.com.my.chinlaicustomer.feature.salesorder.SalesOrderActivity
import addam.com.my.chinlaicustomer.feature.salesperson.SalesLoginActivity
import addam.com.my.chinlaicustomer.feature.statement.StatementActivity

/**
 * Created by Addam on 7/1/2019.
 */
class Router {
    enum class Destination {
        LOGIN,
        PROFILE,
        DASHBOARD,
        MAP,
        DESTINATION,
        RESET_PASSWORD,
        PRODUCT,
        PRODUCT_DETAIL,
        CART,
        STATEMENT,
        INVOICE,
        INVOICE_DETAIL,
        SALES_LOGIN,
        MY_ORDER,
        MY_ORDER_DETAIL,
        CUSTOMER_LIST,
        CUSTOMER_DETAIL,
        ERROR,
        SALES_ORDER,
        IMAGE_VIEWER,
        SEARCH_PRODUCT,
        HISTORY
    }

    enum class Parameter{
        USERNAME,
        PASSWORD,
        LATITUDE,
        LONGITUDE,
        COMPANY,
        DOC_NUM,
        ADDRESS,
        STATUS,
        TYPE,
        CATEGORY_ID,
        DOC_ID,
        ITEM_ID,
        ORDER_ID,
        SELECTED_TAB,
        CUST_ROC,
        CUST_ID,
        ITEM_DATE,
        ITEM_AMOUNT,
        ITEM_STATUS,
        ITEM_NUM,
        IMAGES,
        START_DATE,
        END_DATE
    }

    companion object {
        fun getClass(destination: Destination): Class<*> {
            return when (destination) {
                Destination.LOGIN -> LoginActivity::class.java
                Destination.PROFILE -> ProfileActivity::class.java
                Destination.RESET_PASSWORD -> ResetPasswordActivity::class.java
                Destination.DASHBOARD -> DashboardActivity::class.java
                Destination.MAP -> MapActivity::class.java
                Destination.DESTINATION -> DestinationActivity::class.java
                Destination.PRODUCT -> ProductListActivity::class.java
                Destination.PRODUCT_DETAIL -> ProductDetailActivity::class.java
                Destination.CART -> CartActivity::class.java
                Destination.STATEMENT -> StatementActivity::class.java
                Destination.INVOICE -> InvoiceListActivity::class.java
                Destination.INVOICE_DETAIL -> InvoiceDetailActivity::class.java
                Destination.SALES_LOGIN -> SalesLoginActivity::class.java
                Destination.CUSTOMER_LIST -> CustomerListActivity::class.java
                Destination.MY_ORDER -> MyOrderListActivity::class.java
                Destination.MY_ORDER_DETAIL -> MyOrderDetailActivity::class.java
                Destination.CUSTOMER_DETAIL -> CustomerDetailActivity::class.java
                Destination.ERROR -> ErrorActivity::class.java
                Destination.SALES_ORDER -> SalesOrderActivity::class.java
                Destination.IMAGE_VIEWER -> ViewImageActivity::class.java
                Destination.SEARCH_PRODUCT -> ItemSalesPriceHistoryActivity::class.java
                else -> {
                    TODO("Implement Default case")
                }
            }
        }
    }
}