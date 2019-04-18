package addam.com.my.chinlaicustomer.widgets

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText

class CustomQuantityDialog{
    interface QuantityCallBack{
        fun onSetQuantity(quantity: String)
    }
    companion object{
        fun showDialog(context: Context, callback: QuantityCallBack) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            builder.setTitle(context.getString(R.string.set_quantity))
            val dialogLayout = inflater.inflate(R.layout.dialog_quantity_layout, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.et_quantity)
            builder.setView(dialogLayout)
            builder.setPositiveButton(context.getString(R.string.ok)) { dialogInterface, i -> callback.onSetQuantity(editText.text.toString()) }
            builder.setNegativeButton(context.getString(R.string.cancel)){dialog, which -> dialog.dismiss()}
            builder.show()
            KeyboardManager.showKeyboard(context)
        }
    }

}