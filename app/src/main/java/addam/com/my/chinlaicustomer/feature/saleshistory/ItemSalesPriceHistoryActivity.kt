package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityItemSalesPriceHistoryBinding
import addam.com.my.chinlaicustomer.databinding.NavHeaderDashboardBinding
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.Product
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_item_sales_price_history.*
import kotlinx.android.synthetic.main.content_item_sales_price_history.*
import kotlinx.android.synthetic.main.layout_item_sales_price_history.*
import javax.inject.Inject

class ItemSalesPriceHistoryActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, ItemSalesPriceHistoryViewModel.Callback {
    override fun onSearchClicked() {
        KeyboardManager.hideKeyboard(this)
    }

    @Inject
    lateinit var viewModel: ItemSalesPriceHistoryViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var adapter: ItemSalesPriceAdapter
    var list = listOf<Product>()

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
        setupRecyclerView()
        setupEvents()
    }

    private fun setupEvents() {
        viewModel.products.observe(this){
            it?:return@observe
            adapter.run {
                this.models = it.toMutableList()
                notifyDataSetChanged()
            }
        }

        viewModel.startActivityEvent.observe(this@ItemSalesPriceHistoryActivity, object : StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@ItemSalesPriceHistoryActivity, Router.getClass(data.to), data.parameters, clearHistory = false)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@ItemSalesPriceHistoryActivity, Router.getClass(data.to), data.parameters, clearHistory = false)
            }

        })
    }

    private fun setupRecyclerView() {
        adapter = ItemSalesPriceAdapter(list.toMutableList(), object : ItemSalesPriceAdapter.OnItemSelectListener {
            override fun onItemSelected(item: Product) {
//                Toast.makeText(baseContext,item.description_1, Toast.LENGTH_SHORT).show()
//                val newFragment = DatePickerFragment()
//                newFragment.show(supportFragmentManager, "datePicker")
                val builder = DatePickerBuilder(this@ItemSalesPriceHistoryActivity,
                    OnSelectDateListener { viewModel.onDateSelected(it, item) }).pickerType(CalendarView.RANGE_PICKER)
                val datePicker = builder.build()
                datePicker.show()
            }

        })

        history_list.layoutManager = LinearLayoutManager(this)
        history_list.adapter = adapter
    }

    private fun setupView() {
        viewModel.callback = this
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
