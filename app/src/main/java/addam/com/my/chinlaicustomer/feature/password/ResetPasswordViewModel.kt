package addam.com.my.chinlaicustomer.feature.password

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.event.FinishActivityEvent
import addam.com.my.chinlaicustomer.core.event.FinishActivityEventModel
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.ChangePasswordRequest
import addam.com.my.chinlaicustomer.rest.model.ChangePasswordResponse
import addam.com.my.chinlaicustomer.rest.model.PasswordEncryptResponse
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.Validator
import addam.com.my.chinlaicustomer.utilities.observe
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.github.ajalt.timberkt.Timber
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy

class ResetPasswordViewModel(
    private val schdulerProvider: SchedulerProvider,
    private val generalRepository: GeneralRepository, private val appPreference: AppPreference): ViewModel() {

    var newPassword = ObservableString("")
    var newPasswordConfirm = ObservableString("")
    var isLoading = ObservableBoolean(false)
    lateinit var callback: ResetPasswordCallback

    var newPasswordVerify = ObservableBoolean(false)
    var newPasswordConfirmVerify = ObservableBoolean(false)
    var isFormValid = ObservableBoolean(false)

    var finishActivityEvent = FinishActivityEvent()

    init{
        newPassword.observe().map { Validator.ReasonValidator.validatePassword(it) }.subscribe { newPasswordVerify.set(it) }
        newPasswordConfirm.observe().map { Validator.ReasonValidator.validatePassword(it) }.subscribe { newPasswordConfirmVerify.set(it) }
        io.reactivex.Observable.combineLatest(newPasswordVerify.observe(), newPasswordConfirmVerify.observe(),
            BiFunction { a: Boolean, b: Boolean -> a&&b }).subscribe { isFormValid.set(it) }
    }

    private fun getPasswordEncrypt(password: String): Single<PasswordEncryptResponse> {
        return generalRepository.getPasswordEncrypt(password).compose(schdulerProvider.getSchedulersForSingle())
    }

    private fun getResetPasswordAPI(passwords: ChangePasswordRequest): Single<ChangePasswordResponse> =
        generalRepository.getChangePassword(appPreference.getUser().id, passwords).compose(schdulerProvider.getSchedulersForSingle())

    private fun encryptNewPassword(){
        isLoading.set(true)
        getPasswordEncrypt(newPassword.get().toString()).subscribeBy(onSuccess = {
            if(it.status){
                encryptConfirmPassword(it.data.keys)
            }else {
                Timber.e { it.message }
                isLoading.set(false)
                callback.isError(it.message)
            }
        }, onError = {
            isLoading.set(false)
            callback.isError(it.message.toString())
            Timber.e{it.message.toString()}
        })
    }

    private fun encryptConfirmPassword(newPassword: String){
        getPasswordEncrypt(newPasswordConfirm.get().toString()).subscribeBy(onSuccess = {
            if(it.status){
                val confirmPassword = it.data.keys
                getPasswordReset(ChangePasswordRequest(newPassword, confirmPassword))
            }else {
                Timber.e { it.message }
                callback.isError(it.message)
                isLoading.set(false)
            }
        }, onError = {
            Timber.e{it.message.toString()}
            callback.isError(it.message.toString())
            isLoading.set(false)
        })
    }

    private fun getPasswordReset(passwords: ChangePasswordRequest){
        getResetPasswordAPI(passwords).subscribeBy(onSuccess = {
            if (it.status){
                finishActivityEvent.value = FinishActivityEventModel()
                callback.isCompleted(it.message)
                isLoading.set(false)
            }else {
                Timber.e { it.message }
                isLoading.set(false)
                callback.isError(it.message)
            }
        }, onError = {
            Timber.e { it.message.toString() }
            isLoading.set(false)
            callback.isError(it.message.toString())
        })
    }

    fun onResetClicked(){
        if(newPassword.get().toString() == newPasswordConfirm.get().toString()){
            encryptNewPassword()
        }else callback.isError("Please ensure both passwords are the same")
    }

    interface ResetPasswordCallback{
        fun isError(error: String)
        fun isCompleted(success: String)
    }
}