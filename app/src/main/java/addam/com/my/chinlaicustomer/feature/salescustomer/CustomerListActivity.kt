package addam.com.my.chinlaicustomer.feature.salescustomer

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.databinding.ActivityCustomerListBinding
import addam.com.my.chinlaicustomer.databinding.NavHeaderDashboardBinding
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_customer_list.*
import kotlinx.android.synthetic.main.layout_customer_list.*
import javax.inject.Inject

class CustomerListActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var viewModel: CustomerListViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var binding: ActivityCustomerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_list)
        binding.viewModel = viewModel

        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = viewModel.name.get().toString()

        setSupportActionBar(toolbar)
        setupView()

    }

    private fun setupView(){
        KeyboardManager.hideKeyboard(this)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.my_customer_list)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        if(appPreference.getSalesId() != "0"){
            nav_view.menu.findItem(R.id.customers).isVisible = true
            nav_view.menu.findItem(R.id.profile).isVisible = false
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        setNavigation(item, appPreference, this@CustomerListActivity::class.java.simpleName)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
