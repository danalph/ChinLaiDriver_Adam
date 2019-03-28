package addam.com.my.chinlaicustomer.feature.cart

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.database.Cart
import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.squareup.picasso.Picasso


class CartAdapter(var list: MutableList<Cart>,  var itemClickListener: CartAdapter.OnItemClickListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CartViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val view = inflater.inflate(R.layout.cart_adapter_layout, p0, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(p0: CartViewHolder, p1: Int){
        val item = list[p1]
        Picasso.get()
            .load(item.productImagePath)
            .placeholder(R.drawable.progress_animation)
            .fit()
            .into(p0.ivProductImage)
        p0.cbChoose.isChecked = item.isChecked
        p0.cbChoose.checkedChanges().subscribe {
            item.isChecked = it
        }
        p0.tvProductName.text = item.productName
        p0.tvQuantity.text = item.productQuantity.toString()
        p0.tvProductPrice.text = "${item.productQuantity * item.productPrice.toIntOrNull()!!}"
        p0.btnPlus.setOnClickListener {
            item.productQuantity++
            p0.tvQuantity.text = item.productQuantity.toString()
        }
        p0.btnMinus.setOnClickListener {
            if (item.productQuantity > 1){
                item.productQuantity--
                p0.tvQuantity.text = item.productQuantity.toString()
            }
        }

        p0.btnCancel.setOnClickListener {
            delete(p1)
        }
    }

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbChoose = view.findViewById<CheckBox>(R.id.cb_choose)!!
        val ivProductImage = view.findViewById<ImageView>(R.id.iv_product_img)!!
        val tvProductName = view.findViewById<TextView>(R.id.tv_product_name)!!
        val btnMinus = view.findViewById<Button>(R.id.btn_minus)!!
        val btnPlus = view.findViewById<Button>(R.id.btn_plus)!!
        val tvQuantity = view.findViewById<TextView>(R.id.tv_quantity)!!
        val btnCancel = view.findViewById<ImageView>(R.id.btn_product_cancel)!!
        val tvProductPrice = view.findViewById<TextView>(R.id.tv_product_price)!!
    }

    fun updateui(holder: CartViewHolder, item: Cart){

    }

    fun getSelectedItem() : ArrayList<Cart>{
        val selectedList = arrayListOf<Cart>()
        list.forEach { if (it.isChecked){
            selectedList.add(it)
        }
        }
        return selectedList
    }

    private fun delete(position: Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: Cart)
    }

}