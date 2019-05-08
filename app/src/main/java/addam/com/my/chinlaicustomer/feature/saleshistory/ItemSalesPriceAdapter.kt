package addam.com.my.chinlaicustomer.feature.saleshistory

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.ProductHistoryRowItemBinding
import addam.com.my.chinlaicustomer.rest.model.salesitemhistory.Product
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso

/**
 * Created by owner on 09/05/2019
 */
class ItemSalesPriceAdapter(var models: MutableList<Product> ):
    RecyclerView.Adapter<ItemSalesPriceAdapter.ProductViewHolder>() {
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: ProductHistoryRowItemBinding= DataBindingUtil.inflate(layoutInflater!!, R.layout.product_history_row_item, p0, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, p1: Int) {
        val item = getItemForPosition(p1)

        if (item.product_images.isNotEmpty()){
            Picasso.get()
                .load(models[p1].product_images[0])
                .placeholder(R.drawable.progress_animation)
                .fit()
                .into(holder.mBinding.imgProduct)
        }else
            Picasso.get()
                .load(R.drawable.img_no_image)
                .fit()
                .into(holder.mBinding.imgProduct)

        holder.mBinding.item = item
    }

    private fun getItemForPosition(position: Int): Product {
        return models[position]
    }


    class ProductViewHolder: RecyclerView.ViewHolder {
        var mBinding: ProductHistoryRowItemBinding
        constructor(mBinding: ProductHistoryRowItemBinding): super(mBinding.root){
            this.mBinding = mBinding
        }
    }
}