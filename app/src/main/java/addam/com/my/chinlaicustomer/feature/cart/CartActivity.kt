package addam.com.my.chinlaicustomer.feature.cart

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.event.GenericSingleEvent
import addam.com.my.chinlaicustomer.database.Cart
import addam.com.my.chinlaicustomer.databinding.ActivityCartBinding
import addam.com.my.chinlaicustomer.rest.model.BranchesResponse
import addam.com.my.chinlaicustomer.utilities.observe
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.github.ajalt.timberkt.Timber
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cart.*
import javax.inject.Inject

class CartActivity : AppCompatActivity(), CartAdapter.OnItemClickListener{

    @Inject
    lateinit var viewModel: CartViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var adapter: CartAdapter

    private var selectedList = arrayListOf<Cart>()
    private lateinit var branchesResponse: BranchesResponse
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityCartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.viewModel = viewModel
       /* binding.toolbarModel = ToolbarBackWithButtonModel("My Cart", true, true,
            R.drawable.ic_shopping_cart, this::onCartPressed, this:: onBackPressed)*/

        setupView()
        setupObserver()
    }

    private fun setupObserver() {

        viewModel.cartTtems.observe(this){
            it?:return@observe
            adapter.run {
                list.clear()
                list.addAll(it)
                notifyDataSetChanged()
            }
        }

        viewModel.event.observe(this@CartActivity , object : GenericSingleEvent.EventObserver{
            override fun onPerformEvent() {
                selectedList = adapter.getSelectedItem()
                selectBranchDialog(selectedList, "", "")
            }
        })

        viewModel.branches.observe(this@CartActivity){
            it?:return@observe
            branchesResponse = it
        }
    }

    private fun setupView() {
        adapter = CartAdapter(selectedList, this)
        rv_cart.layoutManager = LinearLayoutManager(this@CartActivity)
        rv_cart.adapter = adapter
    }


    private fun selectBranchDialog(list: ArrayList<Cart>, totalPrice: String, salesPersonId: String) {
        dialog = Dialog(this@CartActivity)
        dialog.setContentView(R.layout.dialog_select_branch)
        dialog.setCancelable(false)
        val spinner = dialog.findViewById<Spinner>(R.id.sp_select_branch)
        val address = dialog.findViewById<TextView>(R.id.tv_address)
        val confirmBtn = dialog.findViewById<Button>(R.id.btn_confirm)
        val cancelBtn = dialog.findViewById<Button>(R.id.btn_cancel)
        var branchId = ""
        val adapter = ArrayAdapter(this@CartActivity, R.layout.spinner_item, branchesResponse.data.customerBranches)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setOnItemClickListener { _, _, position, _ ->
            run {
                address.text = getAddress(position)
                branchId = branchesResponse.data.customerBranches[position].id
            }
        }
        spinner.adapter = adapter
        confirmBtn.setOnClickListener { viewModel.onConfirmOrder(list, branchId, totalPrice, salesPersonId) }
        cancelBtn.setOnClickListener { dialog.cancel() }
        dialog.show()
    }

    private fun getAddress(position: Int) : String{
        val address = StringBuilder()
        address.append(branchesResponse.data.customerBranches[position].address1 + " ")
        address.append(branchesResponse.data.customerBranches[position].address2 + " ")
        address.append(branchesResponse.data.customerBranches[position].address3 + " ")
        address.append(branchesResponse.data.customerBranches[position].areaName + " ")
        address.append(branchesResponse.data.customerBranches[position].postcode + " ")
        address.append(branchesResponse.data.customerBranches[position].stateName)
        return address.toString()
    }

    override fun onItemClick(item: Cart) {
        viewModel.getData(adapter.getSelectedItem())
    }

    private fun onCartPressed(){
        Timber.d { "Cart open" }
    }
}
