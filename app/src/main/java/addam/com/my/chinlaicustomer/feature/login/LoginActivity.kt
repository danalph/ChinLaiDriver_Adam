package addam.com.my.chinlaicustomer.feature.login

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityLoginBinding
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.TextView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginViewModel.LoginCallback {
    @Inject
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var preference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewmodel = viewModel
        binding.setLifecycleOwner(this)

        setupEvents()
    }

    private fun setupEvents() {
        viewModel.loginCallback = this
        viewModel.startPinActivityEvent.observe(this, object: StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@LoginActivity, Router.getClass(data.to), data.parameters, clearHistory = true)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@LoginActivity, Router.getClass(data.to), data.parameters, clearHistory = true)
            }

        })
    }

    override fun loginError() {
        val snackbar: Snackbar = Snackbar.make(main_content, "Invalid Credential", Snackbar.LENGTH_SHORT)
        val view = snackbar.view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(resources.getColor(R.color.black, null))
        }else view.setBackgroundColor(resources.getColor(R.color.black))
        val text = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        text.setTextColor(Color.parseColor("#ffffff"))
        snackbar.show()
    }
}
