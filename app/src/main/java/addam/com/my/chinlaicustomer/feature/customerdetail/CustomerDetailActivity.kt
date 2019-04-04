package addam.com.my.chinlaicustomer.feature.customerdetail

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.databinding.ActivityCustomerDetailBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.AndroidInjection
import javax.inject.Inject

class CustomerDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: CustomerDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val binding: ActivityCustomerDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer_detail)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackModel(getString(R.string.title_activity_customer_detail),true, this::onBackPressed)
        binding.lifecycleOwner = this

        getIntents()
    }

    private fun getIntents() {
        viewModel.getCustomer(intent.getStringExtra(Router.Parameter.CUST_ID.name), intent.getStringExtra(Router.Parameter.CUST_ROC.name))
    }

}
