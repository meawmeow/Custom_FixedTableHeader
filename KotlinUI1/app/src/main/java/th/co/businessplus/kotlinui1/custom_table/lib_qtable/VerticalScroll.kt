package th.co.businessplus.kotlinui1.custom_table.lib_qtable

import android.content.Context
import android.widget.ScrollView

class VerticalScroll(context: Context?) : ScrollView(context) {

    private var scrollViewListener: ScrollViewListener? = null

    interface ScrollViewListener {
        fun onScrollChanged(scrollView: VerticalScroll, x: Int, y: Int, oldx: Int, oldy: Int)
    }

    fun setScrollViewListener(scrollViewListener: ScrollViewListener) {
        this.scrollViewListener = scrollViewListener
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        super.onScrollChanged(x, y, oldx, oldy)
        if (scrollViewListener != null) {
            scrollViewListener!!.onScrollChanged(this, x, y, oldx, oldy)
        }
    }
}