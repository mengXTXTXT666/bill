package com.tcl.easybill.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.tcl.easybill.R;

import com.tcl.easybill.Utils.DateUtils;
import com.tcl.easybill.Utils.SnackbarUtils;
import com.tcl.easybill.mvp.presenter.MonthChartPresenter;
import com.tcl.easybill.mvp.presenter.impl.MonthChartPresenterImpl;
import com.tcl.easybill.mvp.views.MonthChartView;
import com.tcl.easybill.pojo.MonthBillForChart;
import com.tcl.easybill.pojo.MonthDetailAccount;

import com.tcl.easybill.ui.activity.SearchAll;
import com.tcl.easybill.ui.adapter.ChartAdapter;
import com.tcl.easybill.ui.adapter.MonthChartAdapter;

import static android.view.Gravity.CENTER;
import static com.tcl.easybill.Utils.DateUtils.FORMAT_M;
import static com.tcl.easybill.Utils.DateUtils.FORMAT_Y;


public class ChartFragment extends HomeBaseFragment implements /*MonthChartView,*/MonthChartView/*BillFragment.DailyDatas*/ {

    @BindView(R.id.thisweek)
    Button thisWeekBtn;
    @BindView(R.id.lastweek)
    Button lastWeekBtn;
    @BindView(R.id.rv_list_chart)
    RecyclerView rvList;
    @BindView(R.id.swipe2)
    SwipeRefreshLayout swipe;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private MonthChartAdapter adapter;

    private final static int FULL_SPAN = 3;
    private final static int MULTI_SPAN = 2;
    private final static int SINGLE_SPAN = 1;
    private String TAG = "meng111";

    private  GridLayoutManager mLayoutManager;
    private String setYear = DateUtils.getCurYear(FORMAT_Y);
    private String setMonth = DateUtils.getCurMonth(FORMAT_M);
    private MonthChartPresenter presenter;
    private ChartAdapter chartAdapter;

    List<MonthBillForChart.SortTypeList> listInComeData ;
    List<MonthBillForChart.SortTypeList> listOutComeData;
    List<MonthDetailAccount.DaylistBean> detailList;

    private Context context;
    private String crdate;
    private int week;
    private String allMoney;
    private String income;
    private String outcome;
    private static boolean isIncome = true;
    public static boolean isThisWeek = true;
    private Date beginDayOfWeek;
    private Date endDayOfWeek;
    private Date beginDayOfLastWeek;
    private Date endDayOfLastWeek;

    private float[] incomeValues = {0f,0f,0f,0f,0f,0f,0f};
    private float[] outcomeValues = {0f,0f,0f,0f,0f,0f,0f};


    @OnClick({R.id.thisweek, R.id.lastweek})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.thisweek:
                lastWeekBtn.setTextColor(getResources().getColor(R.color.tab_unclicked));
                lastWeekBtn.setBackground(getResources().getDrawable(R.drawable.chart_button_unclick));
                thisWeekBtn.setTextColor(getResources().getColor(R.color.tab_clicked));
                thisWeekBtn.setBackground(getResources().getDrawable(R.drawable.chart_button_click));
                isThisWeek =true;
                presenter.getMonthChartBills(currentUser.getObjectId(), setYear, setMonth);
                break;
            case R.id.lastweek:
                thisWeekBtn.setTextColor(getResources().getColor(R.color.tab_unclicked));
                thisWeekBtn.setBackground(getResources().getDrawable(R.drawable.chart_button_unclick));
                lastWeekBtn.setTextColor(getResources().getColor(R.color.tab_clicked));
                lastWeekBtn.setBackground(getResources().getDrawable(R.drawable.chart_button_click));
                isThisWeek =false;
                presenter.getMonthChartBills(currentUser.getObjectId(), setYear, setMonth);
                break;
            default:
                break;
        }
    }
    public void data(){
        Log.e(TAG, "data: " );
        /*Determine whether income or expenditure and display data*/
        if (isIncome ){
            if (isThisWeek){//isIncome=true,isThisWeek=true
                WeekData(beginDayOfWeek,endDayOfWeek);
                setChart(incomeValues,isThisWeek);
            }else {//isIncome=false,isThisWeek=true
                WeekData(beginDayOfLastWeek,endDayOfLastWeek);
                setChart(incomeValues,isThisWeek);
            }
            setList(listInComeData);

        }else {
            if (isThisWeek){//isIncome=false,isThisWeek=true
                WeekData(beginDayOfWeek,endDayOfWeek);
                setChart(outcomeValues,isThisWeek);
            }else {//isIncome=false,isThisWeek=false
                WeekData(beginDayOfLastWeek,endDayOfLastWeek);
                setChart(outcomeValues,isThisWeek);
            }
            setList(listOutComeData);
        }
    }
    /**
     * load data success
     *
     */
    @Override
    public void loadDataSuccess(MonthBillForChart tData) {
        rvList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        context = getActivity().getApplicationContext();
        listInComeData = tData.getInSortlist();
        listOutComeData = tData.getOutSortlist();
        detailList = tData.getDaylist();

        beginDayOfWeek = DateUtils.getBeginDayOfWeek();
        endDayOfWeek = DateUtils.getEndDayOfWeek();
        beginDayOfLastWeek = DateUtils.getBeginDayOfLastWeek();
        endDayOfLastWeek = DateUtils.getEndDayOfLastWeek();
        data();

        }

    /**
     * Revenue and expenditure this week or last week
     */
    public void WeekData(Date beginDay,Date endDay){
        for (int i=0;i<7;i++){
            incomeValues[i]=0f;
            outcomeValues[i]=0f;
        }
        for (int i =0;i<detailList.size();i++){
            crdate=detailList.get(i).getTime();
            crdate = crdate+" 12:00:00";
            if (DateUtils.belongCalendar(DateUtils.str2Date(crdate),beginDay,endDay)){
                try {
                    week =  DateUtils.DayForWeek(crdate);
                    allMoney = detailList.get(i).getMoney();

                    income = allMoney.substring(allMoney.indexOf("入")+2,allMoney.length());
                    incomeValues[week-1] = Float.valueOf(income);

                    outcome = allMoney.substring(3, allMoney.indexOf(" "));
                    outcomeValues[week-1] = Float.valueOf(outcome);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /**
     * Drop-down refresh and load data
     */
    public void flash(){
        swipe.setColorSchemeColors(getResources().getColor(R.color.text_red), getResources().getColor(R.color.text_red));

        swipe.setDistanceToTriggerSync(50);//set up how much refresh has been set to pull down.
        swipe.setProgressViewEndTarget(false, 250);//Set refresh position

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                presenter.getMonthChartBills(currentUser.getObjectId(), setYear, setMonth);
            }
        });
    }

    /**
     * add chart
     * @param values
     * @param isThisWeek
     */
    public void setChart(float[] values,boolean isThisWeek){
        chartAdapter = new ChartAdapter(getContext(),values,isThisWeek,5);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(chartAdapter);
    }
    /**
     * add listview
     */
    public void setList(List<MonthBillForChart.SortTypeList> Data){
        adapter = new MonthChartAdapter(getActivity(),Data);
        rvList.setAdapter(adapter);
    }
    /**
     *Returns the menu right item  of Toolbar.
     */
    @Override
    protected void importantData() {

        presenter=new MonthChartPresenterImpl(this);
        presenter.getMonthChartBills(currentUser.getObjectId(), setYear, setMonth);
        flash();
        context = getActivity().getApplicationContext();
        mLayoutManager = new GridLayoutManager(getContext(), FULL_SPAN);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                switch (chartAdapter.getItemViewType(position)) {
                    case 0:
                        return FULL_SPAN;
                    default:
                        return SINGLE_SPAN;
                }
            }
        });
        float[] mValues = {0f, 0f, 0f, 0f, 0f, 0f, 0f};

        setChart(mValues,isThisWeek);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart;
    }


    @Override
    protected void loadData() {
    }


    @Override
    protected void beforeDestroy() {
    }

    @Override
    protected int getItemMenu() {
        return R.menu.menu_main;
    }
    /**
     * open the query button
     */
    @Override
    protected void setItemReact() {
        Intent intent = new Intent(getActivity(), SearchAll.class);
        startActivity(intent);
    }
    @Override
    protected Toolbar getToolbar() {
        return getActivity().findViewById(R.id.toolbar_chart);
    }


    @Override
    public void loadDataError(Throwable throwable) {
        SnackbarUtils.show(mActivity, throwable.getMessage());
    }
    /**
     * override myToolBar ,add spinner as drop-down box, add OnclickListener
     */
    @Override
    public void myToolbar() {
        super.myToolbar();
        /*set spinner*/
        Spinner type = new Spinner(getActivity());
        List<String> dataOfType = new ArrayList<String>();
        dataOfType.add("收入");
        dataOfType.add("支出");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_style, dataOfType);
        typeAdapter.setDropDownViewResource(R.layout.spinner_drop_style);
        type.setAdapter(typeAdapter);
        type.setDropDownVerticalOffset(60);
        type.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        type.setPopupBackgroundDrawable(getResources().getDrawable(R.color.center_color));
        type.setLayoutParams(new Toolbar.LayoutParams(CENTER));
        type.setBackground(getResources().getDrawable(R.drawable.spinner_background));
        setToolbar(type);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        isIncome = true;
                        //isThisWeek=true;
                        presenter.getMonthChartBills(currentUser.getObjectId(), setYear, setMonth);

                        break;
                    case 1:
                        isIncome = false;
                        //isThisWeek=true;
                        presenter.getMonthChartBills(currentUser.getObjectId(), setYear, setMonth);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static ChartFragment newInstance(String info) {
        Bundle args = new Bundle();
        ChartFragment fragment = new ChartFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     *when Switch fragment ,reload data
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            presenter.getMonthChartBills(currentUser.getObjectId(),setYear,setMonth);
        }

    }
}
