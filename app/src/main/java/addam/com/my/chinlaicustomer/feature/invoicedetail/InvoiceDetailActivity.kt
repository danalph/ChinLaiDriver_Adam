package addam.com.my.chinlaicustomer.feature.invoicedetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.databinding.ActivityInvoiceDetailBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_invoice_detail.*
import javax.inject.Inject

class InvoiceDetailActivity : BaseActivity(), InvoiceDetailViewModel.InvoiceDetailCallback {
    @Inject
    lateinit var viewModel: InvoiceDetailViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var adapter: InvoiceDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val binding: ActivityInvoiceDetailBinding = DataBindingUtil.setContentView(this@InvoiceDetailActivity, R.layout.activity_invoice_detail)
        binding.viewmodel = viewModel
        viewModel.callback = this
        binding.toolbarModel = ToolbarWithBackModel(getString(R.string.title_activity_invoice_detail),true, this::onBackPressed)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupEvents()
    }

    private fun setupEvents() {
        viewModel.invoiceNo.set(intent.getStringExtra(Router.Parameter.ITEM_NUM.name))
        viewModel.date.set(intent.getStringExtra(Router.Parameter.ITEM_DATE.name))
        viewModel.total.set(intent.getStringExtra(Router.Parameter.ITEM_AMOUNT.name))
        viewModel.isPaid.set(intent.getStringExtra(Router.Parameter.ITEM_STATUS.name))

        viewModel.getInvoiceDetails(intent.getStringExtra(Router.Parameter.ITEM_ID.name))
    }

    private fun setupRecyclerView() {
        rv_invoice_items.layoutManager = LinearLayoutManager(this)
    }

    override fun updateUI() {
        adapter = InvoiceDetailAdapter(viewModel.items)
        rv_invoice_items.adapter = adapter
    }
}
