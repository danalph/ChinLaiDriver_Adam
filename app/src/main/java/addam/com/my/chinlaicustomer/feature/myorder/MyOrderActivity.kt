package addam.com.my.chinlaicustomer.feature.myorder

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.databinding.ActivityMyOrderBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackButtonModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.AndroidInjection
import javax.inject.Inject

class MyOrderActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MyOrderViewModel

    @Inject
    lateinit var appPreference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityMyOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_order)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackButtonModel("My Order List", true, false,R.drawable.ic_shopping_cart)

        setupView()
        setupObserver()
    }

    private fun setupObserver() {

    }

    private fun setupView() {

    }
}
