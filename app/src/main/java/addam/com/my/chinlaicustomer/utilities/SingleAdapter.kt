package addam.com.my.chinlaicustomer.utilities

import addam.com.my.chinlaicustomer.core.SingleRecyclerViewLayoutAdapter


/**
 * Created by Addam on 7/1/2019.
 */
class SingleAdapter<T>(val item: List<T>, layoutId: Int, itemClickListener: OnItemClickListener<T>?) : SingleRecyclerViewLayoutAdapter<T>(layoutId, itemClickListener) {
    override fun getItemCount(): Int {
        return item.size
    }

    override fun getItemForPosition(position: Int): T {
        return item[position]
    }
}