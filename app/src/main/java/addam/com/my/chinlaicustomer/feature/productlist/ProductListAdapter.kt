package addam.com.my.chinlaicustomer.feature.productlist

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseRecyclerViewAdapter
import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter
import addam.com.my.chinlaicustomer.rest.model.ProductListResponse
import java.util.ArrayList
import com.squareup.picasso.Picasso
import android.databinding.BindingAdapter
import android.widget.ImageView


class ProductListAdapter(var list: ArrayList<ProductListResponse.Data.Product>, layoutId: Int, itemClickListener: BaseRecyclerViewAdapter.OnItemClickListener<ProductListResponse.Data.Product>) : SingleRecyclerViewLayoutAdapter<ProductListResponse.Data.Product>(layoutId, itemClickListener){
    override fun getItemForPosition(position: Int): ProductListResponse.Data.Product {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}