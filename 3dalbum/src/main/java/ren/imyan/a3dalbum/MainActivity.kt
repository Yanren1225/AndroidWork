package ren.imyan.a3dalbum

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import ren.imyan.a3dalbum.databinding.ActivityMainBinding
import ren.imyan.a3dalbum.databinding.ItemTitleBinding
import ren.imyan.a3dalbum_library.ui.FeatureCoverFlow

class MainActivity : AppCompatActivity() {

    private lateinit var backTransform: MaterialContainerTransform
    private var isFull = false

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val inAnim by lazy {
        AnimationUtils.loadAnimation(this, R.anim.slide_in_top)
    }

    private val outAnim by lazy {
        AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom)
    }

    private val data by lazy {
        listOf(
            Album(R.drawable.img_1, R.string.image_1),
            Album(R.drawable.img_2, R.string.image_2),
            Album(R.drawable.img_3, R.string.image_3),
            Album(R.drawable.img_4, R.string.image_4),
            Album(R.drawable.img_5, R.string.image_5)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        hideBar()
        initView()
    }

    private fun initView() {
        binding.textSwitcher.setFactory {
            val itemTitleBinding = ItemTitleBinding.inflate(layoutInflater)
            itemTitleBinding.root
        }
        binding.textSwitcher.apply {
            inAnimation = inAnim
            outAnimation = outAnim
        }
        binding.imageCoverFlow.apply {
            adapter = AlbumAdapter(data)
            setOnItemClickListener { _, view, position, _ ->
                if (position < data.size) {
                    isFull = true
                    binding.fullImageView.setImageResource(data[position].imageID)
                    val transform = MaterialContainerTransform().apply {
                        duration = 500
                        startView = view
                        endView = binding.fullImageView
                        setPathMotion(MaterialArcMotion())
                        addTarget(endView as ImageView)
                        scrimColor = Color.TRANSPARENT
                    }

                    backTransform = MaterialContainerTransform().apply {
                        duration = 500
                        startView = binding.fullImageView
                        endView = view
                        setPathMotion(MaterialArcMotion())
                        addTarget(endView!!)
                        scrimColor = Color.TRANSPARENT
                    }

                    TransitionManager.beginDelayedTransition(
                        binding.imageCoverFlow,
                        transform
                    )

                    binding.imageCoverFlow.isVisible = false
                    binding.fullImageView.isVisible = true
                }
            }

            setOnScrollPositionListener(object : FeatureCoverFlow.OnScrollPositionListener {
                override fun onScrolledToPosition(position: Int) {
                    binding.textSwitcher.setText(resources.getString(data[position].titleID))
                }

                override fun onScrolling() {
                    binding.textSwitcher.setText("")
                }
            })
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && isFull) {
            TransitionManager.beginDelayedTransition(
                binding.imageCoverFlow,
                backTransform
            )
            binding.fullImageView.isVisible = false
            binding.imageCoverFlow.isVisible = true
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun hideBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val insetsController = window.insetsController
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars())
                insetsController.hide(WindowInsets.Type.systemBars())
                insetsController.hide(WindowInsets.Type.navigationBars())
            }
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}