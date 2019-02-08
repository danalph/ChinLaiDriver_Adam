package addam.com.my.chinlaicustomer.feature.delivery

import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter
import addam.com.my.chinlaicustomer.rest.model.TripsResponse
import android.widget.Filter
import android.widget.Filterable

/**
 * Created by Firdaus on 16/1/2019.
 */

class DashboardAdapter( var item:ArrayList<TripsResponse.Data.Trip>, layoutId: Int, itemClickListener: OnItemClickListener<TripsResponse.Data.Trip>?) : SingleRecyclerViewLayoutAdapter<TripsResponse.Data.Trip>(layoutId, itemClickListener), Filterable{
    var originalItem = item

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                val filteredList = ArrayList<TripsResponse.Data.Trip>()
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
                item = results.values as ArrayList<TripsResponse.Data.Trip>
                notifyDataSetChanged()
            }

        }
    }

    override fun getItemForPosition(position: Int): TripsResponse.Data.Trip {
        return item[position]
    }

    override fun getItemCount(): Int {
        return item.size
    }

}