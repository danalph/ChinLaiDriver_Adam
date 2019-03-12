package addam.com.my.chinlaicustomer.utilities

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ss.com.bannerslider.ImageLoadingService


class PicassoImageLoadingService() : ImageLoadingService {

    override fun loadImage(url: String, imageView: ImageView) {
        Picasso.get().load(url).fit().into(imageView)
    }

    override fun loadImage(resource: Int, imageView: ImageView) {
        Picasso.get().load(resource).fit().into(imageView)
    }

    override fun loadImage(url: String, placeHolder: Int, errorDrawable: Int, imageView: ImageView) {
        Picasso.get().load(url).fit().placeholder(placeHolder).error(errorDrawable).into(imageView)
    }
}