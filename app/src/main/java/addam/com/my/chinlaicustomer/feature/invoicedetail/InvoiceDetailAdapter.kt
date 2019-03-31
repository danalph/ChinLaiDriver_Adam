package addam.com.my.chinlaicustomer.feature.invoicedetail

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.InvoiceDetailRowItemBinding
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso

/**
 * Created by owner on 31/03/2019
 */
class InvoiceDetailAdapter(var models: MutableList<ItemInvoiceDetail>): RecyclerView.Adapter<InvoiceDetailAdapter.InvoiceDetailViewHolder>() {
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): InvoiceDetailViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: InvoiceDetailRowItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.invoice_detail_row_item,p0, false)
        return InvoiceDetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: InvoiceDetailViewHolder, p1: Int) {
        val item = getItemForPosition(p1)

        Picasso.get()
            .load(models[p1].img)
            .placeholder(R.drawable.progress_animation)
            .fit()
            .into(holder.mBinding.imgInvoice)

    }

    private fun getItemForPosition(position: Int): ItemInvoiceDetail {
        return models[position]
    }


    class InvoiceDetailViewHolder: RecyclerView.ViewHolder {
        var mBinding: InvoiceDetailRowItemBinding
        constructor(mBinding: InvoiceDetailRowItemBinding): super(mBinding.root){
            this.mBinding = mBinding
        }
    }
}