package th.co.businessplus.kotlinui1.pagination_data.lib_pagination

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi


class QPaginationArrayList<T> {

    var pageFetchData: FetchDataListListener<T>? = null

    interface FetchDataListListener<T> {
        fun onFetchDataList(dataList: List<out T>)
    }

    var PAGE_DATA_SIZE = 10
    var PAGE_BTN_SIZE = 5

    var pageSize = PAGE_DATA_SIZE;
    var maxPages: Int = 1
    var page: Int = 0
    var startingIndex: Int = 0
    var endingIndex: Int = 0

    var lengthPageBtn = 0
    var indexPage = 1

    lateinit var dataList: ArrayList<out T>
    internal var itemNumberDiv: MutableList<Int> = ArrayList()

    var linearGroup: LinearLayout
    var btn_previous: Button
    var btn_next: Button
    var context: Context
    var btnPageProperty: BtnPageProperty

    constructor(context: Context, linearGroup: LinearLayout, btn_previous: Button, btn_next: Button, btnPageProperty: BtnPageProperty) {
        this.context = context
        this.linearGroup = linearGroup
        this.btn_previous = btn_previous
        this.btn_next = btn_next
        this.btnPageProperty = btnPageProperty

    }


    fun initPagination(PAGE_DATA_SIZE: Int = 10, PAGE_BTN_SIZE: Int = 5) {
        this.PAGE_DATA_SIZE = PAGE_DATA_SIZE
        this.PAGE_BTN_SIZE = PAGE_BTN_SIZE;

        btn_previous.isEnabled = false
        calculatePageData()
        calculateInitBtnPage()
        setInitPageData(1)

        ShowDataPage()
        CalculationMakeBtn(1, true)
        btn_previous.setOnClickListener {
            CalculationMakeBtn(getPreviousCurrentPageBtn(), false)
        }
        btn_next.setOnClickListener {
            CalculationMakeBtn(getNextCurrentPageBtn(), true)
        }
    }

    fun setPageDataList(dataList: ArrayList<out T>) {
        this.dataList = dataList
    }

    fun calculatePageData() {
        if (pageSize > 0) {
            if (dataList.size % pageSize == 0) {
                maxPages = dataList.size / pageSize
            } else {
                maxPages = dataList.size / pageSize + 1
            }
        }

        if (maxPages % PAGE_BTN_SIZE == 0) {
            lengthPageBtn = maxPages / PAGE_BTN_SIZE
        } else {
            lengthPageBtn = maxPages / PAGE_BTN_SIZE + 1
        }
    }

    private fun calculateInitBtnPage() {
        btn_next.isEnabled = true
        indexPage =1
        itemNumberDiv.clear()
        var TemRow = 0
        var c = 0
        for (i in 0..maxPages) {
            if (TemRow == c) {
                itemNumberDiv.add(TemRow)
                c = TemRow + PAGE_BTN_SIZE
            }
            TemRow++
        }
        itemNumberDiv.add(maxPages)
    }

    fun setInitPageData(p: Int) {
        if (p >= maxPages) {
            this.page = maxPages
        } else if (p <= 1) {
            this.page = 1
        } else {
            this.page = p
        }

        startingIndex = pageSize * (page - 1)
        if (startingIndex < 0) {
            startingIndex = 0
        }
        endingIndex = startingIndex + pageSize
        if (endingIndex > dataList.size) {
            endingIndex = dataList.size
        }
    }

    fun ShowDataPage() {
        pageFetchData?.onFetchDataList(getListForPage())
    }

    fun getListForPage(): List<out T> {
        return dataList.subList(startingIndex, endingIndex)
    }

    fun CalculationMakeBtn(position: Int, isNext: Boolean) {
        linearGroup.removeAllViews()
        val btnsArray = arrayOfNulls<Button>((maxPages))

        if (position == 0) {
            return
        }
        var left = 0
        var right = 0
        if (isNext) {
            left = 20
            right = 0
        } else {
            left = -20
            right = 0
        }
        for (i in itemNumberDiv.get(position - 1)..itemNumberDiv.get(position) - 1) {
            var btns = Button(context)
            //btns.setBackgroundColor(context.getColor(android.R.color.transparent))
            var layoutparam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutparam.setMargins(2, 2, 2, 2)
            if (btnPageProperty.fitSize) {
                layoutparam.weight = 1f
            }
            layoutparam.gravity = Gravity.CENTER
            btns.layoutParams = layoutparam
            btns.text = "${i + 1}"
            btns.setBackgroundResource(btnPageProperty.btnBg)
            btns.setTextColor(btnPageProperty.btnTextColor)


            btnsArray.set(i, btns)
            linearGroup.addView(btns)
            SlideButtonAnimation(btns, left, right, object :
                SlideAnimationCall {
                override fun AnimationEnd() {

                }
            })
            btnsArray[i]!!.setOnClickListener {
                setButtonClickSelected(btnsArray, i)
                setInitPageData(i + 1)
                ShowDataPage()
            }
        }

    }

    fun getPreviousCurrentPageBtn(): Int {
        indexPage--
        if (indexPage <= 1) {
            indexPage = 1
            btn_previous.isEnabled = false
        }
        btn_next.isEnabled = true
        return indexPage
    }

    fun getNextCurrentPageBtn(): Int {
        indexPage++
        if (indexPage >= lengthPageBtn) {
            indexPage = lengthPageBtn
            btn_next.isEnabled = false
        }
        btn_previous.isEnabled = true
        return indexPage
    }

    fun setButtonClickSelected(btnsArray: Array<Button?>, index: Int) {
        for (v in btnsArray) {
            if (v is Button) {
                v.setBackgroundResource(btnPageProperty.btnBg)
            }

        }
        btnsArray[index]!!.setBackgroundResource(btnPageProperty.btnBgSelected)
    }

    interface SlideAnimationCall {
        fun AnimationEnd()
    }

    fun SlideButtonAnimation(
        layout_view: View,
        starLeft: Int,
        starRight: Int?,
        slideCall: SlideAnimationCall
    ) {
        val anim_slid =
            ObjectAnimator.ofFloat(
                layout_view,
                View.TRANSLATION_X,
                starLeft.toFloat(),
                starRight!!.toFloat()

            ) as ObjectAnimator
        anim_slid.setDuration(50)
        anim_slid.start()
        anim_slid.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
            }

            override fun onAnimationEnd(animator: Animator) {
                slideCall.AnimationEnd()
            }

            override fun onAnimationCancel(animator: Animator) {
            }

            override fun onAnimationRepeat(animator: Animator) {
            }
        })
    }

}