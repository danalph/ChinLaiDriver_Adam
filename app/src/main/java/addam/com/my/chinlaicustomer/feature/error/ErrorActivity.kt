package addam.com.my.chinlaicustomer.feature.error

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityErrorBinding
import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.AndroidInjection
import javax.inject.Inject

class ErrorActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: ErrorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityErrorBinding = DataBindingUtil.setContentView(this, R.layout.activity_error)
        binding.viewModel = viewModel

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.startActivityEvent.observe(this, object: StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@ErrorActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@ErrorActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }
        })
    }
}
