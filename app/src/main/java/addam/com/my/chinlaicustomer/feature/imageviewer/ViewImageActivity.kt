package addam.com.my.chinlaicustomer.feature.imageviewer

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.databinding.ActivityViewImageBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.AndroidInjection
import javax.inject.Inject
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ortiz.touchview.TouchImageView
import android.support.v4.view.PagerAdapter
import android.support.annotation.NonNull
import android.support.v4.view.ViewPager
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_image.*


class ViewImageActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: ViewImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityViewImageBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_image)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackModel("", true, this::onBackPressed)
        val imageList = intent.getStringArrayListExtra(Router.Parameter.IMAGES.name)
        setupView(imageList)
    }

    private fun setupView(imageList: ArrayList<String>) {
        tv_position.text = "1/${imageList.size}"
        view_pager.adapter = TouchImageAdapter(imageList)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                tv_position.text = "${p0+1}/${imageList.size}"
            }

        })
    }

    internal class TouchImageAdapter(private val imageList: ArrayList<String>) : PagerAdapter() {

        override fun getCount(): Int {
            return imageList.size
        }

        @NonNull
        override fun instantiateItem(@NonNull container: ViewGroup, position: Int): View {
            val img = TouchImageView(container.context)
            Picasso.get()
                .load(imageList[position])
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image)
                .into(img)
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            return img
        }

        override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
            return view === `object`
        }


    }
}
