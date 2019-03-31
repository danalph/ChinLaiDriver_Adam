package addam.com.my.chinlaicustomer.feature.invoicedetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.databinding.ActivityInvoiceDetailBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_invoice_detail.*
import javax.inject.Inject

class InvoiceDetailActivity : BaseActivity() {

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
        binding.toolbarModel = ToolbarWithBackModel(getString(R.string.title_activity_invoice_detail),true, this::onBackPressed)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupEvents()
    }

    private fun setupEvents() {

    }

    private fun setupRecyclerView() {
        rv_invoice_items.layoutManager = LinearLayoutManager(this)

        adapter = InvoiceDetailAdapter(viewModel.getDummy())
        rv_invoice_items.adapter = adapter
    }
}
