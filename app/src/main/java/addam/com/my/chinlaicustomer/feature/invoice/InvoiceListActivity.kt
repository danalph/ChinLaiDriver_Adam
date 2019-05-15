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
import addam.com.my.chinlaicustomer.utilities.observe
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
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    InvoiceListItemAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener{
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = viewModel.name.get().toString()
        headerBind.isSalesPerson = appPreference.getSalesId() != "0"

        setSupportActionBar(toolbar)
        setupView()
        setupSpinner()
        setupRecyclerView()
        setupEvents()
    }
    private fun setupEvents() {

        viewModel.response.observe(this){
            it?: return@observe
            viewModel.oldFilteredList.clear()
            viewModel.originalList.addAll(it.data.INVs.toMutableList())
            adapter.run {
                viewModel.monthSortList.clear()
                viewModel.monthSortList.addAll(viewModel.sortMonth(it.data.INVs))
                notifyDataSetChanged()
            }
            searchAdapter.run {
                notifyDataSetChanged()
            }
            viewModel.isLoading.set(false)
        }

        viewModel.startActivityEvent.observe(this@InvoiceListActivity, object : StartActivityEvent.StartActivityObserver {
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@InvoiceListActivity, Router.getClass(data.to), data.parameters, data.clearHistory)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@InvoiceListActivity, Router.getClass(data.to), data.parameters, data.clearHistory)
            }

        })
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(this, R.array.filter_array, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_invoice.adapter = adapter
            }
        spinner_invoice.onItemSelectedListener = this
    }

    @SuppressLint("CheckResult")
    private fun setupRecyclerView() {
        invoice_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = InvoiceAdapter(viewModel.monthSortList, this)
        searchAdapter = InvoiceListItemAdapter(viewModel.oldFilteredList, this)
        invoice_list.adapter = adapter

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
                spinner_invoice.setSelection(0)
                }
                else {
                    viewModel.oldFilteredList.clear()
                    viewModel.oldFilteredList.addAll(viewModel.originalList)
                    invoice_list.adapter = adapter
                    spinner_invoice.setSelection(0)
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
        setupNavigationLayout(nav_view, appPreference)

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

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var status = "0"
        when (position){
            0 -> status = "all"
            1 -> status = "paid"
            2 -> status = "unpaid"
        }

        if(status != "all"){
            invoice_list.adapter = searchAdapter
            viewModel.filterStatus(status)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val diffResult = DiffUtil.calculateDiff(ListInvoiceDiffUtilCallback(viewModel.oldFilteredList, viewModel.filteredList))
                    viewModel.oldFilteredList.clear()
                    viewModel.oldFilteredList.addAll(viewModel.filteredList)
                    diffResult.dispatchUpdatesTo(invoice_list.adapter as InvoiceListItemAdapter)
                }.addTo(disposable)
        }
        else{
            viewModel.oldFilteredList.clear()
            viewModel.oldFilteredList.addAll(viewModel.originalList)
            invoice_list.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        KeyboardManager.hideKeyboard(this)
        focus_thief.requestFocus()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
