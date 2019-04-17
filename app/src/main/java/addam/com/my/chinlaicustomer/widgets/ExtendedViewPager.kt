package addam.com.my.chinlaicustomer.widgets

import android.content.Context
import com.ortiz.touchview.TouchImageView
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View


class ExtendedViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun canScroll(v: View, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
        return if (v is TouchImageView) {
            v.canScrollHorizontallyFroyo(-dx)
        } else {
            super.canScroll(v, checkV, dx, x, y)
        }
    }

}