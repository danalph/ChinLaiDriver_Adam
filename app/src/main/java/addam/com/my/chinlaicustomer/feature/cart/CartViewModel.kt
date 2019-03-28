package addam.com.my.chinlaicustomer.feature.cart

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.event.GenericSingleEvent
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.Cart
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.BranchesResponse
import addam.com.my.chinlaicustomer.rest.model.CreateOrderRequest
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import com.github.ajalt.timberkt.Timber
import io.reactivex.rxkotlin.subscribeBy

class CartViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){

    val totalPrice = ObservableInt(0)
    val cartTtems = MutableLiveData<ArrayList<Cart>>()
    val branches = MutableLiveData<BranchesResponse>()
    val event = GenericSingleEvent()

    init {
        getBranches()
        getCartList()
    }

    private fun getCartList() {
        cartTtems.postValue(databaseRepository.getCart() as ArrayList<Cart>?)
    }

    private fun getBranches(){
        generalRepository.getBranches(appPreference.getUser().id)
            .compose(schedulerProvider.getSchedulersForSingle()).subscribeBy(
                onSuccess = {
                    branches.postValue(it)
                },
                onError = {
                    Timber.d(it)
                }
            )
    }

    fun getData(cartList: ArrayList<Cart>){
        var price  = 0
        for (cart in cartList){
            if (cart.isChecked){
                price += cart.productPrice.toInt() * cart.productQuantity
            }
        }
        totalPrice.set(price)
    }

    fun onPlaceOrder(){
        event.value = true
    }

    fun onConfirmOrder(list: ArrayList<Cart>, branchId: String, totalPrice: String, salesPersonId: String){
        val listOfGoods = arrayListOf<CreateOrderRequest.Good>()
        for (item in list){
            val goods = CreateOrderRequest.Good(item.id.toString(),item.productQuantity.toString())
            listOfGoods.add(goods)
        }
        val orderRequest = CreateOrderRequest(branchId, appPreference.getUser().id, listOfGoods, salesPersonId, totalPrice)
        generalRepository.createOrder(orderRequest).compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {

                },
                onError = {

                }
            )
    }

}