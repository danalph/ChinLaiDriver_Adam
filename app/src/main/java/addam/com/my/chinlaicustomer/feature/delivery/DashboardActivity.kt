package addam.com.my.chinlaicustomer.feature.delivery

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.BaseRecyclerViewAdapter
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityDashboardBinding
import addam.com.my.chinlaicustomer.databinding.NavHeaderDashboardBinding
import addam.com.my.chinlaicustomer.rest.model.TripsResponse
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.github.ajalt.timberkt.Timber
import com.jakewharton.rxbinding2.widget.textChanges
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class DashboardActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var viewModel: DashboardViewModel

    @Inject
    lateinit var appPreference: AppPreference

    private lateinit var adapter: DashboardAdapter

    private val trips =  arrayListOf<TripsResponse.Data.Trip>()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.viewModel = viewModel

        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = viewModel.name.get().toString()

        setSupportActionBar(toolbar)
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.tripResponse.observe(this){
            it?: return@observe
            adapter.run {
                trips.clear()
                trips.addAll(it.data.trips)
                notifyDataSetChanged()
                viewModel.tripCount.set(adapter.itemCount)
                swipe_refresh_layout.isRefreshing = false
            }
        }

        viewModel.startActivityEvent.observe(this, object : StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@DashboardActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@DashboardActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }
        })

        viewModel.errorResponse.observe(this){
            val snackbar = Snackbar.make(rv_categories, it.toString(), Snackbar.LENGTH_SHORT)
            val tv = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
            tv.setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.white))
            snackbar.show()
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun setupView(){
        KeyboardManager.hideKeyboard(this)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.category)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        setupRecyclerView()

        swipe_refresh_layout.setOnRefreshListener {
            viewModel.getTrips()
        }
    }


    private fun setupRecyclerView() {
        rv_categories.layoutManager = GridLayoutManager(this,2)
        //rv_trips.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = DashboardAdapter(trips, R.layout.dashboard_adapter_layout,object: BaseRecyclerViewAdapter.OnItemClickListener<TripsResponse.Data.Trip>{
            override fun onItemClick(item: TripsResponse.Data.Trip, view: View) {
                viewModel.startProductListActivity(item.id)
            }
        })
        rv_categories.adapter = adapter
        rv_categories.isFocusable = false
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun reset(){
        KeyboardManager.hideKeyboard(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.btn_browse_product -> {

            }
            R.id.btn_my_order -> {

            }
            R.id.btn_my_invoice -> {

            }
            R.id.btn_my_statement -> {

            }
            R.id.profile -> {
                startActivity(this@DashboardActivity, Router.getClass(Router.Destination.PROFILE))
            }
            R.id.logout -> {
                appPreference.setLoggedIn(false)
                startActivity(this@DashboardActivity, Router.getClass(Router.Destination.LOGIN), clearHistory = true)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_cart){
            //TODO go to cart
            Timber.d { "Shopping cart" }
        }
        return super.onOptionsItemSelected(item)
    }
}
