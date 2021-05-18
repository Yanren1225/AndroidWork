package ren.imyan.topline.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager2.adapter.FragmentStateAdapter
import ren.imyan.topline.data.moshi.News

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-29 15:03
 * @website https://imyan.ren
 */
class ADBannerAdapter(fragment: Fragment, var data: List<News> = ArrayList()) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = if (data.isNotEmpty()) Int.MAX_VALUE else data.size

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        if (data.isNotEmpty())
            fragment = ADBannerFragment(data[position])
        return fragment
    }

    fun getTitle(index: Int) = data[index].newsName

    @JvmName("setData1")
    fun setData(newData: List<News>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return POSITION_NONE.toLong()
    }
}