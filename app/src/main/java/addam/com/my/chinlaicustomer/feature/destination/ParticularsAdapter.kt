package addam.com.my.chinlaicustomer.feature.destination

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.ParticularRowItemBinding
import addam.com.my.chinlaicustomer.rest.model.Particular
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup




/**
 * Created by Addam on 21/1/2019.
 */
class ParticularsAdapter(var models: List<Particular>): RecyclerView.Adapter<ParticularsAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    class ViewHolder: RecyclerView.ViewHolder {
        var mBinding: ParticularRowItemBinding

        constructor(mBinding: ParticularRowItemBinding) : super(mBinding.root) {
            this.mBinding = mBinding
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(p0.context)
        }
        val binding: ParticularRowItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.particular_row_item, p0, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.mBinding.item = models[p1]
        holder.mBinding.row = (p1 + 1).toString()
    }
}