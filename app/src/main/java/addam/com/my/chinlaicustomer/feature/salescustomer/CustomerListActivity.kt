package addam.com.my.chinlaicustomer.feature.salescustomer

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
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
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.github.ajalt.timberkt.Timber
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

class CustomerListActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, CustomerListAdapter.OnItemClickListener {
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

        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = viewModel.name.get().toString()

        setSupportActionBar(toolbar)
        setupView()
        setupRecyclerView()
    }

    @SuppressLint("CheckResult")
    private fun setupRecyclerView() {
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
            nav_view.menu.findItem(R.id.profile).isVisible = false
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        setNavigation(item, appPreference, this@CustomerListActivity::class.java.simpleName)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemClicked(item: Customers) {
        Timber.d{item.companyName}
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
