package addam.com.my.chinlaicustomer.feature.product

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.ProductList
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v4.content.ContextCompat

class ProductListViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){
    val startActivityEvent: StartActivityEvent = StartActivityEvent()
    val productsResponse = MutableLiveData<ArrayList<ProductList>>()

    init {
        //getItem()
    }

    fun getItem(context: Context){
        val productList = arrayListOf<ProductList>()
        productList.add(ProductList("1", "Item 1", "Item 1", "200", ContextCompat.getDrawable(context,R.drawable.test1)))
        productList.add(ProductList("2", "Item 2", "Item 2", "300", ContextCompat.getDrawable(context,R.drawable.test2)))
        productList.add(ProductList("3", "Item 3", "Item 3", "400", ContextCompat.getDrawable(context,R.drawable.test3)))
        productList.add(ProductList("4", "Item 4", "Item 4", "500", ContextCompat.getDrawable(context,R.drawable.test4)))
        productList.add(ProductList("5", "Item 5", "Item 5", "600", ContextCompat.getDrawable(context,R.drawable.test5)))
        productsResponse.postValue(productList)
    }

    fun onItemSelected(){
        startActivityEvent.value = StartActivityModel(Router.Destination.PRODUCT_DETAIL, hasResults = false, clearHistory = false)
    }

}