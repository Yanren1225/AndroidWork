package ren.imyan.hellowcharts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ren.imyan.hellowcharts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initPager()
        initBottomBar()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.layoutToolbar.toolbar)
        supportActionBar?.title = "图表库"
    }

    private fun initPager() {
        binding.viewpager.adapter = ViewPagerAdapter(
            supportFragmentManager,
            lifecycle,
            listOf(LineChartFragment(), PieChartFragment(), ColumnChartFragment())
        )
        binding.viewpager.isUserInputEnabled = false
        binding.viewpager.offscreenPageLimit = 3
    }

    private fun initBottomBar() {
        binding.navigation.setOnNavigationItemSelectedListener {
            binding.viewpager.currentItem = when (it.itemId) {
                R.id.line -> 0
                R.id.pie -> 1
                R.id.column -> 2
                else -> 0
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}