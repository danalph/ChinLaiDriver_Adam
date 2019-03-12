package addam.com.my.chinlaicustomer.feature.password

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.databinding.ActivityResetPasswordBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.AndroidInjection
import javax.inject.Inject

class ResetPasswordActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: ResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val mBinding: ActivityResetPasswordBinding =  DataBindingUtil.setContentView(this@ResetPasswordActivity, R.layout.activity_reset_password)
        mBinding.toolbarModel = ToolbarWithBackModel(getString(R.string.title_activity_reset_password),true, this::onBackPressed)

        mBinding.viewModel = viewModel
    }
}
