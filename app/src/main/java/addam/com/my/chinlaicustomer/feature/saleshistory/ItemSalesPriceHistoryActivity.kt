package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.databinding.ActivityItemSalesPriceHistoryBinding
import addam.com.my.chinlaicustomer.databinding.NavHeaderDashboardBinding
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_item_sales_price_history.*
import kotlinx.android.synthetic.main.layout_item_sales_price_history.*
import javax.inject.Inject

class ItemSalesPriceHistoryActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var viewModel: ItemSalesPriceHistoryViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var binding: ActivityItemSalesPriceHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_sales_price_history)
        binding.viewModel = viewModel

        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = appPreference.getUser().name
        headerBind.isSalesPerson = appPreference.getSalesId() != "0"

        setSupportActionBar(toolbar)
        setupView()
    }

    private fun setupView() {
        KeyboardManager.hideKeyboard(this)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.item_sales_price_history)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        setupNavigationLayout(nav_view, appPreference)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        setNavigation(item, appPreference, this@ItemSalesPriceHistoryActivity::class.java.simpleName)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
