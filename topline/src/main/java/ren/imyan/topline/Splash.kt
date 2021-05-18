package ren.imyan.topline

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import ren.imyan.activity.BaseActivity
import ren.imyan.topline.databinding.ActivitySplashBinding
import ren.imyan.topline.ui.MainActivity
import ren.imyan.topline.util.start
import java.util.*
import kotlin.concurrent.timerTask

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-27 16:37
 * @website https://imyan.ren
 */
class Splash : BaseActivity() {

    private val binding by lazy{
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        hideBar()
        init()
    }

    private fun init() {
        val timer = Timer()
        val task = timerTask {
            start<MainActivity>()
            finish()
        }
        timer.schedule(task, 3000)
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