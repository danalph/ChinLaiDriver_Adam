package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.InvoiceRowHeaderBinding
import addam.com.my.chinlaicustomer.rest.model.Invoices
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * Created by owner on 26/03/2019
 */
class InvoiceAdapter(var models: MutableList<InvoiceMonthModel>, var onItemMonthClickListener: OnItemMonthClickListener): RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>(),
                    InvoiceListItemAdapter.OnItemClickListener{
    private var layoutInflater: LayoutInflater? = null

    lateinit var itemAdapter: InvoiceListItemAdapter
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): InvoiceViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: InvoiceRowHeaderBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.invoice_row_header,p0,false)
        return InvoiceViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, p1: Int) {
        val item = getItemForPosition(p1)

        holder.mBindingHeader.header = item.month


        holder.mBindingHeader.rvInvoiceItems.apply {
            layoutManager = LinearLayoutManager(holder.mBindingHeader.rvInvoiceItems.context, LinearLayout.VERTICAL, false)
            adapter = InvoiceListItemAdapter(models[p1].items as MutableList<Invoices>, this@InvoiceAdapter)
            setRecycledViewPool(viewPool)
        }
    }

    private fun getItemForPosition(position: Int): InvoiceMonthModel {
        return models[position]
    }

    class InvoiceViewHolder : RecyclerView.ViewHolder{


        var mBindingHeader: InvoiceRowHeaderBinding
        constructor(mBinding: InvoiceRowHeaderBinding): super(mBinding.root){
            this.mBindingHeader = mBinding
        }
    }
    override fun onItemClicked(p1: Int, item: Invoices) {
        onItemMonthClickListener.onItemMonthClicked(p1, item)
    }

    interface OnItemMonthClickListener {
        fun onItemMonthClicked(position: Int, item: Invoices)
    }
}
