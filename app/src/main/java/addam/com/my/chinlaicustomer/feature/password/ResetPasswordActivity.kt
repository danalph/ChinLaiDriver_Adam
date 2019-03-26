package addam.com.my.chinlaicustomer.feature.password

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.event.FinishActivityEvent
import addam.com.my.chinlaicustomer.core.event.FinishActivityEventModel
import addam.com.my.chinlaicustomer.databinding.ActivityResetPasswordBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.TextView
import android.widget.Toast
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_reset_password.*
import javax.inject.Inject

class ResetPasswordActivity : BaseActivity(), ResetPasswordViewModel.ResetPasswordCallback {
    @Inject
    lateinit var viewModel: ResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val mBinding: ActivityResetPasswordBinding =  DataBindingUtil.setContentView(this@ResetPasswordActivity, R.layout.activity_reset_password)
        mBinding.toolbarModel = ToolbarWithBackModel(getString(R.string.title_activity_reset_password),true, this::onBackPressed)

        mBinding.viewModel = viewModel

        setupEvents()
    }

    private fun setupEvents() {
        viewModel.callback = this
        viewModel.finishActivityEvent.observe(this@ResetPasswordActivity, object: FinishActivityEvent.FinishActivityObserver{
            override fun onFinishActivity(data: FinishActivityEventModel) {
                finish()
            }

            override fun onFinishActivityForResult(data: FinishActivityEventModel) {
                finish()
            }

        })
    }

    override fun isError(error: String) {
        val snackbar: Snackbar = Snackbar.make(main_content, error, Snackbar.LENGTH_SHORT)
        val view = snackbar.view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(resources.getColor(R.color.black, null))
        }else view.setBackgroundColor(resources.getColor(R.color.black))
        val text = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        text.setTextColor(Color.parseColor("#ffffff"))
        snackbar.show()
    }

    override fun isCompleted(success: String) {
        Toast.makeText(this@ResetPasswordActivity, success, Toast.LENGTH_SHORT).show()
    }
}
