package com.tcl.easybill.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.TextView;

import com.liuwan.customdatepicker.widget.CustomDatePicker;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import com.tcl.easybill.R;

import com.tcl.easybill.Utils.DateUtils;
import com.tcl.easybill.Utils.SnackbarUtils;
import com.tcl.easybill.Utils.UiUtils;
import com.tcl.easybill.Utils.stickyheader.StickyHeaderGridLayoutManager;
import com.tcl.easybill.base.SyncEvent;
import com.tcl.easybill.mvp.presenter.MonthDetailPresenter;
import com.tcl.easybill.mvp.presenter.impl.MonthDetailPresenterImpl;
import com.tcl.easybill.mvp.views.MonthDetailView;
import com.tcl.easybill.pojo.MonthDetailAccount;
import com.tcl.easybill.pojo.ShareBill;
import com.tcl.easybill.pojo.TotalBill;
import com.tcl.easybill.pojo.base;
import com.tcl.easybill.ui.activity.BillEditActivity;
import com.tcl.easybill.ui.activity.HomeActivity;
import com.tcl.easybill.ui.activity.SearchAll;
import com.tcl.easybill.Utils.meng_MyUtils;
import com.tcl.easybill.ui.adapter.MonthDetailAdapter;
import static android.view.Gravity.CENTER;
import static com.tcl.easybill.Utils.DateUtils.FORMAT_M;
import static com.tcl.easybill.Utils.DateUtils.FORMAT_Y;


public class BillFragment extends HomeBaseFragment implements MonthDetailView {
    private CustomDatePicker customDatePicker1;
    private String setYear = DateUtils.getCurYear(FORMAT_Y);
    private String setMonth = DateUtils.getCurMonth(FORMAT_M);

    private MonthDetailPresenter presenter;

    int part, index;
    private static final int SPAN_SIZE = 1;
    private StickyHeaderGridLayoutManager mLayoutManager;
    private MonthDetailAdapter adapter;
    private List<MonthDetailAccount.DaylistBean> list;
    meng_MyUtils meng_util = new meng_MyUtils();

    @BindView(R.id.money_in)
    TextView tOutcome;
    @BindView(R.id.money_out)
    TextView tIncome;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.bill_time_month)
    EditText currentDate;
    @BindView(R.id.bill_time_year)
    EditText currentYear;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SyncEvent event) {
        if (event.getState()==100)
            //getBills(Constants.currentUserId, setYear, setMonth);
            getBills(currentUser.getObjectId(), setYear, setMonth);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bill;
    }
    @Override
    protected void importantData() {
        left();
        //register EventBus
        EventBus.getDefault().register(this);

        flash();

        presenter=new MonthDetailPresenterImpl(this);

        //Request monthly data
        getBills(currentUser.getObjectId(), setYear, setMonth);
        Log.e(TAG, "importantData: year"+setYear+"month"+setMonth );
        initDatePicker();
    }
    @Override
    protected void loadData() {
    }
    /**
     *Monitor list's side pull
     */
    public void left(){
        ShareBill shareBill = new ShareBill();
        Log.e("meng111", "left: " );
        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(5);

        rvList.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });
        rvList.setLayoutManager(mLayoutManager);
        adapter = new MonthDetailAdapter(mContext, list);
        rvList.setAdapter(adapter);

        adapter.setOnStickyHeaderClickListener(new MonthDetailAdapter.OnStickyHeaderClickListener() {
            @Override
            public void OnDeleteClick(TotalBill item, int section, int offset) {
                item.setVersion(-1);
                /*The deleted version number of the bill is set to negative instead of directly deleted.*/
                /*sync bills convenient*/
                presenter.updateBill(item);
               /* BmobRepository.getInstance().deleteBills(shareBill.getObjectId());*/
                part = section;
                index = offset;
            }

            @Override
            public void OnEditClick(TotalBill item, int section, int offset) {
                Intent intent = new Intent(mContext, BillEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", item.getId());
                bundle.putString("rid", item.getRid());
                bundle.putString("sortName", item.getSortName());
                bundle.putString("payName", item.getPayName());
                bundle.putString("content", item.getContent());
                bundle.putDouble("cost", item.getCost());
                bundle.putLong("date", item.getCrdate());
                bundle.putBoolean("income", item.isIncome());
                bundle.putInt("version", item.getVersion());
                intent.putExtra("bundle", bundle);
                startActivityForResult(intent, 0);
            }
        });
    }
    /**
     * call back
     * @param tData
     */
    @Override
    public void loadDataSuccess(MonthDetailAccount tData) {
        BigDecimal outcome = UiUtils.getSmallNumber(tData.getT_outcome());
        BigDecimal income = UiUtils.getSmallNumber(tData.getT_income());
        ((HomeActivity)getActivity()).setmData(outcome.toString());
        tOutcome.setText(outcome.toString());
        tIncome.setText(income.toString());
        list = tData.getDaylist();
        adapter.setmDatas(list);
        adapter.notifyAllSectionsDataSetChanged();//需调用此方法刷新
    }

    @Override
    public void loadDataSuccess(base tData) {
        adapter.remove(part, index);
    }


    @Override
    public void loadDataError(Throwable throwable) {
        SnackbarUtils.show(mActivity,throwable.getMessage());
    }

    /**
     *Drop-down refresh and load data
     */
    public void flash(){
        swipe.setColorSchemeColors(getResources().getColor(R.color.text_red), getResources().getColor(R.color.text_red));

        swipe.setDistanceToTriggerSync(200);//set up how much refresh has been set to pull down.
                swipe.setProgressViewEndTarget(false, 200); //Set refresh position

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                getBills(currentUser.getObjectId(), setYear, setMonth);

            }
        });

    }
    /**
     * refresh data
     */
    public void flashData(){
        getBills(currentUser.getObjectId(), setYear, setMonth);
    }
    /**
     * get bills data
     */
    public void getBills(String userId, String year, String month) {

        presenter.getMonthDetailBills(userId, setYear, setMonth);

    }

    /**
     * date choicer
     */
    /*id = R.id.bill_time setTime on the editext*/
    @OnClick({R.id.bill_time_year,R.id.bill_time_month})
    public void setTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker1.show(/*currentDate.getText().toString()*/now);
        Log.e("mengtime","settime");
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        String month1 = meng_util.getMonth(now);// ini
        String year = meng_util.getYear(now);
        currentDate.setText(Html.fromHtml("<big>"+month1+"</big>"+"<small>"+"月"+"</small>"));
        currentYear.setText(year);
        customDatePicker1 = new CustomDatePicker(this.getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                meng_MyUtils meng_util = new meng_MyUtils();
                String month = meng_util.getMonth(time);
                String year = meng_util.getYear(time);
                currentDate.setText(Html.fromHtml("<big>"+month+"</big>"+"<small>"+"月"+"</small>"));
                currentYear.setText(year.split(" ")[0]);
                setYear = time.substring(0,4);
                setMonth = time.substring(5,7);
                //getBills(Constants.currentUserId, setYear, setMonth);
               getBills(currentUser.getObjectId(), setYear, setMonth);
            }
        }, "2017-01-01 00:00", "2019-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(true); // 不允许循环滚动

    }


    /**
     * Activity返回
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getBills(currentUser.getObjectId(), setYear, setMonth);
        super.onActivityResult(requestCode, resultCode, data);
            //getBills(Constants.currentUserId, setYear, setMonth);
    }

    public static BillFragment newInstance(String info) {
        Bundle args = new Bundle();
        BillFragment fragment = new BillFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    protected void beforeDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void myToolbar(){
        /**
         * set  toolbar  and show
         * */
       super.myToolbar();
        TextView title = new TextView(getActivity());
        title.setText("账单");
        title.setTextSize(18);
        title.setTextColor(getResources().getColor(R.color.white));
        title.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        title.setLayoutParams(new Toolbar.LayoutParams(CENTER));
        title.setGravity(CENTER);
        setToolbar(title);

    }

    /**
     * set toolbar right icon
     * @return
     */
    @Override
    protected int getItemMenu(){ return R.menu.menu_main; }

    @Override
    protected void setItemReact(){
        Intent intent = new Intent(getActivity(), SearchAll.class);
        startActivity(intent);
    }

    @Override
    protected  Toolbar getToolbar(){
        return getActivity().findViewById(R.id.toolbar_bill);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated){
            return;
        }
        if (isVisibleToUser){
            getBills(currentUser.getObjectId(),setYear,setMonth);
        }

    }
}
