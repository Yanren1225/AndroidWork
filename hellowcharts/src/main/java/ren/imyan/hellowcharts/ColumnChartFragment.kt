package ren.imyan.hellowcharts

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import lecho.lib.hellocharts.gesture.ZoomType
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.util.ChartUtils
import ren.imyan.fragment.BaseFragment
import ren.imyan.hellowcharts.databinding.FragmentColumnChartBinding

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-08 20:49
 * @website https://imyan.ren
 */
class ColumnChartFragment : BaseFragment<FragmentColumnChartBinding, ChartViewModel>() {

    private val years by lazy {
        listOf("2013", "2014", "2015", "2016", "2017")
    }

    private val columnY by lazy {
        listOf(500, 1000, 1500, 2500, 3000)
    }

    private val columns by lazy {
        ArrayList<Column>()
    }

    private val axisValues by lazy {
        ArrayList<AxisValue>()
    }

    private val axisYValues by lazy {
        ArrayList<AxisValue>().apply {
            columnY.forEachIndexed { index, i ->
                add(AxisValue(index.toFloat()).setValue(i.toFloat()))
            }
        }
    }

    private var subColumnValues = ArrayList<SubcolumnValue>()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentColumnChartBinding = FragmentColumnChartBinding.inflate(inflater, container, false)

    override fun initViewModel(): ChartViewModel =
        ViewModelProvider(requireActivity())[ChartViewModel::class.java]

    override fun initView() {
        binding.columnChart.apply {
            isValueSelectionEnabled = false
            zoomType = ZoomType.HORIZONTAL
        }
    }

    override fun loadData() {
        years.forEachIndexed { index, s ->
            subColumnValues = ArrayList()
            subColumnValues.apply {
                for (j in 0..1) {
                    when (index + 1) {
                        1 -> add(SubcolumnValue(924f, ChartUtils.COLOR_BLUE))
                        2 -> add(SubcolumnValue(1120f, ChartUtils.COLOR_GREEN))
                        3 -> add(SubcolumnValue(1610f, ChartUtils.COLOR_RED))
                        4 -> add(SubcolumnValue(2125f, ChartUtils.COLOR_ORANGE))
                        5 -> add(SubcolumnValue(2805f, ChartUtils.COLOR_VIOLET))
                    }
                }
                axisValues.add(AxisValue(index.toFloat()).setLabel(s))
                columns.add(Column(this).setHasLabelsOnlyForSelected(true))
            }
        }
        // X
        val x = Axis(axisValues).apply {
            setHasLines(false)
            textColor = Color.BLACK
        }
        // Y
        val y = Axis(axisYValues).apply {
            setHasLines(true)
            textColor = Color.BLACK
            maxLabelChars = 5
        }

        val data = ColumnChartData(columns).apply {
            axisXBottom = x
            axisYLeft = y
        }

        binding.columnChart.columnChartData = data
    }
}