package addam.com.my.chinlaicustomer.feature.dashboard

import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter
import addam.com.my.chinlaicustomer.rest.model.CategoryListResponse
import addam.com.my.chinlaicustomer.rest.model.TripsResponse

/**
 * Created by Firdaus on 16/1/2019.
 */

class DashboardAdapter( var item:ArrayList<CategoryListResponse.Data.Category>, layoutId: Int, itemClickListener: OnItemClickListener<CategoryListResponse.Data.Category>?) : SingleRecyclerViewLayoutAdapter<CategoryListResponse.Data.Category>(layoutId, itemClickListener){

    override fun getItemForPosition(position: Int): CategoryListResponse.Data.Category {
        return item[position]
    }

    override fun getItemCount(): Int {
        return item.size
    }

}