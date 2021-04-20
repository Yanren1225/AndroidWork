package ren.imyan.hellowcharts

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import lecho.lib.hellocharts.gesture.ContainerScrollType
import lecho.lib.hellocharts.gesture.ZoomType
import lecho.lib.hellocharts.model.*
import ren.imyan.fragment.BaseFragment
import ren.imyan.hellowcharts.databinding.FragmentLineChartBinding

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-08 14:36
 * @website https://imyan.ren
 */
class LineChartFragment : BaseFragment<FragmentLineChartBinding, ChartViewModel>() {

    private val temperature by lazy {
        listOf(25, 27, 26, 21, 19, 27, 25)
    }

    private val lineData by lazy {
        listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")
    }

    private val axisValues by lazy {
        ArrayList<AxisValue>().apply {
            temperature.forEachIndexed { index, _ ->
                add(AxisValue(index.toFloat()).setLabel(lineData[index]))
            }
        }
    }

    private val pointValues by lazy {
        ArrayList<PointValue>().apply {
            temperature.forEachIndexed { index, i ->
                add(PointValue(index.toFloat(), i.toFloat()))
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLineChartBinding =
        FragmentLineChartBinding.inflate(inflater, container, false)

    override fun initViewModel(): ChartViewModel =
        ViewModelProvider(requireActivity())[ChartViewModel::class.java]

    override fun initView() {
        binding.lineChart.apply {
            isInteractive = true
            zoomType = ZoomType.HORIZONTAL
            maxZoom = 2.toFloat()
            setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL)
        }
    }

    override fun loadData() {
        val lines = ArrayList<Line>().apply {
            add(Line().apply {
                color = Color.parseColor("#33b5e5")
                shape = ValueShape.CIRCLE
                isCubic = false
                setHasLabels(true)
                setHasLines(true)
                setHasPoints(true)
                values = (pointValues)
            })
        }

        // X 轴
        val axisX = Axis().apply {
            setHasTiltedLabels(true)
            textColor = Color.BLACK
            maxLabelChars = 5
            values = axisValues
            setHasLines(true)
        }

        // Y 轴
        val axisY = Axis().apply {
            textColor = Color.BLACK
            maxLabelChars = 5
        }

        val data = LineChartData().apply {
            setLines(lines)
            axisXBottom = axisX
            axisYLeft = axisY
        }

        binding.lineChart.lineChartData = data
        binding.lineChart.visibility = View.VISIBLE
    }
}