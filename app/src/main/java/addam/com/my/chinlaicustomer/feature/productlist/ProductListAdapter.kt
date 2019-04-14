package addam.com.my.chinlaicustomer.feature.productlist

import addam.com.my.chinlaicustomer.core.BaseRecyclerViewAdapter
import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter
import addam.com.my.chinlaicustomer.rest.model.ProductListResponse
import android.widget.Filter
import android.widget.Filterable
import java.util.*


class ProductListAdapter(var list: ArrayList<ProductListResponse.Data.Product>, layoutId: Int, itemClickListener: BaseRecyclerViewAdapter.OnItemClickListener<ProductListResponse.Data.Product>) : SingleRecyclerViewLayoutAdapter<ProductListResponse.Data.Product>(layoutId, itemClickListener), Filterable{

    var originalItem = list

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                val filteredList = ArrayList<ProductListResponse.Data.Product>()
                if (constraint.isNotEmpty()){
                    for (currItem in originalItem){
                        if (currItem.description1.contains(constraint, true)){
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

    override fun getItemForPosition(position: Int): ProductListResponse.Data.Product {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}