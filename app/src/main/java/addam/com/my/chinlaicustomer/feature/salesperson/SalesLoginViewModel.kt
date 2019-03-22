package addam.com.my.chinlaicustomer.feature.salesperson

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.PasswordEncryptResponse
import addam.com.my.chinlaicustomer.rest.model.UserData
import addam.com.my.chinlaicustomer.rest.model.UserLoginRequest
import addam.com.my.chinlaicustomer.rest.model.UserLoginResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.Validator
import addam.com.my.chinlaicustomer.utilities.observe
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.Single
import io.reactivex.functions.BiFunction

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
    private fun callPassswordEncryptApi(): Single<PasswordEncryptResponse> {
        return generalRepository.getPasswordEncrypt(password.get().toString()).compose(schdulerProvider.getSchedulersForSingle())
    }

    private fun callUserLogin(keys: String): Single<UserLoginResponse> {
        val userLoginRequest = UserLoginRequest(username.get().toString(), keys)
        return generalRepository.getLogin(userLoginRequest).compose(schdulerProvider.getSchedulersForSingle())
    }

    fun onLoginClicked(){
        isLoading.set(true)
//        callPassswordEncryptApi().subscribeBy(onSuccess = {
//            Timber.d{"Success for Encrypt"}
//            loginUser(it.data.keys)
//            isLoading.set(false)
////            startPinActivityEvent.value = StartActivityModel(Router.Destination.MAIN,
////                hashMapOf(Pair(Router.Parameter.USERNAME, it.name)), hasResults = false)
//        }, onError = {
//            isLoading.set(false)
//            Timber.e { it.message.toString() }
//        })
        loginUser("2103918230")
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

    private fun saveUserPreference(data: UserData) {
        appPreference.setUser(data)
    }


    interface LoginCallback{
        fun loginError()
    }
}