package addam.com.my.chinlaicustomer.feature.myorder

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.BaseRecyclerViewAdapter
import addam.com.my.chinlaicustomer.databinding.ActivityMyOrderBinding
import addam.com.my.chinlaicustomer.rest.model.MyOrderResponse
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackButtonModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_my_order.*
import javax.inject.Inject

class MyOrderActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MyOrderViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var adapter: MyOrderAdapter

    lateinit var list: ArrayList<MyOrderResponse.Data.SO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityMyOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_order)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackButtonModel("My Order List", true, false,R.drawable.ic_shopping_cart)

        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.orderList.observe(this){
            it?:return@observe
            adapter.run {
                list.clear()
                list.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    private fun setupView() {
        adapter = MyOrderAdapter(list, R.layout.my_order_row_adapter, object : BaseRecyclerViewAdapter.OnItemClickListener<MyOrderResponse.Data.SO>{
            override fun onItemClick(item: MyOrderResponse.Data.SO, view: View) {

            }
        })
        rv_orders.adapter = adapter
        rv_orders.layoutManager = LinearLayoutManager(this@MyOrderActivity)
    }
}
