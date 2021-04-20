package ren.imyan.a3dalbum

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import ren.imyan.a3dalbum.databinding.ActivityFullViewBinding

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-20 21:23
 * @website https://imyan.ren
 */
class FullViewActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFullViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        findViewById<View>(android.R.id.content).transitionName = "IMAGE"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 300L
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 250L
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initImageView()
    }

    private fun initImageView() {
        val album = intent.getParcelableExtra<Album>("Album")
        val imageID = album?.imageID
        imageID?.let { binding.fullImageView.setImageResource(it) }
    }
}