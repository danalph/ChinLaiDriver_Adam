package addam.com.my.chinlaicustomer.feature.statement

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.StatementRowItemBinding
import addam.com.my.chinlaicustomer.rest.model.StatementResponseModel
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by owner on 20/03/2019
 */
class StatementAdapter(var models: MutableList<StatementResponseModel>,
                       var onItemClickListener: StatementAdapter.OnItemClickListener,
                       var onItemSelectListener: OnItemSelectListener): RecyclerView.Adapter<StatementAdapter.StatementViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): StatementViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: StatementRowItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.statement_row_item, p0, false)
        return StatementViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: StatementViewHolder, p1: Int) {
        val item = getItemForPosition(p1)
        if(onItemClickListener != null){
            holder.mBinding.imgDownload.setOnClickListener({onItemClickListener.onItemDownload(p1, item)})
        }
        if(onItemSelectListener != null){
            holder.mBinding.checkbox.setOnClickListener {
                item.isSelected = holder.mBinding.checkbox.isChecked
                onItemSelectListener.onItemSelect(p1, item)
            }
        }

        holder.mBinding.model = item
    }

    private fun getItemForPosition(position: Int): StatementResponseModel {
        return models.get(position)
    }

    fun removeItemAt(position: Int){
        models.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, models.size)
    }

    class StatementViewHolder: RecyclerView.ViewHolder{
        var mBinding: StatementRowItemBinding
        constructor(mBinding: StatementRowItemBinding): super(mBinding.root){
            this.mBinding = mBinding
        }
    }

    interface OnItemClickListener {
        fun onItemDownload(position: Int, item: StatementResponseModel)
    }

    interface OnItemSelectListener{
        fun onItemSelect(position: Int, item: StatementResponseModel)
    }
}