package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.databinding.ActivityItemHistoryBinding
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.Item
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_item_history.*
import javax.inject.Inject

class ItemHistoryActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: ItemHistoryViewModel

    lateinit var adapter: ItemHistoryAdapter
    var list = listOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val binding: ActivityItemHistoryBinding = DataBindingUtil.setContentView(this@ItemHistoryActivity, R.layout.activity_item_history)
        binding.toolbarModel = ToolbarWithBackModel(getString(R.string.item_sales_price_history), true, this::onBackPressed)
        binding.viewModel = viewModel

        getIntents()
        setupRecyclerView()
        setupEvent()

        viewModel.getItemHistory()
    }

    private fun setupEvent() {
        viewModel.items.observe(this){
            it?:return@observe
            adapter.run {
                this.models = it.toMutableList()
                notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView() {
        rv_history.layoutManager = LinearLayoutManager(this)

        adapter = ItemHistoryAdapter(list.toMutableList())
        rv_history.adapter = adapter
    }

    private fun getIntents() {
        viewModel.id.set(intent.getStringExtra(Router.Parameter.ITEM_ID.name))
        viewModel.start.set(intent.getStringExtra(Router.Parameter.START_DATE.name))
        viewModel.end.set(intent.getStringExtra(Router.Parameter.END_DATE.name))
    }
}
