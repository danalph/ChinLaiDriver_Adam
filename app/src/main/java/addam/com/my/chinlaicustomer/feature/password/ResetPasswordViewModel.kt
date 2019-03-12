package addam.com.my.chinlaicustomer.feature.password

import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.Validator
import addam.com.my.chinlaicustomer.utilities.observe
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.github.ajalt.timberkt.Timber

class ResetPasswordViewModel: ViewModel() {

    var currentPassword = ObservableString("")
    var newPassword = ObservableString("")
    var newPasswordConfirm = ObservableString("")

    var currentPasswordVerify = ObservableBoolean(false)
    var newPasswordVerify = ObservableBoolean(false)
    var newPasswordConfirmVerify = ObservableBoolean(false)

    init{
        currentPassword.observe().map { Validator.ReasonValidator.validate(it) }.subscribe { currentPasswordVerify.set(it)
        Timber.d { it.toString() }}
        newPassword.observe().map { Validator.ReasonValidator.validate(it) }.subscribe { newPasswordVerify.set(it) }
        newPasswordConfirm.observe().map { Validator.ReasonValidator.validate(it) }.subscribe { newPasswordConfirmVerify.set(it) }
    }
}