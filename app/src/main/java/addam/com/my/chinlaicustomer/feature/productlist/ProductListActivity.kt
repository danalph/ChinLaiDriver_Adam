package addam.com.my.chinlaicustomer.feature.productlist

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityProductListBinding
import addam.com.my.chinlaicustomer.rest.model.ProductListResponse
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import addam.com.my.chinlaicustomer.utilities.PaginationScrollListener
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackButtonModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.WindowManager
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
    private val pageSize = 20
    private var offset = 0
    private var limit = pageSize
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    var productId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        AndroidInjection.inject(this)
        val binding: ActivityProductListBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackButtonModel(getString(R.string.product_list), true,true,
            R.drawable.ic_shopping_cart, this::onCartPressed, this::onBackPressed)
        productId = intent.getStringExtra(Router.Parameter.CATEGORY_ID.name)
        viewModel.getItem(productId, offset, limit)
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.productsResponse.observe(this){
            it?: return@observe
            adapter.run {
                val size = it.data.products.size
                if(size != 0){
                    productList.addAll(it.data.products)
                    val sizeNew = productList.size
                    notifyItemRangeChanged(size, sizeNew)

                }else{
                    isLastPage = true
                }
                swipe_refresh_layout.isRefreshing = false
                isLoading = false
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

        adapter = ProductListAdapter(productList, object : ProductListAdapter.OnItemClickListener{
            override fun onItemClick(p1: Int, item: ProductListResponse.Data.Product) {
                viewModel.onItemSelected(item.id)
            }
        })

        val layoutManager =  GridLayoutManager(this@ProductListActivity, 2)
        rv_product.adapter = adapter
        rv_product.layoutManager = layoutManager
        rv_product.isNestedScrollingEnabled = false
        rv_product.addOnScrollListener(object : PaginationScrollListener(layoutManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                offset = limit + 1
                limit += pageSize
                viewModel.getItem(productId, offset, limit)
            }

        })

        et_search
            .textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(et_search.hasFocus()){
                    adapter.filter.filter(it)
                    isLastPage = it.isNotEmpty()
                }
            }.addTo(disposable)

        swipe_refresh_layout.setOnRefreshListener {
            adapter.list.clear()
            isLoading = true
            isLastPage = false
            offset = 0
            limit = pageSize
            viewModel.getItem(productId, offset, limit)
        }
    }

    private fun onCartPressed(){
        Timber.d { "Cart open" }
        viewModel.onOpenCart()
    }

    override fun onResume() {
        super.onResume()
        KeyboardManager.hideKeyboard(this)
        focus_thief.requestFocus()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
