package ren.imyan.topline.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import ren.imyan.activity.ActivityCollector
import ren.imyan.topline.R
import ren.imyan.topline.core.BaseUIActivity
import ren.imyan.topline.databinding.ActivityMainBinding
import ren.imyan.topline.util.toast
import kotlin.system.exitProcess

class MainActivity : BaseUIActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override fun initViewModel(): MainViewModel =
        ViewModelProvider(this)[MainViewModel::class.java]

    override fun initToolbar(): Pair<Toolbar, *> =
        Pair(binding.layoutToolbar.toolbar, "测试")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragmentPager()
    }

    private fun initFragmentPager() {
        binding.viewpager.apply {
            adapter = ViewPagerAdapter(
                supportFragmentManager,
                lifecycle,
                viewModel.fragmentList
            )
            isUserInputEnabled = false
            offscreenPageLimit = 3
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        var exitTime = 0L
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toast("再按一次退出")
                exitTime = System.currentTimeMillis()
            } else {
                ActivityCollector.finishAll()
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}