package addam.com.my.chinlaicustomer.feature.productdetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.databinding.ActivityProductDetailBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarBackWithButtonModel
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_product_detail.*
import javax.inject.Inject

class ProductDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: ProductDetailViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var imageSliderAdapter: ImageSliderAdapter

    private val imageList = arrayListOf<String>()

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarBackWithButtonModel("Professional Electric Cordless Drill", true,true, this::onCartPressed, this::onBackPressed)
        binding.model = viewModel.detailResponse.value
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.detailResponse.observe(this){
            it?:return@observe
            /*imageSliderAdapter.run {
                imageList.clear()
                imageList.addAll(it)
                imageSliderAdapter
            }*/
            binding.model = it
            imageSliderAdapter = ImageSliderAdapter(it.images!!)
            banner_slider.setAdapter(imageSliderAdapter)
        }
    }

    private fun setupView() {

    }

    private fun onCartPressed(){
        Timber.d { "cart pressed" }
    }
}
