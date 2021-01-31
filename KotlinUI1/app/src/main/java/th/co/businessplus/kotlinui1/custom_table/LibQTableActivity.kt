package th.co.businessplus.kotlinui1.custom_table

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_custom_table.*
import th.co.businessplus.kotlinui1.R
import th.co.businessplus.kotlinui1.custom_table.lib_qtable.*
import th.co.businessplus.kotlinui1.pagination_data.lib_pagination.BtnPageProperty
import th.co.businessplus.kotlinui1.pagination_data.lib_pagination.QPaginationMutableList


class LibQTableActivity : AppCompatActivity(), HorizontalScroll.ScrollViewListener {
    override fun onScrollChanged(scrollView: HorizontalScroll, x: Int, y: Int, oldx: Int, oldy: Int) {
        //header
        if (scrollView === headerQTable.horizontalScrollViewHeaderData) {
            headerQTable.horizontalScrollViewBodyData.scrollTo(x, y)
            footersQTable.horizontalScrollViewBodyData.scrollTo(x, y)
        } else if (scrollView === headerQTable.horizontalScrollViewBodyData) {
            headerQTable.horizontalScrollViewHeaderData.scrollTo(x, y)
            footersQTable.horizontalScrollViewBodyData.scrollTo(x, y)
        }
        //footers
        if (scrollView === footersQTable.horizontalScrollViewHeaderData) {
            headerQTable.horizontalScrollViewBodyData.scrollTo(x, y)
            headerQTable.horizontalScrollViewHeaderData.scrollTo(x, y)
        } else if (scrollView === footersQTable.horizontalScrollViewBodyData) {
            headerQTable.horizontalScrollViewBodyData.scrollTo(x, y)
            headerQTable.horizontalScrollViewHeaderData.scrollTo(x, y)
        }
    }

    lateinit var headerQTable: QTableV2;
    lateinit var footersQTable: QTableV2;

    var dataHeaderTable: MutableList<DataTable> = mutableListOf()
    var dataFootersTable: MutableList<DataTable> = mutableListOf()

    lateinit var dataFirstColum: DataHeaderFirstColum
    @JvmField
    var dataHeaderList = arrayOf<String>()
    @JvmField
    var widthColumList = arrayOf<Int>()
    @JvmField
    var textAlignmentHeaderList = arrayOf<Int>()
    @JvmField
    var textAlignmentBodyList = arrayOf<Int>()


    //lib Pagination
    lateinit var qpage: QPaginationMutableList<DataTable>
    lateinit var linearGroup: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_table)
        supportActionBar!!.title = "QTable Kotlin"

        dataFirstColum =
            DataHeaderFirstColum("Index", Color.WHITE, 16f, resources.getColor(R.color.ColorHeaderColumFirst))
        dataHeaderList = arrayOf("Colum1", "Colum2", "Colum3", "Colum4", "Colum5", "Colum6")
        widthColumList = arrayOf(1, 2, 0, 2, 0, 2)

        textAlignmentHeaderList = arrayOf(
            View.TEXT_ALIGNMENT_CENTER,
            View.TEXT_ALIGNMENT_CENTER,
            View.TEXT_ALIGNMENT_CENTER,
            View.TEXT_ALIGNMENT_CENTER,
            View.TEXT_ALIGNMENT_CENTER,
            View.TEXT_ALIGNMENT_CENTER
        )
        textAlignmentBodyList = arrayOf(
            View.TEXT_ALIGNMENT_CENTER,
            View.TEXT_ALIGNMENT_CENTER,
            View.TEXT_ALIGNMENT_TEXT_END,
            View.TEXT_ALIGNMENT_CENTER,
            View.TEXT_ALIGNMENT_TEXT_START,
            View.TEXT_ALIGNMENT_CENTER
        )


        headerQTable = QTableV2(this, relativeLayoutHeader_id)
        headerQTable.initHeaderLayoutMain(0,18,10,7,true)
        headerQTable.initTableHeaderData(
            dataFirstColum,
            dataHeaderList,
            widthColumList,
            textAlignmentHeaderList,
            16f,
            Color.WHITE,
            resources.getColor(R.color.ColorHeaderColum)
        )

        footersQTable = QTableV2(this, relativeLayoutFooter_id)
        footersQTable.initHeaderLayoutMain(0,18,10,7,false)
        footersQTable.initTableHeaderData(
            dataFirstColum,
            dataHeaderList,
            widthColumList,
            textAlignmentHeaderList,
            16f,
            Color.WHITE,
            resources.getColor(R.color.ColorFootersColum)
        )

        headerQTable.horizontalScrollViewBodyData.setScrollViewListener(this)
        headerQTable.horizontalScrollViewHeaderData.setScrollViewListener(this)

        footersQTable.horizontalScrollViewBodyData.setScrollViewListener(this)
        footersQTable.horizontalScrollViewHeaderData.setScrollViewListener(this)


        //initData header
        for (i in 1..223) {
            dataHeaderTable.add(
                DataTable(
                    "col1:" + i,
                    "col2:" + i,
                    "col3:" + i,
                    "col4:" + i,
                    "col5:" + i,
                    "col6:" + i
                )
            )
        }
        //initData footers
        dataFootersTable.add(
            DataTable(
                "Footers1:",
                "Footers2:",
                "Footers3:",
                "Footers4:",
                "Footers5:",
                "Footers6:"
            )
        )
        AddDataTableFooters()



        //lib Pagination
        linearGroup = findViewById(R.id.la_view) as LinearLayout
        var btn_previous = findViewById<Button>(R.id.btn_previous)
        var btn_next = findViewById<Button>(R.id.btn_next)
        var btnPage = BtnPageProperty(R.drawable.bg_btn1_select, R.drawable.bg_btn1_click, Color.WHITE, 16f, true)
        qpage = QPaginationMutableList(this, linearGroup, btn_previous, btn_next, btnPage)
        qpage.setPageDataList(dataHeaderTable)
        qpage.initPagination()
        qpage.pageFetchData = object : QPaginationMutableList.FetchDataListListener<DataTable> {
            override fun onFetchDataList(dataList: MutableList<out DataTable>) {
                dataHeaderTable = dataList as MutableList<DataTable>
                AddDataTableHeader()
            }
        }
        qpage.setInitPageData(1)
        qpage.ShowDataPage()

    }

    fun AddDataTableHeader() {
        //add data body
        headerQTable.StartDataTable()

        for (i in dataHeaderTable.indices) {
            if (i % 2 == 0) {
                headerQTable.AddDataFirstTable(i, dataHeaderTable[i].data[0], 16f, Color.WHITE, Color.BLACK)
            } else {
                headerQTable.AddDataFirstTable(
                    i,
                    dataHeaderTable[i].data[0],
                    16f,
                    resources.getColor(R.color.ColorBodyColum),
                    Color.BLACK
                )
            }

            for (c in 1..dataHeaderTable.get(i).data.size - 1) {
                if (i % 2 == 0) {
                    headerQTable.AddDataBodyTable(
                        i,
                        dataHeaderTable.get(i).data[c],
                        16f,
                        widthColumList[c],
                        textAlignmentBodyList[c],
                        Color.WHITE,
                        Color.BLACK
                    )
                } else {
                    headerQTable.AddDataBodyTable(
                        i,
                        dataHeaderTable.get(i).data[c],
                        16f,
                        widthColumList[c],
                        textAlignmentBodyList[c],
                        resources.getColor(R.color.ColorBodyColum),
                        Color.BLACK
                    )
                }

            }
        }
    }

    fun AddDataTableFooters() {
        //add data body
        footersQTable.StartDataTable()

        for (i in dataFootersTable.indices) {
            if (i % 2 != 0) {
                footersQTable.AddDataFirstTable(i, dataFootersTable[i].data[0], 16f, Color.WHITE, Color.BLACK)
            } else {
                footersQTable.AddDataFirstTable(
                    i,
                    dataFootersTable[i].data[0],
                    16f,
                    resources.getColor(R.color.ColorFootersColum),
                    Color.WHITE
                )
            }

            for (c in 1..dataFootersTable.get(i).data.size - 1) {
                if (i % 2 != 0) {
                    footersQTable.AddDataBodyTable(
                        i,
                        dataFootersTable.get(i).data[c],
                        16f,
                        widthColumList[c],
                        textAlignmentHeaderList[c],
                        Color.WHITE,
                        Color.BLACK
                    )
                } else {
                    footersQTable.AddDataBodyTable(
                        i,
                        dataFootersTable.get(i).data[c],
                        16f,
                        widthColumList[c],
                        textAlignmentHeaderList[c],
                        resources.getColor(R.color.ColorFootersColum),
                        Color.WHITE
                    )
                }

            }
        }
    }
}
