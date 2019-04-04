package addam.com.my.chinlaicustomer.feature.productdetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityProductDetailBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackButtonModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.denzcoskun.imageslider.models.SlideModel
import com.github.ajalt.timberkt.Timber
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_product_detail.*
import javax.inject.Inject

class ProductDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: ProductDetailViewModel

    @Inject
    lateinit var appPreference: AppPreference

    private lateinit var imageSliderAdapter: ImageSliderAdapter

    private val imageList = arrayListOf<String>()

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackButtonModel("", true,true,
            R.drawable.ic_shopping_cart, this::onCartPressed, this::onBackPressed)
        val itemId = intent.getStringExtra(Router.Parameter.ITEM_ID.name)
        viewModel.getDetail(itemId)
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.detailResponse.observe(this){
            it?:return@observe
            binding.model = it.data.product
            binding.toolbarModel = ToolbarWithBackButtonModel(it.data.product.description1, true,true,
                R.drawable.ic_shopping_cart, this::onCartPressed, this::onBackPressed)
            val listImages = ArrayList<SlideModel>()
            if (it.data.productImages != null && it.data.productImages.isNotEmpty()){
                for (image in it.data.productImages){
                    listImages.add(SlideModel(image))
                }
            }
            else{
                listImages.add(SlideModel(R.drawable.img_no_image))
            }
            slider.setImageList(listImages)
        }

        viewModel.startActivityEvent.observe(this, object : StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@ProductDetailActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }
            override fun onStartActivityForResult(data: StartActivityModel) {

            }
        })
    }

    private fun setupView() {

    }

    private fun onCartPressed(){
        Timber.d { "cart pressed" }
        viewModel.onOpenCart()
    }
}
