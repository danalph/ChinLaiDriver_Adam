package addam.com.my.chinlaicustomer.feature.salesorder

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.InvoiceDetailRowItemBinding
import addam.com.my.chinlaicustomer.databinding.SalesOrderDetailRowItemBinding
import addam.com.my.chinlaicustomer.rest.model.InvoiceItem
import addam.com.my.chinlaicustomer.rest.model.SalesOrderDetailResponse
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso

/**
 * Created by owner on 31/03/2019
 */
class SalesOrderDetailAdapter(var models: MutableList<SalesOrderDetailResponse.Data.Item>): RecyclerView.Adapter<SalesOrderDetailAdapter.SalesOrderDetailViewHolder>() {
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SalesOrderDetailViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: SalesOrderDetailRowItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.sales_order_detail_row_item,p0, false)
        return SalesOrderDetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: SalesOrderDetailViewHolder, p1: Int) {
        val item = getItemForPosition(p1)

        if (item.image!!.isNotEmpty()){
            Picasso.get()
                .load(models[p1].image)
                .placeholder(R.drawable.progress_animation)
                .fit()
                .into(holder.mBinding.imgInvoice)
        }else
            Picasso.get()
                .load(R.drawable.img_no_image)
                .fit()
                .into(holder.mBinding.imgInvoice)

        holder.mBinding.item = item

    }

    private fun getItemForPosition(position: Int): SalesOrderDetailResponse.Data.Item{
        return models[position]
    }


    class SalesOrderDetailViewHolder: RecyclerView.ViewHolder {
        var mBinding: SalesOrderDetailRowItemBinding
        constructor(mBinding: SalesOrderDetailRowItemBinding): super(mBinding.root){
            this.mBinding = mBinding
        }
    }
}