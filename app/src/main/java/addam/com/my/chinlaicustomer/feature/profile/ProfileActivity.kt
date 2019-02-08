package addam.com.my.chinlaicustomer.feature.profile

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
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
            viewModel.name.set(user.name)
            viewModel.contact.set(user.contact)
            viewModel.address.set(user.address)
        }catch (e: Exception){
            Timber.e { e.toString() }
        }

    }
}
