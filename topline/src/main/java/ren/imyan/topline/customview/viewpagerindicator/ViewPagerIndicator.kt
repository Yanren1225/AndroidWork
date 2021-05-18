package ren.imyan.topline.customview.viewpagerindicator

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import ren.imyan.topline.R

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-28 15:01
 * @website https://imyan.ren
 */
class ViewPagerIndicator(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    private var mContext: Context = context
    private var mCount = 0
    private var mIndex = 0

    var count: Int = 0
        set(value) {
            field = value
            mCount = value
        }

    constructor(context: Context) : this(context, null) {
        this.mContext = context
    }

    fun setCurrentPosition(index: Int) {
        mIndex = index
        this.removeAllViews()
        val pex = mContext.resources.getDimension(R.dimen.view_indicator_padding).toInt()

        for (i in 0..mCount) {
            val imageRes = if (mIndex == i) {
                R.drawable.indicator_on
            } else {
                R.drawable.indicator_off
            }

            val imageView = ImageView(mContext).apply {
                setImageResource(imageRes)
                setPadding(pex, 0, pex, 0)
            }

            this.addView(imageView)
        }
    }
}