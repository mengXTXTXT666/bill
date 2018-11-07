package com.tcl.easybill.Utils;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;

import java.util.Date;

import com.tcl.easybill.R;
import com.tcl.easybill.ui.widget.CardController;



import static com.tcl.easybill.Utils.DateUtils.getEndDayOfLastWeek;
import static com.tcl.easybill.Utils.DateUtils.getEndDayOfWeek;


public class LineCardOne extends CardController {


    private final LineChartView mChart;


    private final Context mContext;
    private  String[] mLabels =new String[7];
    private Tooltip mTip;
    private Date date;
    private int now ;
    private Runnable mBaseAction;
    private boolean thisweek;
    private float[] floats;
    private float[] data={1f,2f,3f,4f,5f,6f,7f};
    public LineCardOne(CardView card, Context context, float[] floats,boolean week,int now) {

        super(card);
        this.floats=floats;
        this.now=now;
        this.thisweek=week;
        this.mContext = context;
        this.mChart = (LineChartView) card.findViewById(R.id.chart);
    }


    @Override
    public void show(Runnable action) {

        super.show(action);

        // Tooltip
        mTip = new Tooltip(mContext, R.layout.linechart_three_tooltip, R.id.value);

        ((TextView) mTip.findViewById(R.id.value)).setTypeface(
                Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Semibold.ttf"));

        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            mTip.setPivotX(Tools.fromDpToPx(65) / 2);
            mTip.setPivotY(Tools.fromDpToPx(25));
        }

        // 本周
            if (thisweek) {
                date = getEndDayOfWeek();
            } else {
                date = getEndDayOfLastWeek();
            }
            int j = 0;
            for (int i = 0; i < 7; i++) {
                j = 6 - i;
                mLabels[i] = DateUtils.getDay(date.toString(), -j);
            }

        // Data
        LineSet dataset = new LineSet(mLabels, floats);
        dataset.setColor(Color.parseColor("#758cbb"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(4)
                .setDashed(new float[]{10f, 10f})
                .beginAt(4);
        mChart.addData(dataset);

        dataset = new LineSet(mLabels, floats);
        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4)
                .endAt(5);
        mChart.addData(dataset);

        mBaseAction = action;
        Runnable chartAction = new Runnable() {
            @Override
            public void run() {

                mBaseAction.run();
                mTip.prepare(mChart.getEntriesArea(0).get(3),floats[3]);
                mChart.showTooltip(mTip, true);
            }
        };

        mChart/*.setAxisBorderValues(0, 20)*/
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setYAxis(false)
                .setXAxis(false)
                .setTooltips(mTip)
                .show(new Animation().setInterpolator(new BounceInterpolator())
                        .fromAlpha(0)
                        .withEndAction(chartAction));
    }


    /*@Override
    public void update() {

        super.update();

        mChart.dismissAllTooltips();
        if (firstStage) {
            mChart.updateValues(0, floats);
            mChart.updateValues(1, data);
            mChart.updateValues(2, floats);
        } else {
            *//*mChart.updateValues(0, floats);
            mChart.updateValues(1, floats);*//*
            mChart.updateValues(0, floats);
            mChart.updateValues(1, data);
            mChart.updateValues(2, floats);
        }
        mChart.getChartAnimation().withEndAction(mBaseAction);
        mChart.notifyDataUpdate();
    }*/


    @Override
    public void dismiss(Runnable action) {

        super.dismiss(action);

        mChart.dismissAllTooltips();
        mChart.dismiss(new Animation().setInterpolator(new BounceInterpolator()).withEndAction(action));
    }

}
