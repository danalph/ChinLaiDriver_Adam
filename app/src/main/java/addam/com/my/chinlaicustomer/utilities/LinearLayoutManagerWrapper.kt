package addam.com.my.chinlaicustomer.utilities

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet

/**
 * Created by owner on 28/03/2019
 */
class LinearLayoutManagerWrapper: LinearLayoutManager {

    constructor(context: Context): super(context)

    constructor(context: Context, orientation: Int, reverseLayout: Boolean): super(context, orientation, reverseLayout)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}