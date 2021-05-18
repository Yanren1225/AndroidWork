package ren.imyan.topline.customview.wraprecyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT
import kotlin.math.abs

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-28 17:25
 * @website https://imyan.ren
 */
class WrapAdapter2<T : Adapter<*>?>(adapter: T?) :
    Adapter<ViewHolder>() {

    constructor() : this(null)

    private val mRealAdapter: T = adapter!!
    private var isStaggeredGrid = false
    private val mHeaderViewInfos = ArrayList<FixedViewInfo>()

    val wrapAdapter
        get() = mRealAdapter

    companion object {
        const val BASE_HEADER_VIEW_TYPE = -1 shl 10
    }

    // Position 调用
    private val Int.isHeaderPosition
        get() = this < mHeaderViewInfos.size

    private val Int.viewType
        get() = if (this.isHeaderPosition) mHeaderViewInfos[this].viewType else mRealAdapter!!.getItemViewType(
            this - mHeaderViewInfos.size
        )

    // ViewType 调用
    private val Int.isHeader
        get() = this >= BASE_HEADER_VIEW_TYPE && this < (BASE_HEADER_VIEW_TYPE + mHeaderViewInfos.size)

    fun addHeaderView(view: View) {
        val info = FixedViewInfo(view, BASE_HEADER_VIEW_TYPE + mHeaderViewInfos.size)
        mHeaderViewInfos.add(info)
        notifyDataSetChanged()
    }

    fun adjustSpanSize(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val isHeaderOrFooter = position.isHeaderPosition
                    return if (isHeaderOrFooter) layoutManager.spanCount else 1
                }
            }
        }
        if (layoutManager is StaggeredGridLayoutManager) {
            this.isStaggeredGrid = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType.isHeader) {
            val whichHeader = abs(viewType - BASE_HEADER_VIEW_TYPE)
            val headView = mHeaderViewInfos[whichHeader].view
            createHeaderFooterViewHolder(headView)
        } else {
            mRealAdapter!!.onCreateViewHolder(parent, viewType)
        }
    }

    private fun createHeaderFooterViewHolder(view: View): ViewHolder {
        if (isStaggeredGrid) {
            val params = StaggeredGridLayoutManager.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                .apply { isFullSpan = true }
            view.layoutParams = params
        }
        return object : ViewHolder(view) {}
    }

    @Suppress("unchecked", "UNREACHABLE_CODE", "CAST_NEVER_SUCCEEDS")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < mHeaderViewInfos.size + mRealAdapter!!.itemCount) {
            mRealAdapter.onBindViewHolder(holder as Nothing, position - mHeaderViewInfos.size)
        }
    }

    override fun getItemCount(): Int = mHeaderViewInfos.size + mRealAdapter!!.itemCount
}