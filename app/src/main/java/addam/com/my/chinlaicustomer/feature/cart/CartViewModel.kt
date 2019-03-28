package addam.com.my.chinlaicustomer.feature.cart

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.GenericSingleEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.Cart
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.BranchesResponse
import addam.com.my.chinlaicustomer.rest.model.CreateOrderRequest
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat

class CartViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){

    val startActivityEvent = StartActivityEvent()
    val totalPrice = ObservableString("")
    val cartItems = MutableLiveData<List<Cart>>()
    val branches = MutableLiveData<BranchesResponse>()
    val event = GenericSingleEvent()
    val eventDelete = GenericSingleEvent()

    init {
        getBranches()
        getCartList()
    }

    private fun getCartList() {
        databaseRepository.getCart().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { items ->
                run {
                    Timber.d { "item size is $items" }
                    cartItems.postValue(items)
                }
            }
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

    fun setPrice(cartList: ArrayList<Cart>){
        var price  = 0.0
        val format = DecimalFormat("#,###,###,###.00")
        for (cart in cartList){
            if (cart.isChecked){
                price += cart.productPrice.toDouble() * cart.productQuantity
            }
        }
        totalPrice.set(format.format(price))
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
                    if (it.status){
                        Completable.fromAction{
                            databaseRepository.clearTable()
                        }.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(object: CompletableObserver{
                                override fun onComplete() {
                                    startActivityEvent.value = StartActivityModel(
                                        Router.Destination.DASHBOARD , hasResults = false, clearHistory = false)
                                }

                                override fun onSubscribe(d: Disposable) {

                                }

                                override fun onError(e: Throwable) {

                                }
                            })
                    }

                },
                onError = {

                }
            )
    }

    fun onDeleteItem(item: Cart){
        Completable.fromAction{
            databaseRepository.deleteCart(item)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: CompletableObserver{
                override fun onComplete() {
                    eventDelete.value = true
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }
            })
    }

}