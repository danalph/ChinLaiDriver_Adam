package addam.com.my.chinlaicustomer.feature.salescustomer

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.CustomerRowItemBinding
import addam.com.my.chinlaicustomer.rest.model.Customers
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by owner on 23/03/2019
 */
class CustomerListAdapter(var models: List<Customers>,
                          var onItemClickListener: OnItemClickListener): RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomerViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }
        val binding: CustomerRowItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.customer_row_item, p0, false)
        return CustomerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(p0: CustomerViewHolder, p1: Int) {
        val item = getItemForPosition(p1)

        if(onItemClickListener != null){
            p0.mBinding.customerRow.setOnClickListener {onItemClickListener.onItemClicked(item)}
        }
        p0.mBinding.model = item
    }

    private fun getItemForPosition(p1: Int): Customers {
        return models.get(p1)
    }


    class CustomerViewHolder: RecyclerView.ViewHolder {
        var mBinding: CustomerRowItemBinding
        constructor(mBinding: CustomerRowItemBinding): super(mBinding.root){
            this.mBinding = mBinding
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(item: Customers)
    }
}