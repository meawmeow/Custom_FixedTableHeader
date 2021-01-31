package th.co.businessplus.kotlinui1.custom_table;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import th.co.businessplus.kotlinui1.R;
import th.co.businessplus.kotlinui1.custom_table.lib_qtable.DataHeaderFirstColum;
import th.co.businessplus.kotlinui1.custom_table.lib_qtable.DataTable;
import th.co.businessplus.kotlinui1.custom_table.lib_qtable.HorizontalScroll;
import th.co.businessplus.kotlinui1.custom_table.lib_qtable.QTableV2;
import th.co.businessplus.kotlinui1.pagination_data.lib_pagination.BtnPageProperty;
import th.co.businessplus.kotlinui1.pagination_data.lib_pagination.QPaginationMutableList;

public class LibQTableJavaActivity extends AppCompatActivity implements HorizontalScroll.ScrollViewListener{

    DataHeaderFirstColum dataHeaderFirstColum;
    DataHeaderFirstColum dataFooterFirstColum;
    String[] dataHeaderList;
    Integer[] widthColumList;
    Integer[] textAlignmentHeaderList;
    Integer[] textAlignmentBodyList;

    List<DataTable> dataHeaderTable = new ArrayList<>();
    List<DataTable> dataFootersTable = new ArrayList<>();

    QTableV2 headerQTable;
    QTableV2 footersQTable;

    RelativeLayout relativeLayoutHeader_id;
    RelativeLayout relativeLayoutFooter_id;

    //pagegenation
    LinearLayout linearGroup;
    Button btn_previous;
    Button btn_next;

    QPaginationMutableList qPagination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_q_table_v2);
        getSupportActionBar().setTitle("QTable java");

        relativeLayoutHeader_id = (RelativeLayout) findViewById(R.id.relativeLayoutHeader_id);
        relativeLayoutFooter_id = (RelativeLayout) findViewById(R.id.relativeLayoutFooter_id);
        linearGroup = (LinearLayout) findViewById(R.id.la_view);
        btn_previous = (Button) findViewById(R.id.btn_previous);
        btn_next = (Button) findViewById(R.id.btn_next);

        //init data values
        dataHeaderFirstColum =
                new DataHeaderFirstColum("Index", Color.WHITE, 16f, getResources().getColor(R.color.ColorHeaderColumFirst));
        dataFooterFirstColum =
                new DataHeaderFirstColum("Footer", Color.WHITE, 16f, getResources().getColor(R.color.ColorFootersColumFirst));

        dataHeaderList = new String[]{"Colum1", "Colum2", "Colum3", "Colum4"};
        widthColumList = new Integer[]{0, 2, 0, 2};
        textAlignmentHeaderList = new Integer[]{View.TEXT_ALIGNMENT_CENTER,
                View.TEXT_ALIGNMENT_CENTER,
                View.TEXT_ALIGNMENT_CENTER,
                View.TEXT_ALIGNMENT_CENTER};
        textAlignmentBodyList = new Integer[]{View.TEXT_ALIGNMENT_CENTER,
                View.TEXT_ALIGNMENT_CENTER,
                View.TEXT_ALIGNMENT_TEXT_END,
                View.TEXT_ALIGNMENT_CENTER};


        //init Qtable Header
        headerQTable = new QTableV2(this, relativeLayoutHeader_id);
        headerQTable.initHeaderLayoutMain(50,18,10,5,true);
        headerQTable.initTableHeaderData(
                dataHeaderFirstColum,
                dataHeaderList,
                widthColumList,
                textAlignmentHeaderList,
                16f,
                Color.WHITE,
                getResources().getColor(R.color.ColorHeaderColum));

        headerQTable.horizontalScrollViewBodyData.setScrollViewListener(this);
        headerQTable.horizontalScrollViewHeaderData.setScrollViewListener(this);


        //init Qtable Footer
        footersQTable = new QTableV2(this, relativeLayoutFooter_id);
        footersQTable.initHeaderLayoutMain(50,18,10,5,false);
        footersQTable.initTableHeaderData(
                dataFooterFirstColum,
                dataHeaderList,
                widthColumList,
                textAlignmentHeaderList,
                16f,
                Color.WHITE,
                Color.WHITE);

        footersQTable.horizontalScrollViewBodyData.setScrollViewListener(this);
        footersQTable.horizontalScrollViewHeaderData.setScrollViewListener(this);

        //initData header
        for (int i = 0; i < 100; i++) {
            dataHeaderTable.add(
                    new DataTable(
                            "col1:" + i,
                            "col2:" + i,
                            "col3:" + i,
                            "col4:" + i
                    )
            );
        }


        //initData footers
        dataFootersTable.add(
                new DataTable(
                        "Footers1:",
                        "Footers2:",
                        "Footers3:",
                        "Footers4:"
                )
        );

        //add data to table
        //AddDataTableHeader();
        AddDataTableFooters();

        BtnPageProperty btnPageProperty = new BtnPageProperty(R.drawable.bg_btn1_select, R.drawable.bg_btn1_click, Color.WHITE, 16f, true);
        qPagination = new QPaginationMutableList(this, linearGroup, btn_previous, btn_next, btnPageProperty);
        qPagination.setPageDataList(dataHeaderTable);
        qPagination.initPagination(15, 5);
        qPagination.setPageFetchData(new QPaginationMutableList.FetchDataListListener() {
            @Override
            public void onFetchDataList(@NotNull List dataList) {
                dataHeaderTable = (List<DataTable>) dataList;
                AddDataTableHeader();

            }
        });
        qPagination.setInitPageData(1);
        qPagination.ShowDataPage();


    }

    public void AddDataTableHeader() {
        headerQTable.StartDataTable();
        for (int i = 0; i < dataHeaderTable.size(); i++) {


            if (i % 2 == 0) {
                headerQTable.AddDataFirstTable(i, "" + dataHeaderTable.get(i).data[0], 16f, Color.WHITE, Color.BLACK);
            } else {
                headerQTable.AddDataFirstTable(
                        i,
                        dataHeaderTable.get(i).data[0],
                        16f,
                        getResources().getColor(R.color.ColorBodyColum),
                        Color.BLACK
                );
            }

            for (int c = 1; c < dataHeaderTable.get(i).data.length; c++) {
                if (i % 2 == 0) {
                    headerQTable.AddDataBodyTable(
                            i,
                            dataHeaderTable.get(i).data[c],
                            16f,
                            widthColumList[c],
                            textAlignmentBodyList[c],
                            Color.WHITE,
                            Color.BLACK
                    );
                } else {
                    headerQTable.AddDataBodyTable(
                            i,
                            dataHeaderTable.get(i).data[c],
                            16f,
                            widthColumList[c],
                            textAlignmentBodyList[c],
                            getResources().getColor(R.color.ColorBodyColum),
                            Color.BLACK
                    );
                }

            }
        }
    }

    public void AddDataTableFooters() {
        footersQTable.StartDataTable();

        for (int i = 0; i < dataFootersTable.size(); i++) {

            footersQTable.AddDataFirstTable(
                    i,
                    dataFooterFirstColum.getColFirst(),
                    dataFooterFirstColum.getTxtSize(),
                    dataFooterFirstColum.getBgColor(),
                    dataFooterFirstColum.getTxtColor()
            );


            for (int c = 1; c < dataFootersTable.get(i).data.length; c++) {
                if (i % 2 != 0) {
                    footersQTable.AddDataBodyTable(
                            i,
                            dataFootersTable.get(i).data[c],
                            16f,
                            widthColumList[c],
                            textAlignmentHeaderList[c],
                            Color.WHITE,
                            Color.BLACK
                    );
                } else {
                    footersQTable.AddDataBodyTable(
                            i,
                            dataFootersTable.get(i).data[c],
                            16f,
                            widthColumList[c],
                            textAlignmentHeaderList[c],
                            getResources().getColor(R.color.ColorFootersColum),
                            Color.WHITE
                    );
                }

            }
        }
    }

    @Override
    public void onScrollChanged(@NotNull HorizontalScroll scrollView, int x, int y, int oldx, int oldy) {
        //header
        if (scrollView == headerQTable.horizontalScrollViewHeaderData) {
            headerQTable.horizontalScrollViewBodyData.scrollTo(x, y);
            footersQTable.horizontalScrollViewBodyData.scrollTo(x, y);
        } else if (scrollView == headerQTable.horizontalScrollViewBodyData) {
            headerQTable.horizontalScrollViewHeaderData.scrollTo(x, y);
            footersQTable.horizontalScrollViewBodyData.scrollTo(x, y);
        }
        //footers
        if (scrollView == footersQTable.horizontalScrollViewHeaderData) {
            headerQTable.horizontalScrollViewBodyData.scrollTo(x, y);
            headerQTable.horizontalScrollViewHeaderData.scrollTo(x, y);
        } else if (scrollView == footersQTable.horizontalScrollViewBodyData) {
            headerQTable.horizontalScrollViewBodyData.scrollTo(x, y);
            headerQTable.horizontalScrollViewHeaderData.scrollTo(x, y);
        }
    }
}
