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
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.DecimalFormat

class ProductDetailViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){

    var counter = ObservableInt(1)

    var price = ObservableString("")


    val detailResponse = MutableLiveData<ProductDetailResponse>()

    val startActivityEvent: StartActivityEvent = StartActivityEvent()

    val isLoading = ObservableBoolean()

    private var originalPrice = ""

    fun getDetail(id: String){
        isLoading.set(true)
        generalRepository.getProductDetail(id).compose(schedulerProvider.getSchedulersForSingle()).subscribeBy(
            onSuccess = {
                detailResponse.postValue(it)
                originalPrice = it.data.product.refPrice
                setPrice()
                isLoading.set(false)
            },
            onError = {
                isLoading.set(false)
            }
        )
    }

    private fun setPrice(){
        val format = DecimalFormat("#,###,###,###.00")
        price.set(format.format(originalPrice.toDouble() * counter.get()))
    }

    fun onPlusClick(){
        if (counter.get() >= 1){
            counter.set(counter.get() + 1)
            setPrice()
        }

    }

    fun onMinusClick(){
        if (counter.get() > 1){
            counter.set(counter.get() - 1)
            setPrice()
        }
    }

    fun onAddToCart(){
        isLoading.set(true)
        Completable.fromAction {
            val product = detailResponse.value?.data?.product
            val cart = Cart(id = 0, productId = product?.id?.toLong()!!,
                productName = product.description1,
                productPrice = product.refPrice,
                productQuantity = counter.get(),
                productImagePath = if (detailResponse.value?.data?.productImages.isNullOrEmpty()) "" else detailResponse.value?.data?.productImages!![0],
                customerId = appPreference.getUser().id)
            databaseRepository.addToCart(cart)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                    isLoading.set(false)
                    startActivityEvent.value = StartActivityModel(
                        Router.Destination.CART , hasResults = false, clearHistory = false)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    isLoading.set(false)
                }
            })

    }

    fun onOpenCart(){
        startActivityEvent.value = StartActivityModel(Router.Destination.CART, hasResults = false, clearHistory = false)
    }
}