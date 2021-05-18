package ren.imyan.topline.ui.home

import android.annotation.SuppressLint
import android.view.View
import androidx.core.graphics.red
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import ren.imyan.activity.ActivityCollector.currActivity
import ren.imyan.fragment.BaseFragment
import ren.imyan.topline.R
import ren.imyan.topline.databinding.FragmentHomeBinding
import ren.imyan.topline.databinding.HomeHeadViewBinding
import ren.imyan.topline.ui.MainViewModel
import ren.imyan.topline.util.screenWidth

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-27 17:45
 * @website https://imyan.ren
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>(R.layout.fragment_home) {
    override fun initViewModel(): MainViewModel =
        ViewModelProvider(requireActivity())[MainViewModel::class.java]

    private val homeAdapter by lazy {
        HomeAdapter()
    }

    private val adBannerAdapter by lazy {
        ADBannerAdapter(this)
    }

    private val homeHeadViewBinding by lazy {
        DataBindingUtil.inflate<HomeHeadViewBinding>(
            layoutInflater,
            R.layout.home_head_view,
            null,
            false
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun FragmentHomeBinding.initView() {

        list.apply {
            layoutManager = LinearLayoutManager(context)
            addHeaderView(homeHeadViewBinding.root)
            adapter = homeAdapter
        }

        homeHeadViewBinding.adBanner.viewpager.apply {
            isLongClickable = false
            adapter = adBannerAdapter
            setOnTouchListener { _, _ ->
                false
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (adBannerAdapter.itemCount > 0) {
                        val title =
                            adBannerAdapter.getTitle(position.rem(adBannerAdapter.itemCount))
                        homeHeadViewBinding.adBanner.title.text = title
                        homeHeadViewBinding.adBanner.indicator.setCurrentPosition(position)
                    }
                }
            })
        }

        refresh.autoRefresh()
    }

    override fun MainViewModel.loadData() {
        binding.refresh.apply {
            setOnRefreshListener {
                this.setEnableLoadMore(false)
                getNewsData()
                getADData()
            }
        }
        newsListData.data.observe(this@HomeFragment) {
            homeAdapter.setNewData(it)
            binding.refresh.finishRefresh()
        }
        adListData.data.observe(this@HomeFragment) {
            adBannerAdapter.setData(it)
            homeHeadViewBinding.adBanner.indicator.count = it.size
        }
    }

    fun View.resetSize() {
        val sw = currActivity.screenWidth
        val adLHeight = sw / 2
        val adlp = layoutParams
        adlp.width = sw
        adlp.height = adLHeight
        layoutParams = adlp
    }
}