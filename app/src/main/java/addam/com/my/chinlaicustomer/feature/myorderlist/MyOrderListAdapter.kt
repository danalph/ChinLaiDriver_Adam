package addam.com.my.chinlaicustomer.feature.myorderlist

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.MyOrderRowAdapterBinding
import addam.com.my.chinlaicustomer.rest.model.MyOrderResponse
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

/**
 * Created by owner on 31/03/2019
 */
class MyOrderListAdapter(var list: ArrayList<MyOrderResponse.Data.SO>, var onItemClickListener: OnItemClickListener): RecyclerView.Adapter<MyOrderListAdapter.InvoiceDetailViewHolder>(), Filterable {
    private var layoutInflater: LayoutInflater? = null
    private var originalItem = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): InvoiceDetailViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: MyOrderRowAdapterBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.my_order_row_adapter,p0, false)
        return InvoiceDetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: InvoiceDetailViewHolder, p1: Int) {
        val item = getItemForPosition(p1)
        holder.mBinding.model = item
        holder.mBinding.tvTrackOrder.setOnClickListener {
            onItemClickListener.onTrackClick(item)
        }

        holder.mBinding.cardView.setOnClickListener { onItemClickListener.onItemClick(p1, item) }
    }

    private fun getItemForPosition(position: Int): MyOrderResponse.Data.SO{
        return list[position]
    }

    class InvoiceDetailViewHolder: RecyclerView.ViewHolder {
        var mBinding: MyOrderRowAdapterBinding
        constructor(mBinding: MyOrderRowAdapterBinding): super(mBinding.root){
            this.mBinding = mBinding
        }
    }

    interface OnItemClickListener{
        fun onTrackClick(item: MyOrderResponse.Data.SO)
        fun onItemClick(p1: Int, item: MyOrderResponse.Data.SO)
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filterString = constraint.toString().toLowerCase()
                val results = FilterResults()
                val filteredList = ArrayList<MyOrderResponse.Data.SO>()
                if (filterString.isNotEmpty()){
                    for (currItem in originalItem){
                        if (currItem.docNum.toLowerCase().contains(filterString)){
                            filteredList.add(currItem)
                        }
                    }
                    results.count = filteredList.size
                    results.values = filteredList
                }else{
                    results.count = originalItem.size
                    results.values = originalItem
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                list = results.values as ArrayList<MyOrderResponse.Data.SO>
                notifyDataSetChanged()
            }
        }
    }

    fun sortBy(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val filteredList = ArrayList<MyOrderResponse.Data.SO>()
                if (constraint!!.isNotEmpty() && constraint != "all"){
                    for (currItem in originalItem){
                        if (currItem.status.toLowerCase() == constraint.toString().toLowerCase()){
                            filteredList.add(currItem)
                        }
                    }
                    results.count = filteredList.size
                    results.values = filteredList
                }else{
                    results.count = originalItem.size
                    results.values = originalItem
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list = results?.values as ArrayList<MyOrderResponse.Data.SO>
                notifyDataSetChanged()
            }
        }
    }

}