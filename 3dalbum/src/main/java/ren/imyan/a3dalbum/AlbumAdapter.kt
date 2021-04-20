package ren.imyan.a3dalbum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import ren.imyan.a3dalbum.databinding.ItemAlbumBinding

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-20 16:44
 * @website https://imyan.ren
 */
class AlbumAdapter(private val data: List<Album>) : BaseAdapter() {

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemAlbumBinding
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_album, parent, false)
            binding = ItemAlbumBinding.bind(view)
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemAlbumBinding
        }

        binding.image.setImageResource(data[position].imageID)
        return view
    }
}