package addam.com.my.chinlaicustomer.feature.myorder

import addam.com.my.chinlaicustomer.core.BaseRecyclerViewAdapter
import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter
import addam.com.my.chinlaicustomer.rest.model.MyOrderResponse
import android.widget.Filter
import android.widget.Filterable

class MyOrderAdapter(var list: ArrayList<MyOrderResponse.Data.SO>, layoutId: Int, itemClickListener: BaseRecyclerViewAdapter.OnItemClickListener<MyOrderResponse.Data.SO>) : SingleRecyclerViewLayoutAdapter<MyOrderResponse.Data.SO>(layoutId, itemClickListener), Filterable{

    var originalItem = list

    override fun getItemForPosition(position: Int): MyOrderResponse.Data.SO {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                val filteredList = ArrayList<MyOrderResponse.Data.SO>()
                if (constraint.isNotEmpty()){
                    for (currItem in originalItem){
                        if (currItem.id == constraint){
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

    fun sortBy(): Filter{
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val filteredList = ArrayList<MyOrderResponse.Data.SO>()
                if (constraint!!.isNotEmpty() && constraint != "0"){
                    for (currItem in originalItem){
                        if (currItem.status == constraint){
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