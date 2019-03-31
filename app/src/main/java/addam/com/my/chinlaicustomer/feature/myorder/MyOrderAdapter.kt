package addam.com.my.chinlaicustomer.feature.myorder

import addam.com.my.chinlaicustomer.core.BaseRecyclerViewAdapter
import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter
import addam.com.my.chinlaicustomer.rest.model.MyOrderResponse

class MyOrderAdapter(val list: ArrayList<MyOrderResponse.Data.SO>, layoutId: Int, itemClickListener: BaseRecyclerViewAdapter.OnItemClickListener<MyOrderResponse.Data.SO>) : SingleRecyclerViewLayoutAdapter<MyOrderResponse.Data.SO>(layoutId, itemClickListener){
    override fun getItemForPosition(position: Int): MyOrderResponse.Data.SO {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

}