package addam.com.my.chinlaicustomer.feature.salesperson

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.SalesData
import addam.com.my.chinlaicustomer.rest.model.SalesLoginResponse
import addam.com.my.chinlaicustomer.rest.model.UserLoginRequest
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.Validator
import addam.com.my.chinlaicustomer.utilities.observe
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.github.ajalt.timberkt.Timber
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by owner on 22/03/2019
 */

class SalesLoginViewModel(
    private val schdulerProvider: SchedulerProvider,
    private val generalRepository: GeneralRepository, private val appPreference: AppPreference) : ViewModel(){

    var username = ObservableString("")
    var password = ObservableString("")
    val startPinActivityEvent: StartActivityEvent = StartActivityEvent()
    lateinit var loginCallback: SalesLoginViewModel.LoginCallback

    var isUsernameValid = ObservableBoolean(false)
    var isPasswordValid = ObservableBoolean(false)
    var isValid = ObservableBoolean(false)
    var isLoading = ObservableBoolean(false)

    init {
        username.observe().map { Validator.ReasonValidator.validate(it) }.subscribe { isUsernameValid.set(it) }
        password.observe().map { Validator.ReasonValidator.validate(it) }.subscribe {isPasswordValid.set(it) }
        io.reactivex.Observable.combineLatest(isUsernameValid.observe(), isPasswordValid.observe(),
            BiFunction { a: Boolean, b: Boolean -> a&&b }).subscribe { isValid.set(it) }

        if(appPreference.isLoggedIn()){
            startPinActivityEvent.value = StartActivityModel(Router.Destination.DASHBOARD, clearHistory = true, hasResults = false)
        }
    }

    private fun callUserLogin(): Single<SalesLoginResponse> {
        val userLoginRequest = UserLoginRequest(username.get().toString(), password.get().toString())
        return generalRepository.getSalesLogin(userLoginRequest).compose(schdulerProvider.getSchedulersForSingle())
    }

    fun onLoginClicked(){
        isLoading.set(true)
        callUserLogin().subscribeBy(onSuccess = {
            if(it.status){
                appPreference.setLoggedIn(true)
                saveUserPreference(it.data)
                startPinActivityEvent.value = StartActivityModel(Router.Destination.DASHBOARD,
                    hashMapOf(Pair(Router.Parameter.USERNAME, it.data.firstName)),
                    hasResults = false, clearHistory = true)
            }else {
                isLoading.set(false)
                loginCallback.loginError()
            }
        }, onError = {
            isLoading.set(false)
            Timber.e{it.message.toString()}
        })
//        loginUser("2103918230")
    }

    fun onForgotClicked(){
//        TODO go to forgot password
    }

    fun onSalesClicked(){
        startPinActivityEvent.value = StartActivityModel(Router.Destination.LOGIN, hasResults = false, clearHistory = true)
    }

    private fun loginUser(keys: String) {
        startPinActivityEvent.value = StartActivityModel(
            Router.Destination.DASHBOARD,
            hashMapOf(Pair(Router.Parameter.USERNAME, "Herpderp")),
            hasResults = false, clearHistory = true)
//        callUserLogin(keys).subscribeBy(onSuccess = {
//            if(it.status){
//                appPreference.setLoggedIn(true)
//                saveUserPreference(it.data)
//                startPinActivityEvent.value = StartActivityModel(Router.Destination.DASHBOARD,
//                    hashMapOf(Pair(Router.Parameter.USERNAME, it.data.name)),
//                    hasResults = false, clearHistory = true)
//            }else loginCallback.loginError()
//        }, onError = {
//            Timber.e{it.message.toString()}
//        })
    }

    private fun saveUserPreference(data: SalesData) {
        appPreference.setSales(data = data)
    }


    interface LoginCallback{
        fun loginError()
    }
}