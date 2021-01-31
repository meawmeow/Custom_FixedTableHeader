package th.co.businessplus.kotlinui1.custom_table.lib_qtable

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import th.co.businessplus.kotlinui1.R

class QTableV2 : HorizontalScroll.ScrollViewListener,
    VerticalScroll.ScrollViewListener {

    var interfaceHorizontalScroll: HorizontalScrollListener? = null

    interface HorizontalScrollListener {
        fun onHorizontalScroll(x: Int, y: Int)

    }

    fun setOnHorizontalScroll(interfaceHorizontalScroll: HorizontalScrollListener) {
        this.interfaceHorizontalScroll = interfaceHorizontalScroll
    }


    override fun onScrollChanged(scrollView: HorizontalScroll, x: Int, y: Int, oldx: Int, oldy: Int) {
        if (scrollView === horizontalScrollViewHeaderData) {
            horizontalScrollViewBodyData.scrollTo(x, y)
            //interfaceHorizontalScroll!!.onHorizontalScroll(x,y)
        } else if (scrollView === horizontalScrollViewBodyData) {
            horizontalScrollViewHeaderData.scrollTo(x, y)
            //interfaceHorizontalScroll!!.onHorizontalScroll(x,y)
        }

    }

    override fun onScrollChanged(scrollView: VerticalScroll, x: Int, y: Int, oldx: Int, oldy: Int) {
        if (scrollView === verticalScrollViewBodyDataFirst) {
            verticalScrollViewBodyData.scrollTo(x, y)
        } else if (scrollView === verticalScrollViewBodyData) {
            verticalScrollViewBodyDataFirst.scrollTo(x, y)
        }
    }

    var context: Context;
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
    var RowHeaderHeight =0;
    var RowHeight = 18

    var FirstColumWidth = 100
    var ColumWidthSize = 5


    var relativeLayoutHeader: RelativeLayout


    constructor(context: Context, relativeLayoutHeader: RelativeLayout) {
        this.context = context;
        this.relativeLayoutHeader = relativeLayoutHeader
        getScreenDimension()
    }

     fun initHeaderLayoutMain(_RowHeaderHeight :Int,_RowHeight :Int,_FirstColumWidth :Int,_ColumWidthSize : Int, visibleHeader: Boolean = true) {
         this.RowHeaderHeight = _RowHeaderHeight;
         this.RowHeight = _RowHeight;
         this.FirstColumWidth = _FirstColumWidth;
         this.ColumWidthSize = _ColumWidthSize;
        initializeRelativeLayoutHeaderMain(visibleHeader)
        initializeScrollersLayoutHeaderMain()
        initializeTableLayoutHeaderMain()
    }

     fun initTableHeaderData(
        dataFirst: DataHeaderFirstColum,
        dataColumHeaderList: Array<String>,
        widthsColumList: Array<Int>,
        textAlignmentList: Array<Int>,
        txtSize: Float,
        txtColor: Int,
        bgColor: Int
    ) {
        addRowToTableHeaderColumFirstFix(dataFirst.colFirst, dataFirst.txtColor, dataFirst.txtSize, dataFirst.bgColor)
        initializeRowForTableHeaderColumScroll()
        //var dataListHeader = arrayOf("Colum1", "Colum2", "Colum3", "Colum4", "Colum5", "Colum6", "Colum7", "Colum8")
        for (i in 1 until dataColumHeaderList.size) {
            addColumnsToTableHeaderColumScroll(
                i,
                dataColumHeaderList[i],
                widthsColumList[i],
                textAlignmentList[i],
                txtSize, txtColor, bgColor
            )
        }
    }

    private fun getScreenDimension() {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        SCREEN_WIDTH = size.x
        SCREEN_HEIGHT = size.y
    }

//    var tableRowCountHeader = 0
//
//
//    var FirstColumWidthSize = 5
//    var ColumWidthSize = 5

    private fun initializeRelativeLayoutHeaderMain(visibleHeader: Boolean = true) {
        //init layout Header data
        relativeLayoutHeaderColumFirstFix = RelativeLayout(context)
        relativeLayoutHeaderColumFirstFix.id = R.id.relativeIdHeaderColumFirstFix
        relativeLayoutHeaderColumFirstFix.setPadding(0, 0, 0, 0)
        if (!visibleHeader)
            relativeLayoutHeaderColumFirstFix.visibility = View.GONE

        relativeLayoutHeaderColumScroll = RelativeLayout(context)
        relativeLayoutHeaderColumScroll.id = R.id.relativeIdHeaderColumScroll
        relativeLayoutHeaderColumScroll.setPadding(0, 0, 0, 0)
        if (!visibleHeader)
            relativeLayoutHeaderColumScroll.visibility = View.GONE

        ///init layout Body data
        relativeLayoutBodyDataFirst = RelativeLayout(context)
        relativeLayoutBodyDataFirst.id = R.id.relativeIdBodyDataFirst
        relativeLayoutBodyDataFirst.setPadding(0, 0, 0, 0)

        relativeLayoutBodyDataScroll = RelativeLayout(context)
        relativeLayoutBodyDataScroll.id = R.id.relativeIdBodyDataScroll
        relativeLayoutBodyDataScroll.setPadding(0, 0, 0, 0)


        //Main layout Header Data
//        relativeLayoutHeaderColumFirstFix.layoutParams =
//            RelativeLayout.LayoutParams((SCREEN_WIDTH / FirstColumWidthSize), SCREEN_HEIGHT / RowHeaderHeight)
        relativeLayoutHeaderColumFirstFix.layoutParams =
            RelativeLayout.LayoutParams((SCREEN_WIDTH / ColumWidthSize)+FirstColumWidth, (SCREEN_HEIGHT / RowHeight)+RowHeaderHeight)
        //relativeLayoutHeaderColumFirstFix.setBackgroundColor(Color.GREEN)
        this.relativeLayoutHeader.addView(relativeLayoutHeaderColumFirstFix)


//        val layoutParamsRelativeHeaderColumScroll =
//            RelativeLayout.LayoutParams(
//                SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 2),
//                SCREEN_HEIGHT / RowHeaderHeight
//            )
        val layoutParamsRelativeHeaderColumScroll =
            RelativeLayout.LayoutParams(
                SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 2)-FirstColumWidth,
                (SCREEN_HEIGHT / RowHeight)+RowHeaderHeight
            )
        layoutParamsRelativeHeaderColumScroll.addRule(RelativeLayout.RIGHT_OF, R.id.relativeIdHeaderColumFirstFix)
        relativeLayoutHeaderColumScroll.layoutParams = layoutParamsRelativeHeaderColumScroll
        //relativeLayoutHeaderColumScroll.setBackgroundColor(Color.RED)
        this.relativeLayoutHeader.addView(relativeLayoutHeaderColumScroll)


        //Main layout Body Data
//        val layoutParamsRelativeBodyDataFirst =
//            RelativeLayout.LayoutParams(
//                (SCREEN_WIDTH / FirstColumWidthSize),
//                SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeaderHeight
//            )
        val layoutParamsRelativeBodyDataFirst =
            RelativeLayout.LayoutParams(
                (SCREEN_WIDTH / ColumWidthSize)+FirstColumWidth,
                SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeight
            )
        layoutParamsRelativeBodyDataFirst.addRule(RelativeLayout.BELOW, R.id.relativeIdHeaderColumFirstFix)
        relativeLayoutBodyDataFirst.layoutParams = layoutParamsRelativeBodyDataFirst
        //relativeLayoutBodyDataFirst.setBackgroundColor(Color.MAGENTA)
        this.relativeLayoutHeader.addView(relativeLayoutBodyDataFirst)


//        val layoutParamsRelativeBodyDataScroll = RelativeLayout.LayoutParams(
//            SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 3),
//            SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeaderHeight
//        )
        val layoutParamsRelativeBodyDataScroll = RelativeLayout.LayoutParams(
            SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 3),
            SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeight
        )
        layoutParamsRelativeBodyDataScroll.addRule(RelativeLayout.BELOW, R.id.relativeIdHeaderColumScroll)
        layoutParamsRelativeBodyDataScroll.addRule(RelativeLayout.RIGHT_OF, R.id.relativeIdBodyDataFirst)
        relativeLayoutBodyDataScroll.layoutParams = layoutParamsRelativeBodyDataScroll
        //relativeLayoutBodyDataScroll.setBackgroundColor(Color.BLUE)
        this.relativeLayoutHeader.addView(relativeLayoutBodyDataScroll)

    }

    private fun initializeScrollersLayoutHeaderMain() {
        horizontalScrollViewHeaderData = HorizontalScroll(context)
        horizontalScrollViewHeaderData.setPadding(0, 0, 0, 0)

        verticalScrollViewBodyDataFirst = VerticalScroll(context)
        verticalScrollViewBodyDataFirst.setPadding(0, 0, 0, 0)

        horizontalScrollViewBodyData = HorizontalScroll(context)
        horizontalScrollViewBodyData.setPadding(0, 0, 0, 0)

        verticalScrollViewBodyData = VerticalScroll(context)
        verticalScrollViewBodyData.setPadding(0, 1, 0, 0)

        horizontalScrollViewHeaderData.layoutParams =
            ViewGroup.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH / ColumWidthSize)+FirstColumWidth, (SCREEN_HEIGHT / RowHeight)+RowHeaderHeight)

        verticalScrollViewBodyDataFirst.layoutParams =
            ViewGroup.LayoutParams((SCREEN_WIDTH / ColumWidthSize)+FirstColumWidth, SCREEN_HEIGHT - (SCREEN_HEIGHT / RowHeight))

        verticalScrollViewBodyData.layoutParams =
            ViewGroup.LayoutParams(
                SCREEN_WIDTH - SCREEN_WIDTH / (ColumWidthSize + 2),
                SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeight
            )

        horizontalScrollViewBodyData.layoutParams =
            ViewGroup.LayoutParams(
                SCREEN_WIDTH - (SCREEN_WIDTH / ColumWidthSize)-FirstColumWidth,
                SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeight
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
        tableLayoutHeaderColumFirstFix = TableLayout(context)
        tableLayoutHeaderColumFirstFix.setPadding(0, 0, 0, 0)
        tableLayoutHeaderColumScroll = TableLayout(context)
        tableLayoutHeaderColumScroll.setPadding(0, 0, 0, 0)
        //tableLayoutHeaderColumScroll.id = R.id.tableLayoutB

        tableLayoutBodyDataColumFirst = TableLayout(context)
        tableLayoutBodyDataColumFirst.setPadding(0, 1, 0, 0)
        tableLayoutBodyDataColumScroll = TableLayout(context)
        tableLayoutBodyDataColumScroll.setPadding(0, 0, 0, 0)


        //table header Colum First Fix
        val layoutParamsTableLayoutHeaderColumFirstFix =
            TableLayout.LayoutParams((SCREEN_WIDTH / ColumWidthSize)+FirstColumWidth, (SCREEN_HEIGHT / RowHeight)+RowHeaderHeight)
        tableLayoutHeaderColumFirstFix.layoutParams = layoutParamsTableLayoutHeaderColumFirstFix
        //tableLayoutHeaderColumFirstFix.setBackgroundColor(Color.RED)
        this.relativeLayoutHeaderColumFirstFix.addView(tableLayoutHeaderColumFirstFix)

        //table header Colum Scroll to ScrollView
        val layoutParamsTableLayoutHeaderColumScroll =
            TableLayout.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH / ColumWidthSize)+FirstColumWidth, (SCREEN_HEIGHT / RowHeight)+RowHeaderHeight)
        tableLayoutHeaderColumScroll.layoutParams = layoutParamsTableLayoutHeaderColumScroll
        //tableLayoutHeaderColumScroll.setBackgroundColor(Color.MAGENTA)
        this.horizontalScrollViewHeaderData.addView(tableLayoutHeaderColumScroll)


        //Body Data Colum First to ScrollView
        val layoutParamsTableLayoutBodyDataColumFirst =
            TableLayout.LayoutParams((SCREEN_WIDTH / 2)+FirstColumWidth, SCREEN_HEIGHT - SCREEN_HEIGHT / RowHeight)
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
        tableRowAFix = TableRow(context)
        tableRowAFix.gravity = Gravity.CENTER_HORIZONTAL
        tableRowAFix.layoutParams =
            TableRow.LayoutParams((SCREEN_WIDTH / ColumWidthSize)+FirstColumWidth, (SCREEN_HEIGHT / RowHeight)+RowHeaderHeight)

        val txtView = TextView(context)
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
        tableRowAScroll = TableRow(context)
        //tableRowAFix.setPadding(0, 0, 0, 0)
        //this.tableLayoutHeaderColumScroll.setBackgroundColor(backGroudColor)
        this.tableLayoutHeaderColumScroll.addView(tableRowAScroll)
    }

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
        tableRowAFix = TableRow(context)
        //tableRowAFix.background = resources.getDrawable(R.drawable.cell_bacground)
        tableRowAFix.gravity = Gravity.CENTER
        tableRowAFix.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH  / (ColumWidthSize - width), (SCREEN_HEIGHT / RowHeight)+RowHeaderHeight)

        val txtView = TextView(context)
        txtView.text = text
        txtView.textAlignment = TEXT_ALIGNMENT
        txtView.textSize = txtSize
        txtView.setTextColor(txtColor)
        this.tableRowAFix.addView(txtView)
        this.tableRowAFix.tag = id

        this.tableRowAScroll.addView(tableRowAFix)
        tableColumnHeaderCountB++
    }

    public fun StartDataTable() {
        tableLayoutBodyDataColumScroll.removeAllViews()
        tableLayoutBodyDataColumFirst.removeAllViews()
        tableRowCountHeader = 0
    }

    public fun AddDataFirstTable(row: Int, columData: String, txtSize: Float, bgColor: Int, txtColor: Int) {
        initializeRowForTableData(row)
        addRowToTableAtFirst(row, columData, txtSize, bgColor, txtColor)
    }

    public fun AddDataBodyTable(
        row: Int, columData: String, txtSize: Float, widthsColum: Int,
        textAlignment: Int, bgColor: Int, txtColor: Int
    ) {
        addColumnToTableAtScroll(
            row,
            columData,
            txtSize,
            widthsColum,
            textAlignment,
            bgColor,
            txtColor
        )
    }


    @Synchronized
    private fun initializeRowForTableData(pos: Int) {
        val tableRowB = TableRow(context)
        //tableRowB.setPadding(-1, 1, 0, -1)
        tableRowB.layoutParams =
            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, SCREEN_HEIGHT / RowHeight)
        this.tableLayoutBodyDataColumScroll.addView(tableRowB, pos)
    }

    @Synchronized
    private fun addRowToTableAtFirst(
        i: Int,
        text: String,
        txtSize: Float = 16f,
        ROW_COLOR: Int,
        TXT_COLOR: Int
    ) {//First Row
        val tableRowData = TableRow(context)
        tableRowData.layoutParams =
            TableRow.LayoutParams((SCREEN_WIDTH / ColumWidthSize)+FirstColumWidth, SCREEN_HEIGHT / RowHeight)

        val txtView = TextView(context)
        txtView.text = text
        txtView.textSize = txtSize
        txtView.setTextColor(TXT_COLOR)
        txtView.gravity = Gravity.CENTER or Gravity.BOTTOM
        tableRowData.addView(txtView)
        tableRowData.setBackgroundColor(ROW_COLOR)
        tableRowData.gravity = Gravity.CENTER

        val tableRow = TableRow(context)
        tableRow.setBackgroundColor(ROW_COLOR)
        //tableRowData.setPadding(-1,1,0,-1)
        tableRowData.background = context.resources.getDrawable(R.drawable.cell_first_bacground)
        tableRow.layoutParams =
            TableRow.LayoutParams((SCREEN_WIDTH / ColumWidthSize)+FirstColumWidth, SCREEN_HEIGHT / RowHeight)

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
        tableRowAFix = TableRow(context)
        tableRowAFix.background = context.resources.getDrawable(R.drawable.cell_bacground)
        tableRowAFix.layoutParams =
            TableRow.LayoutParams(SCREEN_WIDTH / (ColumWidthSize - width), (SCREEN_HEIGHT / RowHeight))

        val txtView = TextView(context)
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


}