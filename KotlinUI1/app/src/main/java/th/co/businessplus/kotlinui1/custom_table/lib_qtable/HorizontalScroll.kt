package th.co.businessplus.kotlinui1.custom_table.lib_qtable

import android.content.Context
import android.widget.HorizontalScrollView

class HorizontalScroll(context: Context?) : HorizontalScrollView(context) {

    private var scrollViewListener: ScrollViewListener? = null

    interface ScrollViewListener {

        fun onScrollChanged(scrollView: HorizontalScroll, x: Int, y: Int, oldx: Int, oldy: Int)

    }

    fun setScrollViewListener(scrollViewListener: ScrollViewListener) {
        this.scrollViewListener = scrollViewListener
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        if (scrollViewListener != null) {
            scrollViewListener!!.onScrollChanged(this, x, y, oldx, oldy)
        }
    }

}