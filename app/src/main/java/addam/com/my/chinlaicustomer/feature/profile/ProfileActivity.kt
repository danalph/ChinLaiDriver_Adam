package addam.com.my.chinlaicustomer.feature.profile

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityProfileBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import dagger.android.AndroidInjection
import javax.inject.Inject

class ProfileActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: ProfileViewModel

    @Inject
    lateinit var appPreference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityProfileBinding= DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackModel(getString(R.string.title_activity_profile),true, this::onBackPressed)
        binding.setLifecycleOwner(this)

        setupEvents()
    }

    private fun setupEvents() {
        try{
            val user = appPreference.getUser()
            val address = user.address1 + ", " + user.address2 + ", " + user.address3 + ", " + user.areaName + ", " + user.postcode + ", " + user.stateName
            viewModel.name.set(user.name)
            viewModel.contact.set(user.person_contact)
            viewModel.address.set(address)
        }catch (e: Exception){
            Timber.e { e.toString() }
        }

        viewModel.startActivityEvent.observe(this, object: StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@ProfileActivity, Router.getClass(data.to), clearHistory = data.clearHistory)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@ProfileActivity, Router.getClass(data.to), clearHistory = data.clearHistory)
            }

        })

    }
}
