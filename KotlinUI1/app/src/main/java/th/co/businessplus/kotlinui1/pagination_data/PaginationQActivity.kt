package th.co.businessplus.kotlinui1.pagination_data

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import th.co.businessplus.kotlinui1.R
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_pagination2.*
import th.co.businessplus.kotlinui1.pagination_data.lib_pagination.BtnPageProperty
import th.co.businessplus.kotlinui1.pagination_data.lib_pagination.QPaginationArrayList


class PaginationQActivity : AppCompatActivity() {

    lateinit var dataList: ArrayList<ModelItemPage>
    lateinit var linearGroup: LinearLayout
    @JvmField
    var stringBuffer = StringBuffer()


    lateinit var qpage: QPaginationArrayList<ModelItemPage>

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination2)

        linearGroup = findViewById(R.id.la_view) as LinearLayout
        var btn_previous = findViewById<Button>(R.id.btn_previous)
        var btn_next = findViewById<Button>(R.id.btn_next)
        dataList = addData1()
        txtLoad.text = "Load A Size:${dataList.size}"


        var btnPage = BtnPageProperty(R.drawable.bg_btn1_select, R.drawable.bg_btn1_click, Color.WHITE, 16f, true)
        qpage = QPaginationArrayList(this, linearGroup, btn_previous, btn_next, btnPage)
        qpage.setPageDataList(dataList)
        qpage.initPagination()
        qpage.pageFetchData = object : QPaginationArrayList.FetchDataListListener<ModelItemPage> {
            override fun onFetchDataList(data: List<ModelItemPage>) {
                stringBuffer.setLength(0)
                for (i in data) {
                    stringBuffer.append("${i.pagename} | ${i.page} \n")
                }
                txtData.text = stringBuffer.toString()

            }
        }

        btn_load1.setOnClickListener {
            dataList = addData1()
            txtLoad.text = "Load A Size:${dataList.size}"
            qpage.setPageDataList(dataList)
            qpage.initPagination()
        }
        btn_load2.setOnClickListener {
            dataList = addData2()
            txtLoad.text = "Load B Size:${dataList.size}"
            qpage.setPageDataList(dataList)
            qpage.initPagination()
        }
    }


    private fun addData1(): ArrayList<ModelItemPage> {
        val pageList = arrayListOf<ModelItemPage>()
        for (i in 1..153) {//127
            pageList.add(ModelItemPage("A Data$i", i))
        }
        return pageList
    }
    private fun addData2(): ArrayList<ModelItemPage> {
        val pageList = arrayListOf<ModelItemPage>()
        for (i in 1..127) {//127
            pageList.add(ModelItemPage("B Data$i", i))
        }
        return pageList
    }
}
