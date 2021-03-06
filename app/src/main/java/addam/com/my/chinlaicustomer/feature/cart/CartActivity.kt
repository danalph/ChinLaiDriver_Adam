package addam.com.my.chinlaicustomer.feature.cart

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.GenericSingleEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.database.Cart
import addam.com.my.chinlaicustomer.databinding.ActivityCartBinding
import addam.com.my.chinlaicustomer.rest.model.BranchesResponse
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackButtonModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.ajalt.timberkt.Timber
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.dialog_select_branch.view.*
import javax.inject.Inject

class CartActivity : BaseActivity(), CartAdapter.OnItemClickListener{

    @Inject
    lateinit var viewModel: CartViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var adapter: CartAdapter
    private var itemList = arrayListOf<Cart>()
    private lateinit var branchesResponse: BranchesResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        AndroidInjection.inject(this)
        val binding: ActivityCartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackButtonModel(getString(R.string.my_cart), true, true,
            R.drawable.ic_shopping_cart, this::onCartPressed, this:: onBackPressed)

        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.cartItems.observe(this){
            it?:return@observe
            itemList = it as ArrayList<Cart>
            adapter.run {
                list.clear()
                list.addAll(it)
                notifyDataSetChanged()
                viewModel.setPrice(adapter.getSelectedItem())
            }
        }

        viewModel.event.observe(this@CartActivity , object : GenericSingleEvent.EventObserver{
            override fun onPerformEvent() {
                selectBranchDialog(adapter.getSelectedItem(), appPreference.getSalesId())
            }
        })

        viewModel.eventDelete.observe(this@CartActivity, object : GenericSingleEvent.EventObserver{
            override fun onPerformEvent() {
                viewModel.setPrice(adapter.getSelectedItem())
            }

        })

        viewModel.branches.observe(this@CartActivity){
            it?:return@observe
            branchesResponse = it
        }

        viewModel.startActivityEvent.observe(this@CartActivity, object : StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@CartActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {

            }
        })

        viewModel.eventError.observe(this@CartActivity, object : GenericSingleEvent.EventObserver{
            override fun onPerformEvent() {
                Toast.makeText(this@CartActivity,
                    getString(R.string.error),
                    Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.eventSuccess.observe(this@CartActivity, object : GenericSingleEvent.EventObserver{
            override fun onPerformEvent() {
                Toast.makeText(this@CartActivity, getString(R.string.order_success), Toast.LENGTH_SHORT).show()
            }

        })

        viewModel.eventItemUpdated.observe(this@CartActivity, object: GenericSingleEvent.EventObserver{
            override fun onPerformEvent() {
                finish()
            }
        })
    }

    private fun setupView() {
        adapter = CartAdapter(this@CartActivity, itemList, this)
        rv_cart.layoutManager = LinearLayoutManager(this@CartActivity)
        rv_cart.adapter = adapter
    }


    private fun selectBranchDialog(list: ArrayList<Cart>, salesPersonId: String) {
        val view = LayoutInflater.from(this@CartActivity).inflate(R.layout.dialog_select_branch,null)
        val builder = AlertDialog.Builder(this@CartActivity)
            .setView(view)
            .setCancelable(false)
        val dialog = builder.show()
        val spinner = view.sp_select_branch
        val address = view.tv_address
        val confirmBtn = view.btn_confirm
        val cancelBtn = view.btn_cancel
        var branchId = ""

        val spinnerList: ArrayList<String> = ArrayList()
        for (branches in branchesResponse.data.customerBranches){
            spinnerList.add(branches.name)
        }
        val adapter = ArrayAdapter(this@CartActivity, R.layout.spinner_item, spinnerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                address.text = getAddress(position)
                branchId = branchesResponse.data.customerBranches[position].id
            }

        }
        spinner.adapter = adapter
        confirmBtn.setOnClickListener { viewModel.onConfirmOrder(list, branchId, salesPersonId) }
        cancelBtn.setOnClickListener { dialog.cancel() }
    }

    private fun getAddress(position: Int) : String{
        val address = StringBuilder()
        address.append(branchesResponse.data.customerBranches[position].address1 + " ")
        address.append(branchesResponse.data.customerBranches[position].address2 + " ")
        address.append(branchesResponse.data.customerBranches[position].address3)
        return address.toString()
    }

    override fun onItemClick() {
        viewModel.setPrice(adapter.getSelectedItem())
    }

    override fun onDeleteItem(item: Cart) {
        viewModel.onDeleteItem(item)
    }

    private fun onCartPressed(){
        Timber.d { "Cart open" }
    }

    override fun onBackPressed() {
        viewModel.onUpdateItem(itemList)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
