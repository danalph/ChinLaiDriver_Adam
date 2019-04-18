package addam.com.my.chinlaicustomer.feature.salescustomer

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityCustomerListBinding
import addam.com.my.chinlaicustomer.databinding.NavHeaderDashboardBinding
import addam.com.my.chinlaicustomer.rest.model.Customers
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.jakewharton.rxbinding2.widget.textChanges
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_customer_list.*
import kotlinx.android.synthetic.main.content_customer_list.*
import kotlinx.android.synthetic.main.layout_customer_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CustomerListActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, CustomerListAdapter.OnItemClickListener,
                            CustomerListViewModel.CustomerListViewModelCallback{
    @Inject
    lateinit var viewModel: CustomerListViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var binding: ActivityCustomerListBinding

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_list)
        binding.viewModel = viewModel
        viewModel.callback = this@CustomerListActivity

        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = viewModel.name.get().toString()

        setSupportActionBar(toolbar)
        setupView()
        setupEvents()
    }

    private fun setupEvents() {
        viewModel.startActivityEvent.observe(this@CustomerListActivity, object: StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@CustomerListActivity, Router.getClass(data.to), data.parameters, data.clearHistory)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@CustomerListActivity, Router.getClass(data.to), data.parameters, data.clearHistory)
            }

        })
    }

    override fun updateUI() {
        setupRecyclerView()
    }

    @SuppressLint("CheckResult")
    fun setupRecyclerView() {
        rv_customer.layoutManager = LinearLayoutManager(this)
        rv_customer.adapter = CustomerListAdapter(viewModel.oldFilteredList, this)

        txt_search.textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe{
                viewModel.search(it.toString())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{
                        val diffResult = DiffUtil.calculateDiff(ListDiffUtilCallback(viewModel.oldFilteredList, viewModel.filteredList))
                        viewModel.oldFilteredList.clear()
                        viewModel.oldFilteredList.addAll(viewModel.filteredList)
                        diffResult.dispatchUpdatesTo(rv_customer.adapter as CustomerListAdapter)
                    }.addTo(disposable)
            }
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
        }

        if(appPreference.getCustomerName().isNotEmpty()){
            current_customer.text = appPreference.getCustomerName()
            layout_nav_customer.visibility = View.VISIBLE
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        setNavigation(item, appPreference, this@CustomerListActivity::class.java.simpleName)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemClicked(item: Customers) {
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_select_customer)
            .setMessage(item.companyName)
            .setPositiveButton("View Profile"){_ ,_ ->
                viewModel.setCustomer(item)
            }
            .setNegativeButton(R.string.confirm){_,_ ->
                appPreference.setCustomerId(item)
                startActivity(this, Router.getClass(Router.Destination.DASHBOARD),clearHistory = true)
            }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
