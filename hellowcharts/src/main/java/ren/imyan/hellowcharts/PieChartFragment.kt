package ren.imyan.hellowcharts

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import ren.imyan.fragment.BaseFragment
import ren.imyan.hellowcharts.databinding.FragmentPieChartBinding

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-08 15:57
 * @website https://imyan.ren
 */
class PieChartFragment : BaseFragment<FragmentPieChartBinding, ChartViewModel>() {

    private val pieData by lazy {
        listOf(8, 15, 84, 36, 10, 25)
    }

    private val stateChar by lazy {
        listOf("第一个", "第二个", "第三个", "第四个", "第五个", "第六个")
    }

    private val color by lazy {
        ArrayList<Int>().apply {
            pieData.forEachIndexed { _, _ ->
                val currColor = Integer.toHexString((-(Math.random() * (16777216 - 1) + 1)).toInt())
                add(Color.parseColor("#$currColor"))
            }
        }
    }

    private val sliceValue by lazy {
        ArrayList<SliceValue>().apply {
            pieData.forEachIndexed { index, i ->
                add(SliceValue(i.toFloat(), color[index]))
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPieChartBinding = FragmentPieChartBinding.inflate(layoutInflater, container, false)

    override fun initViewModel(): ChartViewModel =
        ViewModelProvider(requireActivity())[ChartViewModel::class.java]

    override fun initView() {
        binding.pieChart.apply {
            isValueSelectionEnabled = true
            alpha = 0.9f
            circleFillRatio = 1f
        }
    }

    override fun loadData() {
        val data = PieChartData().apply {
            setHasLabels(true)
            setHasLabelsOnlyForSelected(false)
            setHasLabelsOutside(false)
            setHasCenterCircle(true)
            values = sliceValue
            centerCircleColor = Color.WHITE
            centerCircleScale = 0.5f
            centerText1 = "数据"
        }

        val selectListener = object : PieChartOnValueSelectListener {
            override fun onValueDeselected() {}

            override fun onValueSelected(arcIndex: Int, value: SliceValue?) {
                data.centerText1 = stateChar[arcIndex]
                data.centerText2 = "${value?.value}(${calPercent(pieData[arcIndex])})"
            }
        }

        binding.pieChart.apply {
            onValueTouchListener = selectListener
            pieChartData = data
        }
    }

    private fun calPercent(pieDataEle: Int): String {
        val sum = pieData.sum()
        return String.format("% .2f", (pieDataEle * 100 / sum).toFloat()) + "%"
    }
}