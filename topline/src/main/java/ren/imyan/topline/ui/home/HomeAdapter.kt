package ren.imyan.topline.ui.home

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import ren.imyan.activity.ActivityCollector.currActivity
import ren.imyan.recyclerviewadapter.BaseRecyclerViewAdapter
import ren.imyan.topline.R
import ren.imyan.topline.data.moshi.News
import ren.imyan.topline.databinding.ItemHomeBinding

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-29 15:28
 * @website https://imyan.ren
 */
class HomeAdapter(data: List<News> = ArrayList()) : BaseRecyclerViewAdapter<News, ItemHomeBinding>(
    data.toMutableList(),
    R.layout.item_home
) {

    companion object {
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
    }

    override fun bindItem(itemBinding: ItemHomeBinding, itemData: News) {
        if (itemData.type == TYPE_ONE) {
            itemBinding.itemHomeTwo.root.isVisible = false
            itemBinding.itemHomeOne.apply {
                name.text = itemData.newsName
                newTypeName.text = itemData.newsTypeName
                Glide.with(currActivity).load(itemData.image1).into(image)
            }
        } else {
            itemBinding.itemHomeOne.root.isVisible = false
            itemBinding.itemHomeTwo.apply {
                name.text = itemData.newsName
                newsTypeName.text = itemData.newsTypeName
                Glide.with(currActivity).load(itemData.image1).into(image1)
                Glide.with(currActivity).load(itemData.image2).into(image2)
                Glide.with(currActivity).load(itemData.image3).into(image3)
            }
        }
    }
}