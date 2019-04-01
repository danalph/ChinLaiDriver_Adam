package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityInvoiceListBinding
import addam.com.my.chinlaicustomer.databinding.NavHeaderDashboardBinding
import addam.com.my.chinlaicustomer.rest.model.Invoices
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
import android.view.View
import com.jakewharton.rxbinding2.widget.textChanges
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_invoice_list.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_invoice_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class InvoiceListActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, InvoiceAdapter.OnItemMonthClickListener,
    InvoiceListItemAdapter.OnItemClickListener {
    @Inject
    lateinit var viewModel: InvoiceListViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var binding: ActivityInvoiceListBinding

    lateinit var adapter: InvoiceAdapter
    lateinit var searchAdapter: InvoiceListItemAdapter
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice_list)
        binding.viewModel = viewModel

        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = viewModel.name.get().toString()

        setSupportActionBar(toolbar)
        setupView()
        setupRecyclerView()
        setupEvents()
    }

    private fun setupEvents() {
        viewModel.startActivityEvent.observe(this@InvoiceListActivity, object : StartActivityEvent.StartActivityObserver {
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@InvoiceListActivity, Router.getClass(data.to), data.parameters, data.clearHistory)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@InvoiceListActivity, Router.getClass(data.to), data.parameters, data.clearHistory)
            }

        })
    }

    @SuppressLint("CheckResult")
    private fun setupRecyclerView() {
        invoice_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = InvoiceAdapter(viewModel.dummyData(), this)

        searchAdapter = InvoiceListItemAdapter(viewModel.oldFilteredList, this)
        invoice_list.adapter = searchAdapter

        et_invoice_search.textChanges()
            .debounce (200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(it.isNotEmpty()){
                    invoice_list.adapter = searchAdapter
                    viewModel.search(it.toString())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            val diffResult = DiffUtil.calculateDiff(ListInvoiceDiffUtilCallback(viewModel.oldFilteredList, viewModel.filteredList))
                            viewModel.oldFilteredList.clear()
                            viewModel.oldFilteredList.addAll(viewModel.filteredList)
                            diffResult.dispatchUpdatesTo(invoice_list.adapter as InvoiceListItemAdapter)
                        }.addTo(disposable)
                }
                else {
                    viewModel.oldFilteredList.clear()
                    viewModel.oldFilteredList.addAll(viewModel.originalList)
                    invoice_list.adapter = adapter
                }
            }
    }

    private fun setupView() {
        KeyboardManager.hideKeyboard(this)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.my_invoice)
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

        if(appPreference.getCustomerName().isNotEmpty()){
            current_customer.text = appPreference.getCustomerName()
            layout_nav_customer.visibility = View.VISIBLE
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        setNavigation(item, appPreference, this@InvoiceListActivity::class.java.simpleName)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemMonthClicked(position: Int, item: Invoices) {
        viewModel.startActivity(item)
    }

    override fun onItemClicked(p1: Int, item: Invoices) {
        viewModel.startActivity(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
