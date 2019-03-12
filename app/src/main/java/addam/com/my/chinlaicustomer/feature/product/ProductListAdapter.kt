package addam.com.my.chinlaicustomer.feature.product

import addam.com.my.chinlaicustomer.core.BaseRecyclerViewAdapter
import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter
import addam.com.my.chinlaicustomer.rest.model.ProductList
import java.util.ArrayList

class ProductListAdapter(var list: ArrayList<ProductList>, layoutId: Int, itemClickListener: BaseRecyclerViewAdapter.OnItemClickListener<ProductList>) : SingleRecyclerViewLayoutAdapter<ProductList>(layoutId, itemClickListener){
    override fun getItemForPosition(position: Int): ProductList {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

}