package addam.com.my.chinlaicustomer.feature.myorderdetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.databinding.ActivityMyOrderDetailBinding
import addam.com.my.chinlaicustomer.feature.myorderdetail.fragment.OrderDeliveryStatusFragment
import addam.com.my.chinlaicustomer.feature.myorderdetail.fragment.OrderDriverFragment
import addam.com.my.chinlaicustomer.feature.myorderdetail.fragment.OrderInformationFragment
import addam.com.my.chinlaicustomer.utilities.ViewPagerAdapter
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_my_order_detail.*
import javax.inject.Inject

class MyOrderDetailActivity : BaseActivity() {


    @Inject
    lateinit var viewModel: MyOrderDetailViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var adapter: ViewPagerAdapter

    private var selectedTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityMyOrderDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_order_detail)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackModel("View Delivery Order", true, this::onBackPressed)

        setupView()
        setupObserver()
        selectedTab = intent.getIntExtra(Router.Parameter.SELECTED_TAB.name, 0)
        viewModel.orderId = intent.getStringExtra(Router.Parameter.ORDER_ID.name)
        viewModel.getOrderDetail()
    }

    private fun setupView() {
        setupTabs()
        setupViewPager()
    }

    private fun setupObserver() {
        viewModel.orderDetail.observe(this){
            it?:return@observe
            adapter.run {
                this.addFragment(OrderInformationFragment.newInstance(it.data.dO), "Order Information")
                notifyDataSetChanged()
                viewModel.getDriverDetail()
            }
        }
        viewModel.orderDriverDetail.observe(this){
            it?:return@observe
            adapter.run {
                this.addFragment(OrderDriverFragment.newInstance(it.data?.dO!!), "Driver Information")
                notifyDataSetChanged()
                viewModel.getDeliveryStatus()
            }
        }
        viewModel.orderDeliveryStatus.observe(this){
            it?:return@observe
            adapter.run {
                this.addFragment(OrderDeliveryStatusFragment.newInstance(it.data!!.pOD), "Delivery Status")
                notifyDataSetChanged()
            }
        }
    }

    private fun setupTabs() {
        order_tabs.addTab(order_tabs.newTab().setText("Order Information"))
        order_tabs.addTab(order_tabs.newTab().setText("Driver Information"))
        order_tabs.addTab(order_tabs.newTab().setText("Delivery Status"))

        order_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewpager.currentItem = p0?.position!!
            }

        })

        order_tabs.getTabAt(selectedTab)?.select()

    }

    private fun setupViewPager(){
        viewpager.setPagingEnabled(false)
        adapter = ViewPagerAdapter(supportFragmentManager)
        viewpager?.adapter = adapter
        viewpager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(order_tabs))
    }
}
