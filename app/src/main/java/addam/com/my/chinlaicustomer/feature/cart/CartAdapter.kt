package addam.com.my.chinlaicustomer.feature.cart

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.database.Cart
import addam.com.my.chinlaicustomer.widgets.CustomQuantityDialog
import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.squareup.picasso.Picasso
import java.text.DecimalFormat


class CartAdapter(val context: Context, var list: ArrayList<Cart>,  var itemClickListener: CartAdapter.OnItemClickListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CartViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val view = inflater.inflate(R.layout.cart_adapter_layout, p0, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(p0: CartViewHolder, p1: Int){
        val item = list[p1]
        var quantity = item.productQuantity
        if (item.productImagePath.isNotEmpty()){
            Picasso.get()
                .load(item.productImagePath)
                .placeholder(R.drawable.img_no_image)
                .fit()
                .into(p0.ivProductImage)
        }else{
            Picasso.get()
                .load(R.drawable.img_no_image)
                .placeholder(R.drawable.img_no_image)
                .fit()
                .into(p0.ivProductImage)
        }
        p0.cbChoose.isChecked = item.isChecked
        p0.cbChoose.checkedChanges().subscribe {
            item.isChecked = it
            itemClickListener.onItemClick()
        }
        p0.tvProductName.text = item.productName
        p0.tvQuantity.text = quantity.toString()
        p0.tvProductPrice.text = setPrice(item.productPrice)

        p0.btnPlus.setOnClickListener {
            if (p0.tvQuantity.text.toString().toInt() >= 1){
                var qty = p0.tvQuantity.text.toString().toInt()
                qty += 1
                p0.tvQuantity.text = qty.toString()
                item.productQuantity = qty
                itemClickListener.onItemClick()
            }
        }
        p0.btnMinus.setOnClickListener {
            if (p0.tvQuantity.text.toString().toInt() > 1){
                var qty = p0.tvQuantity.text.toString().toInt()
                qty -= 1
                p0.tvQuantity.text = qty.toString()
                item.productQuantity = qty
                itemClickListener.onItemClick()
            }
        }

        p0.btnCancel.setOnClickListener {
            delete(p1,item)
        }

        p0.tvQuantity.setOnClickListener { CustomQuantityDialog.showDialog(context, object : CustomQuantityDialog.QuantityCallBack{
            override fun onSetQuantity(quantity: String) {
                p0.tvQuantity.text = quantity
                item.productQuantity = quantity.toInt()
                itemClickListener.onItemClick()
            }
        })
        }
    }

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbChoose = view.findViewById<CheckBox>(R.id.cb_choose)!!
        val ivProductImage = view.findViewById<ImageView>(R.id.iv_product_img)!!
        val tvProductName = view.findViewById<TextView>(R.id.tv_product_name)!!
        val btnMinus = view.findViewById<TextView>(R.id.btn_minus)!!
        val btnPlus = view.findViewById<TextView>(R.id.btn_plus)!!
        val tvQuantity = view.findViewById<TextView>(R.id.tv_quantity)!!
        val btnCancel = view.findViewById<ImageView>(R.id.btn_product_cancel)!!
        val tvProductPrice = view.findViewById<TextView>(R.id.tv_product_price)!!
    }

    fun getSelectedItem() : ArrayList<Cart>{
        val selectedList = arrayListOf<Cart>()
        list.forEach { if (it.isChecked){
            selectedList.add(it)
        }
        }
        return selectedList
    }

    private fun delete(position: Int, item:Cart){
        list.removeAt(position)
        notifyDataSetChanged()
        itemClickListener.onDeleteItem(item)
    }

    interface OnItemClickListener {
        fun onItemClick()
        fun onDeleteItem(item:Cart)
    }

    private fun setPrice(price: String) : String{
        val format = DecimalFormat("#,###,###,##0.00")
        return "RM ${format.format(price.toDouble())}"
    }

}