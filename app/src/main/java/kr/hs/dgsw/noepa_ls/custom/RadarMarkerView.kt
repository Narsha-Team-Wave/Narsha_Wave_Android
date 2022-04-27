package kr.hs.dgsw.noepa_ls.custom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kr.hs.dgsw.noepa_ls.R
import java.text.DecimalFormat


/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
class RadarMarkerView: MarkerView {
    private var tvContent: TextView? = null
    private val format = DecimalFormat("##0")
    constructor(context: Context?, layoutResource: Int) : super(context, layoutResource){
        this.tvContent = findViewById(R.id.tvContent)

    }
    override fun refreshContent(e: Entry, highlight: Highlight) {
        this.tvContent?.text = String.format("%s %%", format.format(e.y.toDouble()))
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height - 10).toFloat())
    }

    //    private val tvContent: TextView
//    private val format = DecimalFormat("##0")
//
//    // runs every time the MarkerView is redrawn, can be used to update the
//    // content (user-interface)
//
//
//    init {
//        tvContent = findViewById(R.id.tvContent)
//        //tvContent.typeface = Typeface.createFromAsset(context.assets, "OpenSans-Light.ttf")
//    }
}
