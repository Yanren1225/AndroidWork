package ren.imyan.topline.ui.home

import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ren.imyan.activity.ActivityCollector.currActivity
import ren.imyan.fragment.BaseFragment
import ren.imyan.topline.R
import ren.imyan.topline.data.moshi.News
import ren.imyan.topline.databinding.AdBannerBinding
import ren.imyan.topline.databinding.ItemAdBinding
import ren.imyan.topline.ui.MainViewModel

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-29 15:02
 * @website https://imyan.ren
 */
class ADBannerFragment(private val news: News) :
    BaseFragment<ItemAdBinding, MainViewModel>(R.layout.item_ad) {

    override fun initViewModel(): MainViewModel =
        ViewModelProvider(requireActivity())[MainViewModel::class.java]

    override fun ItemAdBinding.initView() {

    }

    override fun MainViewModel.loadData() {
        Glide.with(currActivity).load(news.image1).into(binding.image)
    }
}