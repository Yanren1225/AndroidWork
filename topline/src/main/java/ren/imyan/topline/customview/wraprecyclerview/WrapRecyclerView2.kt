package ren.imyan.topline.customview.wraprecyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-28 18:43
 * @website https://imyan.ren
 */
class WrapRecyclerView2 : RecyclerView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private var mWrapAdapter: WrapAdapter2<*>? = null

    private val mTmpHeaderView = ArrayList<View>()

    private var shouldAdjustSpanSize = false

    var adapter: WrapAdapter2<*>?
        get() = mWrapAdapter
        set(value) {
            mWrapAdapter = value
        }

    val wrappedAdapter: Adapter<*>?
        get() = mWrapAdapter?.wrapAdapter

    override fun setAdapter(adapter: Adapter<*>?) {
        if (adapter is WrapAdapter2<*>) {
            mWrapAdapter = adapter
            super.setAdapter(adapter)
        } else {
            mTmpHeaderView.forEach {
                mWrapAdapter?.addHeaderView(it)
            }

            if (mTmpHeaderView.size > 0) {
                mTmpHeaderView.clear()
            }

            super.setAdapter(mWrapAdapter)
        }
    }

    fun addHeaderView(view: View) {
        mWrapAdapter?.addHeaderView(view)
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        if (layout is GridLayoutManager || layout is StaggeredGridLayoutManager) {
            this.shouldAdjustSpanSize = true
        }
    }

    val mDaraObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            mWrapAdapter?.notifyDataSetChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mWrapAdapter?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mWrapAdapter?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mWrapAdapter?.notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mWrapAdapter?.notifyItemMoved(fromPosition, toPosition)
        }
    }
}