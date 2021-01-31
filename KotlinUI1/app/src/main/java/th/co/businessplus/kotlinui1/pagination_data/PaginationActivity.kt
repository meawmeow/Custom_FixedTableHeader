package th.co.businessplus.kotlinui1.pagination_data

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pagination.*
import th.co.businessplus.kotlinui1.R



class PaginationActivity : AppCompatActivity() {

    lateinit var mutableArray: MutableMap<Int, ArrayList<ModelItem>>
    @JvmField
    var strLogBuf = StringBuffer()
    var pageSize_ = 4
    var maxPages_: Int = 1


    var page_: Int = 0
    var startingIndex_: Int = 0
    var endingIndex_: Int = 0

    lateinit var linearGroup: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination)
        mutableArray = addPageItem()

        linearGroup = findViewById(R.id.la_view) as LinearLayout

        calculatePageData()
        setInitPageData(1)
        ShowDataPage()


        btn_previous.setOnClickListener {
            setInitPageData(getPreviousPage())
            ShowDataPage()
        }
        btn_next.setOnClickListener {
            setInitPageData(getNextPage())
            ShowDataPage()
        }
    }


    fun ShowDataPage() {
        strLogBuf.setLength(0)
        linearGroup.removeAllViews()

        var d = calculateDistance(startingIndex_, endingIndex_)
        val btnsArray = arrayOfNulls<Button>(d)

        for (i in startingIndex_ + 1..endingIndex_) {
            var btns = Button(this)
            btns.setBackgroundColor(resources.getColor(android.R.color.transparent))
            var layoutparam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutparam.setMargins(2, 2, 2, 2)
            layoutparam.weight = 1f
            btns.layoutParams = layoutparam
            btns.text = "Page$i"
            btns.setBackgroundResource(R.drawable.bg_btn1_select)
            btns.setTextColor(Color.WHITE)

            btnsArray.set(i - 1, btns)
            linearGroup.addView(btns)
            btnsArray[i - 1]!!.setOnClickListener {
                strLogBuf.setLength(0)
                for (i in mutableArray.get(i)!!) {
                    strLogBuf.append("${i.name} | ${i.room}\n")
                }
                // strLogBuf.append("${mutableArray.get(i)}")
                txtLog.text = strLogBuf.toString()
            }
        }

    }

    fun calculateDistance(x1: Int, x2: Int): Int {
        val x = Math.abs(x1 + x2)
        return x
    }


    fun calculatePageData() {
        if (pageSize_ > 0) {
            if (mutableArray.size % pageSize_ == 0) {
                maxPages_ = mutableArray.size / pageSize_
            } else {
                maxPages_ = mutableArray.size / pageSize_ + 1
            }
        }
    }

    fun setInitPageData(p: Int) {
        if (p >= maxPages_) {
            this.page_ = maxPages_
        } else if (p <= 1) {
            this.page_ = 1
        } else {
            this.page_ = p
        }
        startingIndex_ = pageSize_ * (page_ - 1)
        if (startingIndex_ < 0) {
            startingIndex_ = 0
        }
        endingIndex_ = startingIndex_ + pageSize_
        if (endingIndex_ > mutableArray.size) {
            endingIndex_ = mutableArray.size
        }
    }

    fun getPreviousPage(): Int {
        return if (page_ > 1) {
            page_ - 1
        } else {
            0
        }
    }

    fun getNextPage(): Int {
        return if (page_ < maxPages_) {
            page_ + 1
        } else {
            0
        }
    }

    private fun addPageItem(): MutableMap<Int, ArrayList<ModelItem>> {
        val items = ArrayList<ModelItem>()
        val itemsArray = mutableMapOf<Int, ArrayList<ModelItem>>()
        var index = 0
        var type = 3
        items.add(ModelItem("A1", "S"))
        items.add(ModelItem("A2", "S"))
        items.add(ModelItem("A3", "S"))
        items.add(ModelItem("A4", "S"))
        itemsArray.put(1, items.clone() as ArrayList<ModelItem>)
        items.clear()
        items.add(ModelItem("B1", "SA"))
        items.add(ModelItem("B2", "SA"))
        itemsArray.put(2, items.clone() as ArrayList<ModelItem>)
        items.clear()
        items.add(ModelItem("C1", "C"))
        items.add(ModelItem("C2", "C"))
        items.add(ModelItem("C3", "C"))
        items.add(ModelItem("C4", "C"))
        items.add(ModelItem("C3", "C"))
        items.add(ModelItem("C4", "C"))
        itemsArray.put(3, items.clone() as ArrayList<ModelItem>)
        items.clear()
        for (i in 1..200) {
            index++
            items.add(ModelItem("Cat$i", "$type"))
            if (index >= 10) {
                type++
                itemsArray.put(type, items.clone() as ArrayList<ModelItem>)
                items.clear()
                index = 0
            }
        }
        return itemsArray
    }


}
