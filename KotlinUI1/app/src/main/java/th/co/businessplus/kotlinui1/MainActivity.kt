package th.co.businessplus.kotlinui1

import android.content.Intent
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import th.co.businessplus.kotlinui1.custom_table.LibQTableFixSizeJavaActivity
import th.co.businessplus.kotlinui1.custom_table.LibQTableActivity
import th.co.businessplus.kotlinui1.custom_table.LibQTableJavaActivity
import th.co.businessplus.kotlinui1.custom_table_report.TableReportActivity
import th.co.businessplus.kotlinui1.pagination_data.PaginationQActivity
import th.co.businessplus.kotlinui1.pagination_data.PaginationActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        Log.d("DSSV", "Width $width")
        Log.d("DSSV", "height $height")


        // pixels, dpi
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val heightPixels = metrics.heightPixels
        val widthPixels = metrics.widthPixels
        val densityDpi = metrics.densityDpi
        val xdpi = metrics.xdpi
        val ydpi = metrics.ydpi
        Log.d("DSSV", "widthPixels  = $widthPixels")
        Log.d("DSSV", "heightPixels = $heightPixels")
        Log.d("DSSV", "densityDpi   = $densityDpi")
        Log.d("DSSV", "xdpi         = $xdpi")
        Log.d("DSSV", "ydpi         = $ydpi")

        txt_wh.text = "w:$widthPixels x h:$heightPixels"
        // deprecated
        val screenHeight = display.height
        val screenWidth = display.width
        Log.d("DSSV", "screenHeight = $screenHeight")
        Log.d("DSSV", "screenWidth  = $screenWidth")

        // orientation (either ORIENTATION_LANDSCAPE, ORIENTATION_PORTRAIT)
        val orientation = resources.configuration.orientation
        Log.d("DSSV", "orientation  = $orientation")

        val density = resources.displayMetrics.densityDpi
        Log.d("DSSV", "density  = $density")
        txt_density.text = "Density  = $density";

        val config = resources.configuration
        Log.d("DSSV", "smallestScreenWidthDp = " + config.smallestScreenWidthDp)
        txt_smalles.text = "Smallest" + config.smallestScreenWidthDp;

        txt_reso.text = resources.getString(R.string.name1)


        btn_menu5.setOnClickListener {
            val intent = Intent(this, TableReportActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
        btn_menu6.setOnClickListener {
            val intent = Intent(this, LibQTableActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
        btn_menu6_1.setOnClickListener{
            val inten = Intent(this, LibQTableFixSizeJavaActivity::class.java);
            startActivity(inten);
        }
        btn_menu6_2.setOnClickListener{
            val inten = Intent(this, LibQTableJavaActivity::class.java);
            startActivity(inten);
        }

        btn_menu7.setOnClickListener {
            val intent = Intent(this, PaginationActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
        btn_menu8.setOnClickListener {
            val intent = Intent(this, PaginationQActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
