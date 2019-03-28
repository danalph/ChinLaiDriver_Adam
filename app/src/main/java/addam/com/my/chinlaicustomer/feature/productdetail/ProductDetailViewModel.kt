package addam.com.my.chinlaicustomer.feature.productdetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.Cart
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.ProductDetailResponse
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){

    var counter = ObservableInt(1)

    val detailResponse = MutableLiveData<ProductDetailResponse>()

    val startActivityEvent: StartActivityEvent = StartActivityEvent()

    fun getDetail(id: String){
        generalRepository.getProductDetail(id).compose(schedulerProvider.getSchedulersForSingle()).subscribeBy(
            onSuccess = {
                detailResponse.postValue(it)
            },
            onError = {

            }
        )
    }

    fun onPlusClick(){
        if (counter.get() >= 1)
            counter.set(counter.get() + 1)
    }

    fun onMinusClick(){
        if (counter.get() > 1)
            counter.set(counter.get() - 1)
    }

    fun onAddToCart(){
        Completable.fromAction {
            val product = detailResponse.value?.data?.product
            val cart = Cart(id = 0, productId = product?.id?.toLong()!!,
                productName = product.description1,
                productPrice = product.refPrice,
                productQuantity = counter.get(),
                productImagePath = detailResponse.value?.data?.productImages!![0])
            databaseRepository.addToCart(cart)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                    startActivityEvent.value = StartActivityModel(
                        Router.Destination.CART , hasResults = false, clearHistory = false)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }
            })

    }
}