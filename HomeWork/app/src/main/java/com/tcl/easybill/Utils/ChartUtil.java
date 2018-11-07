package com.tcl.easybill.Utils;

import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Date;
import java.util.List;

import com.tcl.easybill.R;
import lecho.lib.hellocharts.model.AxisValue;

import static com.tcl.easybill.Utils.DateUtils.getEndDayOfLastWeek;
import static com.tcl.easybill.Utils.DateUtils.getEndDayOfWeek;

/**
 *图表工具
 */
public class ChartUtil {
    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    public static int dayValue = 0;
    public static int weekValue = 1;
    public static boolean thisWeek1 =true;
    public static int lastWeek =2;
    private static Date date;


    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    public static void initChart(LineChart chart) {
        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisRight().setDrawAxisLine(false);
        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTextColor(Color.WHITE);
        yAxis.setTextSize(15);
        yAxis.setAxisMinimum(0);
        chart.animateX(2500);
        //不显示右边
    }
    /**
     * 设置图表数据
     *
     * @param chart  图表
     * @param values 数据
     */
    public static void setChartData(LineChart chart, List<Entry> values) {
        initChart(chart);
        LineDataSet lineDataSet;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            lineDataSet = new LineDataSet(values, "");
            // 设置曲线颜色
            lineDataSet.setColor(Color.GREEN);
            lineDataSet.setDrawValues(true);
            // 设置平滑曲线
            //lineDataSet.setMode(LineDataSet.Mode.STEPPED);
            // 不显示坐标点的小圆点
            lineDataSet.setDrawCircles(true);
            // 不显示坐标点的数据
            lineDataSet.setDrawValues(false);
            // 不显示定位线
            lineDataSet.setHighlightEnabled(false);

            lineDataSet.setCircleColor(Color.BLACK);

            LineData data = new LineData(lineDataSet);
            chart.setData(data);
            chart.invalidate();
        }
    }
    /**
     * 更新图表
     *
     * @param chart     图表
     * @param values    数据
     * @param valueType 数据类型
     */
    public static void notifyDataSetChanged(LineChart chart, List<Entry> values, final int valueType)
    {

        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValuesProcess(valueType)[(int) value];
            }
        });

        chart.invalidate();
        setChartData(chart, values);
    }
    /**
     * x轴数据处理
     *
     * @param valueType 数据类型
     * @return x轴数据
     */
    private static String[] xValuesProcess(int valueType) {

         if (valueType == weekValue) { // 本周
            String[] weekValues = new String[7];
            if (thisWeek1){
                date  = getEndDayOfWeek();
            }else {
                date = getEndDayOfLastWeek();
            }
            int j=0;
            for (int i = 0; i< 7; i++) {
                j = 6-i;
                weekValues[i] =DateUtils.getDay(date.toString(),-j);
            }
            return weekValues;

        }
        return new String[]{};
    }

}
