package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.HistoryRowItemBinding
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.Item
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by owner on 10/05/2019
 */
class ItemHistoryAdapter(var models: MutableList<Item>): RecyclerView.Adapter<ItemHistoryAdapter.ItemViewHolder>() {
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: HistoryRowItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.history_row_item, p0, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {
        val item = getItemForPosition(p1)

        val itemPost = p1 + 1
        holder.mBinding.number = itemPost.toString()
        holder.mBinding.item = item
    }

    private fun getItemForPosition(position: Int): Item {
        return models[position]
    }

    class ItemViewHolder: RecyclerView.ViewHolder {
        var mBinding: HistoryRowItemBinding
        constructor(mBinding: HistoryRowItemBinding): super(mBinding.root){
            this.mBinding = mBinding
        }
    }
}