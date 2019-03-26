package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.InvoiceRowItemBinding
import addam.com.my.chinlaicustomer.rest.model.Invoices
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by owner on 26/03/2019
 */
class InvoiceListItemAdapter(val models: MutableList<Invoices>, private val onItemClickListener: InvoiceListItemAdapter.OnItemClickListener): RecyclerView.Adapter<InvoiceListItemAdapter.InvoiceListItemViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): InvoiceListItemViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: InvoiceRowItemBinding= DataBindingUtil.inflate(layoutInflater!!, R.layout.invoice_row_item,p0,false)
        return InvoiceListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: InvoiceListItemViewHolder, p1: Int) {
        val item = getItemForPosition(p1)
        if(onItemClickListener != null){
            holder.mBinding.itemLayout.setOnClickListener {onItemClickListener.onItemClicked(p1, item)}
        }

        holder.mBinding.item = item
    }

    private fun getItemForPosition(position: Int): Invoices {
        return models[position]
    }


    class InvoiceListItemViewHolder: RecyclerView.ViewHolder {
        val mBinding: InvoiceRowItemBinding

        constructor(binding: InvoiceRowItemBinding): super(binding.root){
            this.mBinding = binding
        }

    }

    interface OnItemClickListener {
        fun onItemClicked(p1: Int, item: Invoices)

    }
}