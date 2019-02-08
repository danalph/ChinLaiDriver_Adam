package addam.com.my.chinlaicustomer.feature.delivery

import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter
import addam.com.my.chinlaicustomer.rest.model.ViewDeliveryTripResponse

/**
 * Created by Firdaus on 22/1/2019.
 */
class DeliveryDetailsAdapter(var item: ArrayList<ViewDeliveryTripResponse.Data.Document>, layoutId: Int, itemClickListener: OnItemClickListener<ViewDeliveryTripResponse.Data.Document>?) : SingleRecyclerViewLayoutAdapter<ViewDeliveryTripResponse.Data.Document>(layoutId, itemClickListener){

    override fun getItemForPosition(position: Int): ViewDeliveryTripResponse.Data.Document {
        return item[position]
    }

    override fun getItemCount(): Int {
        return item.size
    }
}