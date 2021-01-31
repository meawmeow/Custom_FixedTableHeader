package th.co.businessplus.kotlinui1.custom_table_report

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_table_report.*
import th.co.businessplus.kotlinui1.R
import th.co.businessplus.kotlinui1.custom_table.lib_qtable.DataTable
import th.co.businessplus.kotlinui1.custom_table.lib_qtable.HorizontalScroll
import th.co.businessplus.kotlinui1.custom_table.lib_qtable.VerticalScroll

class TableReportActivity : AppCompatActivity(), HorizontalScroll.ScrollViewListener,
    VerticalScroll.ScrollViewListener {
    override fun onScrollChanged(scrollView: HorizontalScroll, x: Int, y: Int, oldx: Int, oldy: Int) {
        if (scrollView === horizontalScrollViewHeaderData || scrollView === horizontalScrollViewBodyData) {
            horizontalScrollViewBodyData.scrollTo(x, y)
            horizontalScrollViewFootersBodyData.scrollTo(x, y)
        } else if (scrollView === horizontalScrollViewBodyData) {
            horizontalScrollViewHeaderData.scrollTo(x, y)
            horizontalScrollViewFootersHeaderColum.scrollTo(x, y)
        } else if (scrollView === horizontalScrollViewFootersBodyData) {
            horizontalScrollViewHeaderData.scrollTo(x, y)
            horizontalScrollViewBodyData.scrollTo(x, y)
        }

        if (scrollView === horizontalScrollViewFootersHeaderColum) {
            horizontalScrollViewBodyData.scrollTo(x, y)
            horizontalScrollViewFootersBodyData.scrollTo(x, y)
        } else if (scrollView === horizontalScrollViewFootersBodyData) {
            horizontalScrollViewHeaderData.scrollTo(x, y)
            horizontalScrollViewFootersHeaderColum.scrollTo(x, y)
        }

    }

    override fun onScrollChanged(scrollView: VerticalScroll, x: Int, y: Int, oldx: Int, oldy: Int) {
        if (scrollView === verticalScrollViewBodyDataFirst) {
            verticalScrollViewBodyData.scrollTo(x, y)
        } else if (scrollView === verticalScrollViewBodyData) {
            verticalScrollViewBodyDataFirst.scrollTo(x, y)
        }

    }

    private var SCREEN_HEIGHT: Int = 0
    private var SCREEN_WIDTH: Int = 0

    lateinit var relativeLayoutHeaderColumFirstFix: RelativeLayout
    lateinit var relativeLayoutHeaderColumScroll: RelativeLayout
    lateinit var relativeLayoutBodyDataFirst: RelativeLayout
    lateinit var relativeLayoutBodyDataScroll: RelativeLayout

    lateinit var tableLayoutHeaderColumFirstFix: TableLayout
    lateinit var tableLayoutHeaderColumScroll: TableLayout
    lateinit var tableLayoutBodyDataColumFirst: TableLayout
    lateinit var tableLayoutBodyDataColumScroll: TableLayout

    lateinit var tableRowAFix: TableRow
    lateinit var tableRowAScroll: TableRow

    lateinit var horizontalScrollViewHeaderData: HorizontalScroll
    lateinit var horizontalScrollViewBodyData: HorizontalScroll

    lateinit var verticalScrollViewBodyDataFirst: VerticalScroll
    lateinit var verticalScrollViewBodyData: VerticalScroll

    var tableColumnHeaderCountB = 0
    var tableRowCountHeader = 0
    var RowHeaderHeight = 18

    var FirstColumWidthSize = 5
    var ColumWidthSize = 5


    var widthsColumHeader = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
    var widthsColumFooter = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)

    var relativeLayoutFootersHeaderColumFirstFix: RelativeLayout? = null
    var relativeLayoutFootersHeaderColumScroll: RelativeLayout? = null
    var relativeLayoutFootersBodyDataFirst: RelativeLayout? = null
    var relativeLayoutFootersBodyDataScroll: RelativeLayout? = null


    var HeaderFooterHeight = 200
    var RowFooterHeight = 15

    lateinit var horizontalScrollViewFootersHeaderColum: HorizontalScroll
    lateinit var horizontalScrollViewFootersBodyData: HorizontalScroll

    lateinit var verticalScrollViewFootersBodyDataFirst: VerticalScroll
    lateinit var verticalScrollViewFootersBodyData: VerticalScroll

    lateinit var tableLayoutFootersHeaderColumFirst: TableLayout
    lateinit var tableLayoutFootersHeaderColum: TableLayout
    lateinit var tableLayoutFootersBodyDataFirst: TableLayout
    lateinit var tableLayoutFootersBodyData: TableLayout

    lateinit var tableRowBFix: TableRow
    lateinit var tableRowBScroll: TableRow

    var tableColumnFootersCountB = 0
    var tableRowCountFooters = 0

    var dataHeaderTable: MutableList<DataTable> = mutableListOf()
    var dataFooterTable: MutableList<DataTable> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_report)

        getScreenDimension()


        //Header
        initializeRelativeLayoutHeaderMain()
        initializeScrollersLayoutHeaderMain()
        initializeTableLayoutHeaderMain()

        addRowToTableHeaderColumFirstFix("RowFirst", Color.WHITE, 18f)
        initializeRowForTableHeaderColumScroll()
        var dataListHeader = arrayOf("Colum1", "Colum2", "Colum3", "Colum4", "Colum5", "Colum6", "Colum7", "Colum8")
        for (i in 1 until dataListHeader.size) {
            addColumnsToTableHeaderColumScroll(i, dataListHeader[i], widthsColumHeader[i], View.TEXT_ALIGNMENT_CENTER)
        }
        //Footer
        initializeRelativeLayoutFootersMain()
        initializeScrollersLayoutFootersMain()
        initializeTableLayoutFootersMain()

        addRowToTableFootersColumFirstFix()
        initializeRowForTableFootersColumScroll()
        val dataListHeaderFooters = arrayOf("", "", "", "", "", "", "", "")
        for (i in 1 until dataListHeaderFooters.size) {
            addColumnsToTableFootersColumScroll(
                i,
                dataListHeaderFooters[i],
                widthsColumFooter[i],
                View.TEXT_ALIGNMENT_CENTER
            )
        }



        addTableData()

    }

    fun addTableData() {
        //add data
        for (i in 1..20) {
            dataHeaderTable.add(
                DataTable(
                    "col1:" + i,
                    "col2:" + i,
                    "col3:" + i,
                    "col4:" + i,
                    "col5:" + i,
                    "col6:" + i,
                    "col7:" + i,
                    "col8:" + i
                )
            )
        }
        dataFooterTable.add(
            DataTable(
                "f1",
                "f2",
                "f3",
                "f4",
                "f5",
                "f6",
                "f7",
                "f8"
            )
        )
        CreateHeaderTableModel()
        CreateFootersTableModel()
    }

    private fun getScreenDimension() {
        val wm = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        SCREEN_WIDTH = size.x
        SCREEN_HEIGHT = size.y
    }

    private fun initializeRelativeLayoutHeaderMain(visibleHeader: Boolean = true) {
        //init layout Header data
        relativeLayoutHeaderColumFirstFix = RelativeLayout(applicationContext)
        relativeLayoutHeaderColumFirstFix.id = R.id.relativeIdHeaderColumFirstFix
        relativeLayoutHeaderColumFirstFix.setPadding(0, 0, 0, 0)
        if (!visibleHeader)
            relativeLayoutHeaderColumFirstFix.visibility = View.GONE

        relativeLayoutHeaderColumScroll = RelativeLayout(applicationContext)
        relativeLayoutHeaderColumScroll.id = R.id.relativeIdHeaderColumScroll
        relativeLayoutHeaderColumScroll.setPadding(0, 0, 0, 0)
        if (!visibleHeader)
            relativeLayoutHeaderColumScroll.visibility = View.GONE

        ///init layout Body data
        relativeLayoutBodyDataFirst = RelativeLayout(applicationContext)
        relativeLayoutBodyDataFirst.id = R.id.relativeIdBodyDataFirst
        relativeLayoutBodyDataFirst.setPadding(0, 0, 0, 0)

        relativeLayoutBodyDataScroll = RelativeLayout(applicationContext)
        relativeLayoutBodyDataScroll.id = R.id.relativeIdBodyDataScroll
        relativeLayoutBodyDataScroll.setPadding(0, 0, 0, 0)


        //Main layout Header Data
        relativeLayoutHeaderColumFirstFix.layoutParams =
            RelativeLayout.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowHeaderHeight)
        //relativeLayoutHeaderColumFirstFix.setBackgroundColor(Color.GREEN)
        this.relativeLayoutHeader.addView(relativeLayoutHeaderColumFirstFix)

        val layoutParamsRelativeHeaderColumScroll =
            RelativeLayout.LayoutParams(
                SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 2),
                SCREEN_HEIGHT / RowHeaderHeight
            )
        layoutParamsRelativeHeaderColumScroll.addRule(RelativeLayout.RIGHT_OF, R.id.relativeIdHeaderColumFirstFix)
        relativeLayoutHeaderColumScroll.layoutParams = layoutParamsRelativeHeaderColumScroll
        ///relativeLayoutHeaderColumScroll.setBackgroundColor(Color.RED)
        this.relativeLayoutHeader.addView(relativeLayoutHeaderColumScroll)


        //Main layout Body Data
        val layoutParamsRelativeBodyDataFirst =
            RelativeLayout.LayoutParams(
                SCREEN_WIDTH / FirstColumWidthSize,
                SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeaderHeight
            )
        layoutParamsRelativeBodyDataFirst.addRule(RelativeLayout.BELOW, R.id.relativeIdHeaderColumFirstFix)
        relativeLayoutBodyDataFirst.layoutParams = layoutParamsRelativeBodyDataFirst
        //relativeLayoutBodyDataFirst.setBackgroundColor(Color.GREEN)
        this.relativeLayoutHeader.addView(relativeLayoutBodyDataFirst)

        val layoutParamsRelativeBodyDataScroll = RelativeLayout.LayoutParams(
            SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 3),
            SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeaderHeight
        )
        layoutParamsRelativeBodyDataScroll.addRule(RelativeLayout.BELOW, R.id.relativeIdHeaderColumScroll)
        layoutParamsRelativeBodyDataScroll.addRule(RelativeLayout.RIGHT_OF, R.id.relativeIdBodyDataFirst)
        relativeLayoutBodyDataScroll.layoutParams = layoutParamsRelativeBodyDataScroll
        //relativeLayoutBodyDataScroll.setBackgroundColor(Color.GREEN)
        this.relativeLayoutHeader.addView(relativeLayoutBodyDataScroll)

    }

    private fun initializeScrollersLayoutHeaderMain() {
        horizontalScrollViewHeaderData =
            HorizontalScroll(applicationContext)
        horizontalScrollViewHeaderData.setPadding(0, 0, 0, 0)

        verticalScrollViewBodyDataFirst =
            VerticalScroll(applicationContext)
        verticalScrollViewBodyDataFirst.setPadding(0, 0, 0, 0)

        horizontalScrollViewBodyData =
            HorizontalScroll(applicationContext)
        horizontalScrollViewBodyData.setPadding(0, 0, 0, 0)

        verticalScrollViewBodyData =
            VerticalScroll(applicationContext)
        verticalScrollViewBodyData.setPadding(0, 1, 0, 0)

        horizontalScrollViewHeaderData.layoutParams =
            ViewGroup.LayoutParams(SCREEN_WIDTH - SCREEN_WIDTH / 5, SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeaderHeight)

        verticalScrollViewBodyDataFirst.layoutParams =
            ViewGroup.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeaderHeight)

        verticalScrollViewBodyData.layoutParams =
            ViewGroup.LayoutParams(
                SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 2),
                SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeaderHeight
            )

        horizontalScrollViewBodyData.layoutParams =
            ViewGroup.LayoutParams(
                SCREEN_WIDTH - SCREEN_WIDTH / FirstColumWidthSize,
                SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeaderHeight
            )

        this.relativeLayoutHeaderColumScroll.addView(horizontalScrollViewHeaderData)
        this.relativeLayoutBodyDataFirst.addView(verticalScrollViewBodyDataFirst)

        this.verticalScrollViewBodyData.addView(horizontalScrollViewBodyData)
        this.relativeLayoutBodyDataScroll.addView(verticalScrollViewBodyData)


        horizontalScrollViewHeaderData.setScrollViewListener(this)
        horizontalScrollViewBodyData.setScrollViewListener(this)
        verticalScrollViewBodyDataFirst.setScrollViewListener(this)
        verticalScrollViewBodyData.setScrollViewListener(this)

    }

    @SuppressLint("ResourceAsColor")
    private fun initializeTableLayoutHeaderMain() {
        tableLayoutHeaderColumFirstFix = TableLayout(applicationContext)
        tableLayoutHeaderColumFirstFix.setPadding(0, 0, 0, 0)
        tableLayoutHeaderColumScroll = TableLayout(applicationContext)
        tableLayoutHeaderColumScroll.setPadding(0, 0, 0, 0)
        //tableLayoutHeaderColumScroll.id = R.id.tableLayoutB

        tableLayoutBodyDataColumFirst = TableLayout(applicationContext)
        tableLayoutBodyDataColumFirst.setPadding(0, 1, 0, 0)
        tableLayoutBodyDataColumScroll = TableLayout(applicationContext)
        tableLayoutBodyDataColumScroll.setPadding(0, 0, 0, 0)


        //table header Colum First Fix
        val layoutParamsTableLayoutHeaderColumFirstFix =
            TableLayout.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowHeaderHeight)
        tableLayoutHeaderColumFirstFix.layoutParams = layoutParamsTableLayoutHeaderColumFirstFix
        //tableLayoutHeaderColumFirstFix.setBackgroundColor(Color.RED)
        this.relativeLayoutHeaderColumFirstFix.addView(tableLayoutHeaderColumFirstFix)

        //table header Colum Scroll to ScrollView
        val layoutParamsTableLayoutHeaderColumScroll =
            TableLayout.LayoutParams(SCREEN_WIDTH - SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowHeaderHeight)
        tableLayoutHeaderColumScroll.layoutParams = layoutParamsTableLayoutHeaderColumScroll
        //tableLayoutHeaderColumScroll.setBackgroundColor(Color.MAGENTA)
        this.horizontalScrollViewHeaderData.addView(tableLayoutHeaderColumScroll)


        //Body Data Colum First to ScrollView
        val layoutParamsTableLayoutBodyDataColumFirst =
            TableLayout.LayoutParams(SCREEN_WIDTH / 2, SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeaderHeight)
        tableLayoutBodyDataColumFirst.layoutParams = layoutParamsTableLayoutBodyDataColumFirst
        ///tableLayoutBodyDataColumFirst.setBackgroundColor(Color.BLUE);
        this.verticalScrollViewBodyDataFirst.addView(tableLayoutBodyDataColumFirst)

        //Body Data Colum Scroll to ScrollView
        val layoutParamsTableLayoutBodyDataColumScroll =
            TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
        tableLayoutBodyDataColumScroll.layoutParams = layoutParamsTableLayoutBodyDataColumScroll
        //tableLayoutBodyDataColumScroll.setBackgroundColor(Color.GRAY)
        this.horizontalScrollViewBodyData.addView(tableLayoutBodyDataColumScroll)

    }

    private fun addRowToTableHeaderColumFirstFix(
        str: String = "First",
        txtColor: Int = Color.WHITE,
        txtSize: Float = 16f,
        backGroundColor: Int = Color.RED
    ) {
        tableRowAFix = TableRow(applicationContext)
        tableRowAFix.gravity = Gravity.CENTER_HORIZONTAL
        tableRowAFix.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowHeaderHeight)

        val txtView = TextView(applicationContext)
        txtView.text = str
        txtView.setTextColor(txtColor)
        txtView.textSize = txtSize
        tableRowAFix.addView(txtView)
        this.tableLayoutHeaderColumFirstFix.gravity = Gravity.CENTER
        this.tableLayoutHeaderColumFirstFix.setBackgroundColor(backGroundColor)
        this.tableLayoutHeaderColumFirstFix.addView(tableRowAFix)

    }

    private fun initializeRowForTableHeaderColumScroll(
        backGroudColor: Int = Color.BLACK
    ) {
        tableRowAScroll = TableRow(applicationContext)
        //tableRowAFix.setPadding(0, 0, 0, 0)
        //this.tableLayoutHeaderColumScroll.setBackgroundColor(backGroudColor)
        this.tableLayoutHeaderColumScroll.addView(tableRowAScroll)
    }

    @Synchronized
    private fun addColumnsToTableHeaderColumScroll(
        id: Int,
        text: String,
        width: Int,
        TEXT_ALIGNMENT: Int,
        txtSize: Float = 16f,
        txtColor: Int = Color.WHITE,
        backGroudColor: Int = Color.LTGRAY
    ) {
        this.tableLayoutHeaderColumScroll.setBackgroundColor(backGroudColor)
        tableRowAFix = TableRow(applicationContext)
        //tableRowAFix.background = resources.getDrawable(R.drawable.cell_bacground)
        tableRowAFix.gravity = Gravity.CENTER
        tableRowAFix.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / (ColumWidthSize - width), SCREEN_HEIGHT / RowHeaderHeight)

        val txtView = TextView(applicationContext)
        txtView.text = text
        txtView.textAlignment = TEXT_ALIGNMENT
        txtView.textSize = txtSize
        txtView.setTextColor(txtColor)
        this.tableRowAFix.addView(txtView)
        this.tableRowAFix.tag = id

        this.tableRowAScroll.addView(tableRowAFix)
        tableColumnHeaderCountB++
    }

    private fun initializeRelativeLayoutFootersMain() {
        //init layout Header data
        relativeLayoutFootersHeaderColumFirstFix = RelativeLayout(applicationContext)
        relativeLayoutFootersHeaderColumFirstFix!!.setId(R.id.relativeIdHeaderColumFirstFix)
        relativeLayoutFootersHeaderColumFirstFix!!.setPadding(0, 0, 0, 0)

        relativeLayoutFootersHeaderColumScroll = RelativeLayout(applicationContext)
        relativeLayoutFootersHeaderColumScroll!!.setId(R.id.relativeIdHeaderColumScroll)
        relativeLayoutFootersHeaderColumScroll!!.setPadding(0, 0, 0, 0)

        ///init layout Body data
        relativeLayoutFootersBodyDataFirst = RelativeLayout(applicationContext)
        relativeLayoutFootersBodyDataFirst!!.setId(R.id.relativeIdBodyDataFirst)
        relativeLayoutFootersBodyDataFirst!!.setPadding(0, 0, 0, 0)

        relativeLayoutFootersBodyDataScroll = RelativeLayout(applicationContext)
        relativeLayoutFootersBodyDataScroll!!.setId(R.id.relativeIdBodyDataScroll)
        relativeLayoutFootersBodyDataScroll!!.setPadding(0, 1, 0, 0)


        //Main layout Header data
        relativeLayoutFootersHeaderColumFirstFix!!.setLayoutParams(
            RelativeLayout.LayoutParams(
                SCREEN_WIDTH / FirstColumWidthSize,
                SCREEN_HEIGHT / HeaderFooterHeight
            )
        )
        relativeLayoutFooter!!.addView(relativeLayoutFootersHeaderColumFirstFix)

        val layoutParamsRelativeHeaderColumScroll = RelativeLayout.LayoutParams(
            SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 2),
            SCREEN_HEIGHT / HeaderFooterHeight
        )
        layoutParamsRelativeHeaderColumScroll.addRule(RelativeLayout.RIGHT_OF, R.id.relativeIdHeaderColumFirstFix)
        relativeLayoutFootersHeaderColumScroll!!.setLayoutParams(layoutParamsRelativeHeaderColumScroll)
        relativeLayoutFooter!!.addView(relativeLayoutFootersHeaderColumScroll)


        //Main layout Body Data
        val layoutParamsRelativeBodyDataFirst =
            RelativeLayout.LayoutParams(
                SCREEN_WIDTH / FirstColumWidthSize,
                SCREEN_HEIGHT - SCREEN_HEIGHT / HeaderFooterHeight
            )
        layoutParamsRelativeBodyDataFirst.addRule(RelativeLayout.BELOW, R.id.relativeIdHeaderColumFirstFix)
        relativeLayoutFootersBodyDataFirst!!.setLayoutParams(layoutParamsRelativeBodyDataFirst)
        relativeLayoutFooter!!.addView(relativeLayoutFootersBodyDataFirst)

        val layoutParamsRelativeBodyDataScroll = RelativeLayout.LayoutParams(
            SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 3),
            SCREEN_HEIGHT - SCREEN_HEIGHT / RowFooterHeight
        )
        layoutParamsRelativeBodyDataScroll.addRule(RelativeLayout.BELOW, R.id.relativeIdHeaderColumScroll)
        layoutParamsRelativeBodyDataScroll.addRule(RelativeLayout.RIGHT_OF, R.id.relativeIdBodyDataFirst)
        relativeLayoutFootersBodyDataScroll!!.setLayoutParams(layoutParamsRelativeBodyDataScroll)
        relativeLayoutFootersBodyDataScroll!!.setBackgroundColor(Color.WHITE)
        relativeLayoutFooter!!.addView(relativeLayoutFootersBodyDataScroll)
    }

    private fun initializeScrollersLayoutFootersMain() {//horizontalScrollViewHeaderData   verticalScrollViewBodyDataFirst  horizontalScrollViewBodyData   verticalScrollViewBodyData
        horizontalScrollViewFootersHeaderColum =
            HorizontalScroll(applicationContext)
        horizontalScrollViewFootersHeaderColum.setPadding(0, 0, 0, 0)

        horizontalScrollViewFootersBodyData =
            HorizontalScroll(applicationContext)
        horizontalScrollViewFootersBodyData.setPadding(0, 0, 0, 0)

        verticalScrollViewFootersBodyDataFirst =
            VerticalScroll(applicationContext)
        verticalScrollViewFootersBodyDataFirst.setPadding(0, 0, 0, 0)

        verticalScrollViewFootersBodyData =
            VerticalScroll(applicationContext)
        verticalScrollViewFootersBodyData.setPadding(0, 0, 0, 0)

        horizontalScrollViewFootersHeaderColum.setLayoutParams(
            ViewGroup.LayoutParams(
                SCREEN_WIDTH - SCREEN_WIDTH / 5,
                SCREEN_HEIGHT / RowFooterHeight
            )
        )

        verticalScrollViewFootersBodyDataFirst.setLayoutParams(
            ViewGroup.LayoutParams(
                SCREEN_WIDTH / FirstColumWidthSize,
                SCREEN_HEIGHT - SCREEN_HEIGHT / RowFooterHeight
            )
        )

        verticalScrollViewFootersBodyData.setLayoutParams(
            ViewGroup.LayoutParams(
                SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 2),
                SCREEN_HEIGHT - SCREEN_HEIGHT / RowFooterHeight
            )
        )

        horizontalScrollViewFootersBodyData.setLayoutParams(
            ViewGroup.LayoutParams(
                SCREEN_WIDTH - SCREEN_WIDTH / FirstColumWidthSize,
                SCREEN_HEIGHT - SCREEN_HEIGHT / RowFooterHeight
            )
        )

        this.relativeLayoutFootersHeaderColumScroll!!.addView(horizontalScrollViewFootersHeaderColum)
        this.relativeLayoutFootersBodyDataFirst!!.addView(verticalScrollViewFootersBodyDataFirst)
        this.verticalScrollViewFootersBodyData.addView(horizontalScrollViewFootersBodyData)
        this.relativeLayoutFootersBodyDataScroll!!.addView(verticalScrollViewFootersBodyData)

        horizontalScrollViewFootersHeaderColum.setScrollViewListener(this)
        horizontalScrollViewFootersBodyData.setScrollViewListener(this)
        verticalScrollViewFootersBodyDataFirst.setScrollViewListener(this)
        verticalScrollViewFootersBodyData.setScrollViewListener(this)
    }

    @SuppressLint("ResourceAsColor")
    private fun initializeTableLayoutFootersMain() {
        tableLayoutFootersHeaderColumFirst = TableLayout(applicationContext)
        tableLayoutFootersHeaderColumFirst.setPadding(0, 0, 0, 0)
        tableLayoutFootersHeaderColum = TableLayout(applicationContext)
        tableLayoutFootersHeaderColum.setPadding(0, 0, 0, 0)
        //tableLayoutFootersHeaderColum.setId(R.id.tableLayoutB)

        tableLayoutFootersBodyDataFirst = TableLayout(applicationContext)
        tableLayoutFootersBodyDataFirst.setPadding(0, 1, 0, 0)
        tableLayoutFootersBodyData = TableLayout(applicationContext)
        tableLayoutFootersBodyData.setPadding(0, 0, 0, 0)

        //table header Colum First Fix
        val layoutParamsTableLayoutHeaderColumFirst =
            TableLayout.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowFooterHeight)
        tableLayoutFootersHeaderColumFirst.setLayoutParams(layoutParamsTableLayoutHeaderColumFirst)
        tableLayoutFootersHeaderColumFirst.setBackgroundColor(R.color.colorRowIndex)
        this.relativeLayoutFootersHeaderColumFirstFix!!.addView(tableLayoutFootersHeaderColumFirst)


        //table header Colum Scroll to ScrollView
        val layoutParamsTableLayoutHeaderColum =
            TableLayout.LayoutParams(SCREEN_WIDTH - SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowFooterHeight)
        tableLayoutFootersHeaderColum.setLayoutParams(layoutParamsTableLayoutHeaderColum)
        tableLayoutFootersHeaderColum.setBackgroundColor(resources.getColor(R.color.colorColmun))
        this.horizontalScrollViewFootersHeaderColum.addView(tableLayoutFootersHeaderColum)

        //Body Data Colum First to ScrollView
        val layoutParamsTableLayoutBodyDataFirst =
            TableLayout.LayoutParams(SCREEN_WIDTH / 2, SCREEN_HEIGHT - SCREEN_HEIGHT / RowFooterHeight)
        tableLayoutFootersBodyDataFirst.setLayoutParams(layoutParamsTableLayoutBodyDataFirst)
        //        tableLayoutBodyDataColumFirst.setBackgroundColor(getResources().getColor(R.color.colorOrenDR1));
        this.verticalScrollViewFootersBodyDataFirst.addView(tableLayoutFootersBodyDataFirst)

        //Body Data Colum scroll to ScrollView
        val layoutParamsTableLayoutBodyData =
            TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
        tableLayoutFootersBodyData.setLayoutParams(layoutParamsTableLayoutBodyData)
        tableLayoutFootersBodyData.setBackgroundColor(R.color.colorWhiteDR2)
        this.horizontalScrollViewFootersBodyData.addView(tableLayoutFootersBodyData)

    }

    private fun addRowToTableFootersColumFirstFix(visibleHeader: Boolean = false, backGroudColor: Int = Color.WHITE) {
        tableRowBFix = TableRow(applicationContext)
        tableRowBFix.setLayoutParams(TableRow.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / 40))

        val txtView = TextView(applicationContext)
        //txtView.setPadding(-1, 10, 0, 1)
        txtView.text = ""
        txtView.setTextColor(Color.WHITE)
        tableRowBFix.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
        //txtView.textSize = resources.getDimension(R.dimen.cell_text_size14)
        tableRowBFix.addView(txtView)
        tableLayoutFootersHeaderColumFirst.setBackgroundColor(backGroudColor)
        if (visibleHeader)
            this.tableLayoutFootersHeaderColumFirst.addView(tableRowBFix)
    }

    private fun initializeRowForTableFootersColumScroll(
        visibleHeader: Boolean = false
    ) {
        tableRowBScroll = TableRow(applicationContext)
        //tableRow2.setPadding(0, 0, 0, 0);
        //this.tableLayoutFootersHeaderColum.setBackgroundColor(backGroudColor)
        if (visibleHeader)
            this.tableLayoutFootersHeaderColum.addView(tableRowBScroll)
    }

    @Synchronized
    private fun addColumnsToTableFootersColumScroll(
        id: Int,
        text: String,
        width: Int,
        TEXT_ALIGNMENT: Int,
        txtSize: Float = 16f,
        txtColor: Int = Color.WHITE,
        backGroudColor: Int = Color.LTGRAY
    ) {

        this.tableLayoutFootersHeaderColum.setBackgroundColor(backGroudColor)
        tableRowBFix = TableRow(applicationContext)
        tableRowBFix.setPadding(3, 3, 3, 4)
        tableRowBFix.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / (ColumWidthSize - width), SCREEN_HEIGHT / RowFooterHeight)

        val txtView = TextView(applicationContext)
        txtView.text = text
        txtView.textAlignment = TEXT_ALIGNMENT
        txtView.setPadding(-1, 10, 0, -1)
        txtView.textSize = txtSize
        txtView.setTextColor(txtColor)
        this.tableRowBFix.addView(txtView)
        this.tableRowBFix.tag = id

        this.tableRowBScroll.addView(tableRowBFix)
        tableColumnFootersCountB++
    }


    ///table fetch data

    private fun CreateHeaderTableModel() {
        tableLayoutBodyDataColumScroll.removeAllViews()
        tableLayoutBodyDataColumFirst.removeAllViews()
        tableRowCountHeader = 0
        //Log.d("DSSW", "SIZE:" + dataHeaderTable.get(0).data.size)
        for (i in dataHeaderTable.indices) {
            initializeRowForTableData(i)
            if (i % 2 == 0) {
                addRowToTableAtFirst(i, dataHeaderTable.get(i).data[0],16f, Color.WHITE, Color.BLACK)
            } else {
                addRowToTableAtFirst(i, dataHeaderTable.get(i).data[0],16f, Color.LTGRAY, Color.BLACK)
            }

            for (c in 1..dataHeaderTable.get(i).data.size - 1) {
                //Log.d("DSSW", " Row:" + i + " colum:" + c + " header:" + dataHeaderTable.get(i).data[c])
                if (i % 2 == 0) {
                    addColumnToTableAtScroll(
                        i,
                        dataHeaderTable.get(i).data[c],
                        16f,
                        widthsColumHeader[c],
                        View.TEXT_ALIGNMENT_CENTER,
                        Color.WHITE,
                        Color.BLACK
                    )
                } else {
                    addColumnToTableAtScroll(
                        i,
                        dataHeaderTable.get(i).data[c],
                        16f,
                        widthsColumHeader[c],
                        View.TEXT_ALIGNMENT_CENTER,
                        Color.LTGRAY,
                        Color.BLACK
                    )
                }

            }
        }
    }

    @Synchronized
    private fun initializeRowForTableData(pos: Int) {
        val tableRowB = TableRow(applicationContext)
        //tableRowB.setPadding(-1, 1, 0, -1)
        tableRowB.layoutParams =
            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, SCREEN_HEIGHT / RowHeaderHeight)
        this.tableLayoutBodyDataColumScroll.addView(tableRowB, pos)
    }

    @Synchronized
    private fun addRowToTableAtFirst(i: Int, text: String,txtSize: Float = 16f, ROW_COLOR: Int, TXT_COLOR: Int) {//First Row
        val tableRowData = TableRow(applicationContext)
        tableRowData.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowHeaderHeight)

        val txtView = TextView(applicationContext)
        txtView.text = text
        txtView.textSize = txtSize
        txtView.setTextColor(TXT_COLOR)
        txtView.gravity = Gravity.CENTER or Gravity.BOTTOM
        tableRowData.addView(txtView)
        tableRowData.setBackgroundColor(ROW_COLOR)
        tableRowData.gravity = Gravity.CENTER

        val tableRow = TableRow(applicationContext)
        tableRow.setBackgroundColor(ROW_COLOR)
        //tableRowData.setPadding(-1,1,0,-1)
        tableRowData.background = resources.getDrawable(R.drawable.cell_first_bacground)
        tableRow.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowHeaderHeight)

        tableRow.addView(tableRowData)
        this.tableLayoutBodyDataColumFirst.addView(tableRow, tableRowCountHeader)
        tableRowCountHeader++
    }

    @Synchronized
    private fun addColumnToTableAtScroll(
        rowPos: Int,
        text: String,
        txtSize: Float = 16f,
        width: Int,
        TEXT_ALIGNMENT: Int,
        ROW_COLOR: Int,
        TXT_COLOR: Int
    ) {
        val tableRowAdd = this.tableLayoutBodyDataColumScroll.getChildAt(rowPos) as TableRow
        tableRowAFix = TableRow(applicationContext)
        tableRowAFix.background = resources.getDrawable(R.drawable.cell_bacground)
        tableRowAFix.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / (ColumWidthSize - width), (SCREEN_HEIGHT / RowHeaderHeight))

        val txtView = TextView(applicationContext)
        txtView.text = "" + text
        txtView.setTextColor(TXT_COLOR)
        txtView.textAlignment = TEXT_ALIGNMENT
        txtView.textSize = txtSize
        tableRowAFix.gravity = Gravity.CENTER
        tableRowAFix.tag = txtView
        //this.tableRowAFix.setPadding(-1,1,0,1)
        this.tableRowAFix.addView(txtView)
        tableRowAdd.setBackgroundColor(ROW_COLOR)
        tableRowAdd.addView(tableRowAFix)
    }

    private fun CreateFootersTableModel() {
        tableLayoutFootersBodyData.removeAllViews()
        tableLayoutFootersBodyDataFirst.removeAllViews()
        tableRowCountFooters = 0

        for (i in dataFooterTable.indices) {
            initializeRowForTableDataFooters(i)
            addRowToTableAtFirstFooters(
                i,
                dataFooterTable.get(i).data[0],
                16f,
                Color.RED,
                Color.WHITE
            )
            for (c in 1..dataHeaderTable.get(i).data.size - 1) {
                addColumnToTableAtScrollFooters(
                    i,
                    dataFooterTable.get(i).data[c],
                    16f,
                    widthsColumFooter[c],
                    View.TEXT_ALIGNMENT_CENTER,
                    Color.DKGRAY,
                    Color.WHITE
                )
            }
        }

    }

    @Synchronized
    private fun initializeRowForTableDataFooters(pos: Int) {
        val tableRow = TableRow(applicationContext)
        tableRow.setPadding(-1, 1, 0, -1)
        tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, SCREEN_HEIGHT / 40)
        this.tableLayoutFootersBodyData.addView(tableRow, pos)
    }

    @Synchronized
    private fun addRowToTableAtFirstFooters(i: Int, text: String,txtSize: Float = 16f, ROW_COLOR: Int, TXT_COLOR: Int) {
        val tableRowData = TableRow(applicationContext)
        //tableRowData.setPadding(-1, -2, 0, -2)
        tableRowData.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowFooterHeight)

        val txtView = TextView(applicationContext)
        //txtView.setPadding(-1, 15, 0, -1)
        txtView.text = text
        txtView.textSize = txtSize
        txtView.setTextColor(TXT_COLOR)
        txtView.gravity = Gravity.CENTER or Gravity.BOTTOM
        tableRowData.addView(txtView)
        tableRowData.setBackgroundColor(ROW_COLOR)
        tableRowData.gravity = Gravity.CENTER

        val tableRow = TableRow(applicationContext)
        //tableRow.setPadding(-1, 2, 0, -2)
        tableRow.background = resources.getDrawable(R.drawable.cell_first_bacground)
        tableRow.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / FirstColumWidthSize, SCREEN_HEIGHT / RowFooterHeight)

        tableRow.addView(tableRowData)
        this.tableLayoutFootersBodyDataFirst.addView(tableRow, tableRowCountFooters)
        tableRowCountFooters++
    }

    @Synchronized
    private fun addColumnToTableAtScrollFooters(
        rowPos: Int,
        text: String,
        txtSize: Float = 16f,
        width: Int,
        TEXT_ALIGNMENT: Int,
        ROW_COLOR: Int,
        TXT_COLOR: Int
    ) {
        val tableRowAdd = this.tableLayoutFootersBodyData.getChildAt(rowPos) as TableRow
        tableRowBFix = TableRow(applicationContext)
        tableRowBFix.background = resources.getDrawable(R.drawable.cell_bacground)
        tableRowBFix.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / (ColumWidthSize - width), SCREEN_HEIGHT / RowFooterHeight)
        val txtView = TextView(applicationContext)
        //txtView.setPadding(+2, 15, +2, -1)
        txtView.text = "" + text
        txtView.setTextColor(TXT_COLOR)
        txtView.textAlignment = TEXT_ALIGNMENT
        txtView.textSize = txtSize
        tableRowBFix.tag = txtView
        tableRowBFix.gravity = Gravity.CENTER
        this.tableRowBFix.addView(txtView)
        tableRowAdd.setBackgroundColor(ROW_COLOR)
        tableRowAdd.addView(tableRowBFix)
    }


}
