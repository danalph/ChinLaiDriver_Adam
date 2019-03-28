package addam.com.my.chinlaicustomer.feature.productlist

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.BaseRecyclerViewAdapter
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityProductListBinding
import addam.com.my.chinlaicustomer.rest.model.ProductListResponse
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.github.ajalt.timberkt.Timber
import com.jakewharton.rxbinding2.widget.textChanges
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductListActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: ProductListViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var adapter: ProductListAdapter

    private val productList = arrayListOf<ProductListResponse.Data.Product>()

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityProductListBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        binding.viewModel = viewModel
        /*binding.toolbarModel = ToolbarBackWithButtonModel(getString(R.string.product_list), true,true,
            R.drawable.ic_shopping_cart, this::onCartPressed, this::onBackPressed)*/
        val productId = intent.getStringExtra(Router.Parameter.CATEGORY_ID.name)
        viewModel.getItem(productId)
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.productsResponse.observe(this){
            it?: return@observe
            adapter.run {
                productList.clear()
                productList.addAll(it.data.products)
                adapter.notifyDataSetChanged()
                swipe_refresh_layout.isRefreshing = false
            }
        }

        viewModel.startActivityEvent.observe(this, object : StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@ProductListActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@ProductListActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

        })
    }

    private fun setupView() {
        rv_product.layoutManager = GridLayoutManager(this, 2)
        adapter = ProductListAdapter(productList, R.layout.product_adapter_layout , object : BaseRecyclerViewAdapter.OnItemClickListener<ProductListResponse.Data.Product> {
            override fun onItemClick(item: ProductListResponse.Data.Product, view: View) {
                viewModel.onItemSelected(item.id)
            }
        })
        rv_product.adapter = adapter
        rv_product.isFocusable = false

        et_search
            .textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(et_search.hasFocus()){
                    //TODO filter search
                }
            }.addTo(disposable)
    }

    private fun onCartPressed(){
        Timber.d { "Cart open" }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
