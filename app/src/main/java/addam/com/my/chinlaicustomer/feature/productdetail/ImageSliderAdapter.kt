package addam.com.my.chinlaicustomer.feature.productdetail

import addam.com.my.chinlaicustomer.R
import ss.com.bannerslider.viewholder.ImageSlideViewHolder
import ss.com.bannerslider.adapters.SliderAdapter


class ImageSliderAdapter(private val imageList: ArrayList<String>) : SliderAdapter() {

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindImageSlide(position: Int, viewHolder: ImageSlideViewHolder) {
        viewHolder.bindImageSlide(imageList[position], R.drawable.img_no_image, R.drawable.img_no_image)
    }

}