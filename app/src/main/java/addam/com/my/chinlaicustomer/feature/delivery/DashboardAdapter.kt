package addam.com.my.chinlaicustomer.feature.delivery

import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter
import addam.com.my.chinlaicustomer.rest.model.TripsResponse
import android.widget.Filter
import android.widget.Filterable

/**
 * Created by Firdaus on 16/1/2019.
 */

class DashboardAdapter( var item:ArrayList<TripsResponse.Data.Trip>, layoutId: Int, itemClickListener: OnItemClickListener<TripsResponse.Data.Trip>?) : SingleRecyclerViewLayoutAdapter<TripsResponse.Data.Trip>(layoutId, itemClickListener){

    override fun getItemForPosition(position: Int): TripsResponse.Data.Trip {
        return item[position]
    }

    override fun getItemCount(): Int {
        return item.size
    }

}