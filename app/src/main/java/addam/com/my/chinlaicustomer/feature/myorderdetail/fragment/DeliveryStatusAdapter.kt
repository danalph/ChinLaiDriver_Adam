package addam.com.my.chinlaicustomer.feature.myorderdetail.fragment

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.DeliveryStatusRowItemBinding
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDeliveryStatusResponse
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup




/**
 * Created by Addam on 21/1/2019.
 */
class DeliveryStatusAdapter(val dO: OrderDeliveryStatusResponse.Data.DO, var models: List<OrderDeliveryStatusResponse.Data.POD>): RecyclerView.Adapter<DeliveryStatusAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    class ViewHolder: RecyclerView.ViewHolder {
        var mBinding: DeliveryStatusRowItemBinding

        constructor(mBinding: DeliveryStatusRowItemBinding) : super(mBinding.root) {
            this.mBinding = mBinding
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(p0.context)
        }
        val binding: DeliveryStatusRowItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.delivery_status_row_item, p0, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val item = models[p1]
        holder.mBinding.item = item
        holder.mBinding.tvStatus.text = when(item.action){
            "create" -> "#${dO.docNum} Initiated"
            "packing" -> "Preparing to deliver"
            "trip" -> "#${dO.docNum} Driver assigned and delivering"
            "deliver" -> "Delivered to Destination"
            else -> ""
        }
    }
}