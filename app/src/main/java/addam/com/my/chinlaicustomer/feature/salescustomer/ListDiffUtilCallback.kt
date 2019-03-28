package addam.com.my.chinlaicustomer.feature.salescustomer

import addam.com.my.chinlaicustomer.rest.model.Customers
import android.support.v7.util.DiffUtil

/**
 * Created by owner on 23/03/2019
 */
class ListDiffUtilCallback(private val oldList: List<Customers>, private val newList: List<Customers>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, p1: Int): Boolean = oldList[oldItemPosition].id == newList[p1].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean = oldList[p0] == newList[p1]
}