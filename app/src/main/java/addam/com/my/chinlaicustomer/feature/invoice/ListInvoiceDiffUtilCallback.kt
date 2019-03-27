package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.rest.model.Invoices
import android.support.v7.util.DiffUtil

/**
 * Created by owner on 27/03/2019
 */
class ListInvoiceDiffUtilCallback(private val oldList: List<Invoices>, private val newList: List<Invoices>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, p1: Int): Boolean = oldList[oldItemPosition].id == newList[p1].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean = oldList[p0] == newList[p1]
}