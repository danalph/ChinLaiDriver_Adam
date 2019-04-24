package addam.com.my.chinlaicustomer.feature.productlist

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.ProductAdapterLayoutBinding
import addam.com.my.chinlaicustomer.rest.model.ProductListResponse
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.squareup.picasso.Picasso

class ProductListFilteredAdapter(var list: MutableList<ProductListResponse.Data.Product>, var onItemClickListener: OnItemClickListener): RecyclerView.Adapter<ProductListFilteredAdapter.ProductListViewHolder>(), Filterable{

    private var layoutInflater: LayoutInflater? = null
    private var originalItem = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductListViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: ProductAdapterLayoutBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.product_adapter_layout,p0, false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val item = getItemForPosition(position)
        holder.mBinding.model = item
        holder.mBinding.root.setOnClickListener { onItemClickListener.onItemClick(position, item) }
        if (item.productImages.isNotEmpty()){
            Picasso.get()
                .load(item.productImages[0])
                .fit()
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image)
                .into(holder.mBinding.ivProductImg)
        }else{
            Picasso.get()
                .load(R.drawable.img_no_image)
                .fit()
                .into(holder.mBinding.ivProductImg)
        }
    }

    class ProductListViewHolder: RecyclerView.ViewHolder {
        var mBinding: ProductAdapterLayoutBinding
        constructor(mBinding: ProductAdapterLayoutBinding): super(mBinding.root){
            this.mBinding = mBinding
        }
    }

    private fun getItemForPosition(position: Int): ProductListResponse.Data.Product{
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onItemClick(p1: Int, item: ProductListResponse.Data.Product)
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filterString = constraint.toString().toLowerCase()
                val results = FilterResults()
                val filteredList = ArrayList<ProductListResponse.Data.Product>()
                if (filterString.isNotEmpty()){
                    for (currItem in originalItem){
                        if (currItem.description1.toLowerCase().contains(filterString) ){
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
                list = results.values as ArrayList<ProductListResponse.Data.Product>
                notifyDataSetChanged()
            }
        }
    }

}