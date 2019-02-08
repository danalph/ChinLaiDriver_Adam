package addam.com.my.chinlaicustomer.widgets

import android.content.Context
import android.view.MotionEvent
import android.support.v4.view.ViewPager
import android.util.AttributeSet


/**
 * Created by Firdaus on 15/1/2019.
 */
class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {


    private var allow = false

    init {
        this.allow = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.allow) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.allow) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.allow = enabled
    }
}