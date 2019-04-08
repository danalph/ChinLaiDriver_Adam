package addam.com.my.chinlaicustomer.feature.error

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import android.os.Bundle
import dagger.android.AndroidInjection

class ErrorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_error)
    }
}
