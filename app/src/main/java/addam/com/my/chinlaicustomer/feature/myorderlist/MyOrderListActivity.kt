package addam.com.my.chinlaicustomer.feature.myorderlist

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityMyOrderBinding
import addam.com.my.chinlaicustomer.databinding.NavHeaderDashboardBinding
import addam.com.my.chinlaicustomer.rest.model.MyOrderResponse
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import addam.com.my.chinlaicustomer.utilities.PaginationScrollListener
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
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
import kotlinx.android.synthetic.main.activity_my_order.*
import kotlinx.android.synthetic.main.content_my_order.*
import kotlinx.android.synthetic.main.layout_my_order.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MyOrderListActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var listViewModel: MyOrderListViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var adapter: MyOrderListAdapter

    private val list = arrayListOf<MyOrderResponse.Data.SO>()

    private val disposable = CompositeDisposable()

    private val categories = arrayOf("All", "Pending", "Confirmed", "Processing", "Delivering", "Completed")
    private var offset = 0
    private var limit = 20
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityMyOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_order)
        binding.viewModel = listViewModel
        //binding.toolbarModel = ToolbarWithBackButtonModel("My Order List", true, false,R.drawable.ic_shopping_cart, callback = this::onBackPressed)
        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = listViewModel.name.get().toString()
        headerBind.isSalesPerson = appPreference.getSalesId() != "0"

        setSupportActionBar(toolbar)
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        listViewModel.orderList.observe(this){
            it?:return@observe
            adapter.run {
                val size = it.size
                if(size != 0){
                    list.addAll(it)
                    val sizeNew = list.size
                    notifyItemRangeChanged(size, sizeNew)

                }else{
                    isLastPage = true
                }
                swipe_refresh_layout_order.isRefreshing = false
                isLoading = false
            }
        }

        listViewModel.startActivityEvent.observe(this, object : StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@MyOrderListActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@MyOrderListActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

        })
    }

    private fun setupView() {
        KeyboardManager.hideKeyboard(this)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.my_order)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        setupNavigationLayout(nav_view, appPreference)

        adapter = MyOrderListAdapter(list, object: MyOrderListAdapter.OnItemClickListener{
            override fun onTrackClick(item: MyOrderResponse.Data.SO) {
                listViewModel.viewDetail(item.id, 2)
            }
            override fun onItemClick(p1: Int, item: MyOrderResponse.Data.SO) {
                listViewModel.viewSalesOrderDetail(item.id)
            }
        })

        val layoutManager = LinearLayoutManager(this@MyOrderListActivity)
        rv_orders.adapter = adapter
        rv_orders.layoutManager = layoutManager
        rv_orders.setOnScrollListener(object : PaginationScrollListener(layoutManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                offset += limit
                listViewModel.getOrder(offset, limit, "all", false)
            }

        })

        sp_status.adapter = ArrayAdapter(this@MyOrderListActivity, android.R.layout.simple_list_item_1, categories)
        sp_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, itemID: Long) {
                if(position >= 0 && position < categories.size){
                    adapter.sortBy().filter(categories[position].toLowerCase())
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        et_search
            .textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(et_search.hasFocus()){
                    adapter.filter.filter(it)
                    listViewModel.totalOrder.set(adapter.itemCount)
                    isLastPage = it.isNotEmpty()
                }
            }.addTo(disposable)



        swipe_refresh_layout_order.setOnRefreshListener {
            adapter.list.clear()
            isLoading = true
            isLastPage = false
            offset = 0
            listViewModel.getOrder(offset, limit, "all", true)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        setNavigation(item, appPreference, this@MyOrderListActivity::class.java.simpleName)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
