package addam.com.my.chinlaicustomer.utilities

import android.util.Patterns

/**
 * Created by Addam on 12/1/2019.
 */
open class Validator {
    class ReasonValidator{
        companion object {
            fun validate(reason: CharSequence): Boolean = reason.isNotEmpty()
            fun validateEmail(reason: CharSequence): Boolean = reason.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(reason).matches()
        }
    }
    class StatusValidator{
        companion object {
            fun isPaid(paid: String): Boolean{
                return paid == "1"
            }
        }
    }
}