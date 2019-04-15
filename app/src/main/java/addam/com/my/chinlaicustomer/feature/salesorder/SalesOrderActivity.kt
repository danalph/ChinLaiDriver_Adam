package addam.com.my.chinlaicustomer.feature.salesorder

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.databinding.ActivitySalesOrderBinding
import addam.com.my.chinlaicustomer.rest.model.SalesOrderDetailResponse
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_sales_order.*
import javax.inject.Inject

class SalesOrderActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: SalesOrderViewModel

    lateinit var adapter: SalesOrderDetailAdapter

    private var list = arrayListOf<SalesOrderDetailResponse.Data.Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivitySalesOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_sales_order)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackModel("Sales Order", true, this::onBackPressed)

        viewModel.getSalesOrderDetails(intent.getStringExtra(Router.Parameter.ORDER_ID.name))

        setupView()
        setupObserver()
    }

    private fun setupView() {
        rv_sales_items.layoutManager = LinearLayoutManager(this)
        adapter = SalesOrderDetailAdapter(list)
        rv_sales_items.adapter = adapter
        rv_sales_items.isNestedScrollingEnabled = false
    }

    private fun setupObserver() {
        viewModel.items.observe(this){
            it?:return@observe
            adapter.run {
                models.clear()
                models.addAll(it)
                notifyDataSetChanged()
            }
        }
    }
}
