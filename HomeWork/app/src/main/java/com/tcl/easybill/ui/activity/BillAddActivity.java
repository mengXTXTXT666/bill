package com.tcl.easybill.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.tcl.easybill.R;
import com.tcl.easybill.Utils.ToastUtils;
import com.tcl.easybill.base.BmobRepository;
import com.tcl.easybill.pojo.ShareBill;
import com.tcl.easybill.ui.adapter.BookNoteAdapter;
import com.tcl.easybill.Utils.DateUtils;
//import com.tcl.easybill.Utils.ProgressUtils;
import com.tcl.easybill.Utils.SharedPUtils;
import com.tcl.easybill.Utils.SnackbarUtils;
import com.tcl.easybill.base.Constants;
import com.tcl.easybill.base.LocalRepository;
import com.tcl.easybill.mvp.presenter.BillPresenter;
import com.tcl.easybill.mvp.presenter.impl.BillPresenterImpl;
import com.tcl.easybill.mvp.views.BillView;
import com.tcl.easybill.pojo.AllSortBill;
import com.tcl.easybill.pojo.SortBill;
import com.tcl.easybill.pojo.TotalBill;
import com.tcl.easybill.pojo.base;
import com.tcl.easybill.ui.adapter.MonthAccountAdapter;

import static com.tcl.easybill.Utils.DateUtils.FORMAT_M;
import static com.tcl.easybill.Utils.DateUtils.FORMAT_Y;

/**
 * add bill
 */
public class BillAddActivity extends BaseActivity implements BillView {

    @BindView(R.id.tb_note_income)
    TextView incomeTv;    //income button
    @BindView(R.id.tb_note_outcome)
    TextView outcomeTv;   //outcome button
    @BindView(R.id.item_tb_type_tv)
    TextView sortTv;     //show bill's sort
    @BindView(R.id.tb_note_money)
    TextView moneyTv;
    @BindView(R.id.tb_note_date)
    TextView dateTv;      //date choice
    @BindView(R.id.tb_note_remark)
    ImageView remarkIv;
    @BindView(R.id.viewpager_item)
    ViewPager viewpagerItem;
    @BindView(R.id.layout_icon)
    LinearLayout layoutIcon;

    protected BillPresenter presenter;


    public boolean isOutcome = false;
    /*counter */
    protected boolean isDot;
    protected String num = "0";               //integer part
    protected String dotNum = ".00";          //decimal part
    protected final int MAX_NUM = 9999999;    //max number
    protected final int DOT_NUM = 2;          //max number in decimal part
    protected int count = 0;
    /*choicer*/
    protected OptionsPickerView pvCustomOptions;
    protected List<String> cardItem;
    /*viewpager data*/
    protected int page;
    protected boolean isTotalPage;
    protected int sortPage = -1;
    protected List<SortBill> mDatas;
    protected List<SortBill> tempList;
    /*note the last sort's click*/
    public SortBill lastBean;


    protected AlertDialog alertDialog;

    /*time choicer*/
    protected int mYear;
    protected int mMonth;
    protected int mDay;
    protected String days;
    private SimpleDateFormat simpleDateFormat;


    /*notes*/
    protected String remarkInput = "";
    protected AllSortBill noteBean = null;




    @Override
    protected int getLayout() {
        return R.layout.activity_add;
    }
    /**
     *  initEventAndData assist in oncreate
     */
    @Override
    protected void initEventAndData() {

        presenter = new BillPresenterImpl(this);

        //loading data
        initSortView();

        /*set up begin day of time choicer*/
        mYear = Integer.parseInt(DateUtils.getCurYear(FORMAT_Y));
        mMonth = Integer.parseInt(DateUtils.getCurMonth(FORMAT_M));
        /*set up now */
        days = DateUtils.getCurDateStr("yyyy-MM-dd");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        if (simpleDateFormat.format(date).equals(days)) {
            dateTv.setText("今天");
        } else {
            dateTv.setText(days);
        }
    }


    @Override
    public void loadDataSuccess(AllSortBill tData) {
        noteBean=tData;
        setTitleStatus();//load data when success
    }

    @Override
    public void loadDataSuccess(base tData) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void loadDataError(Throwable throwable) {
        SnackbarUtils.show(mContext,throwable.getMessage());
    }
    /**
     * load sort data
     */
    protected void initSortView() {
        noteBean= SharedPUtils.getUserNoteBean(this);
        //get local data fail
        if (noteBean==null){
            //sync data
            presenter.getNote();
        }else {
            //load data when success
            setTitleStatus();
        }
    }
    /**
     * set up status
     */
    protected void setTitleStatus() {

        setTitle();
        //default choice first sort
        lastBean = mDatas.get(0);
        //set up choice sort
        sortTv.setText(lastBean.getSortName());
        initViewPager();
    }

    protected void setTitle(){
        if (isOutcome) {
            //set up payment
            outcomeTv.setSelected(true);
            incomeTv.setSelected(false);
            mDatas = noteBean.getOutSortList();
        } else {
            //set up income status
            incomeTv.setSelected(true);
            outcomeTv.setSelected(false);
            mDatas = noteBean.getInSortList();
        }
    }

    protected void initViewPager() {
        LayoutInflater inflater = this.getLayoutInflater();
        viewList = new ArrayList<>();
        List<SortBill> tempData=new ArrayList<>();//use for store up sort
        tempData.addAll(mDatas);
        //tempData.add(new SortBill(null,"添加", "sort_tianjia.png",0,null));
        if (tempData.size() % 15 == 0)
            isTotalPage = true;
        page = (int) Math.ceil(tempData.size() * 1.0 / 15);
        for (int i = 0; i < page; i++) {
            tempList = new ArrayList<>();
            View view = inflater.inflate(R.layout.pager_item_tb_type, null);
            final RecyclerView recycle = (RecyclerView) view.findViewById(R.id.pager_type_recycle);
            if (i != page - 1 || (i == page - 1 && isTotalPage)) {
                for (int j = 0; j < 15; j++) {
                    tempList.add(tempData.get(i * 15 + j));
                }
            } else {
                for (int j = 0; j < tempData.size() % 15; j++) {
                    tempList.add(tempData.get(i * 15 + j));
                }
            }

            BookNoteAdapter mAdapter = new BookNoteAdapter(this, tempList);
            mAdapter.setOnBookNoteClickListener(new BookNoteAdapter.OnBookNoteClickListener() {
                @Override
                public void OnClick(int index) {

                    //get index
                    index=index + viewpagerItem.getCurrentItem() * 15;
                    /*if (index==mDatas.size()) {
                        //修改分类
                        Intent intent = new Intent(BillAddActivity.this, SortEditActivity.class);
                        intent.putExtra("type", isOutcome);
                        startActivityForResult(intent, 0);
                    } else{*/
                        //select sort
                        lastBean = mDatas.get(index);
                        sortTv.setText(lastBean.getSortName());


                }

                @Override
                public void OnLongClick(int index) {
                    Toast.makeText(BillAddActivity.this, "长按", Toast.LENGTH_SHORT).show();
                }
            });
            GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
            recycle.setLayoutManager(layoutManager);
            recycle.setAdapter(mAdapter);
            viewList.add(view);
        }

        viewpagerItem.setAdapter(new MonthAccountAdapter(viewList));
        viewpagerItem.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewpagerItem.setOffscreenPageLimit(1);//preload data
        viewpagerItem.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    for (int i = 0; i < viewList.size(); i++) {
                        icons[i].setImageResource(R.drawable.icon_banner_point2);
                    }
                    icons[position].setImageResource(R.drawable.icon_banner_point1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initIcon();
    }

    protected List<View> viewList;
    protected ImageView[] icons;

    /**
     * add bill sort indicator
     */
    protected void initIcon() {
        icons = new ImageView[viewList.size()];
        layoutIcon.removeAllViews();
        for (int i = 0; i < icons.length; i++) {
            icons[i] = new ImageView(this);
            icons[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            icons[i].setImageResource(R.drawable.icon_banner_point2);
            if (viewpagerItem.getCurrentItem() == i) {
                icons[i].setImageResource(R.drawable.icon_banner_point1);
            }
            icons[i].setPadding(5, 0, 5, 0);
            icons[i].setAdjustViewBounds(true);
            layoutIcon.addView(icons[i]);
        }
        if (sortPage != -1)
            viewpagerItem.setCurrentItem(sortPage);
    }


    @OnClick({R.id.tb_note_income, R.id.tb_note_outcome, R.id.tb_note_date,
            R.id.tb_note_remark, R.id.tb_calc_num_done, R.id.tb_calc_num_del, R.id.tb_calc_num_1,
            R.id.tb_calc_num_2, R.id.tb_calc_num_3, R.id.tb_calc_num_4, R.id.tb_calc_num_5,
            R.id.tb_calc_num_6, R.id.tb_calc_num_7, R.id.tb_calc_num_8, R.id.tb_calc_num_9,
            R.id.tb_calc_num_0, R.id.tb_calc_num_dot, R.id.tb_note_clear, R.id.back_btn,})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.tb_note_income://income
                isOutcome = false;
                setTitleStatus();
                break;
            case R.id.tb_note_outcome://outcome
                isOutcome = true;
                setTitleStatus();
                break;
            case R.id.tb_note_date://date
                showTimeSelector();
                break;
            case R.id.tb_note_remark://note
                showContentDialog();
                break;
            case R.id.tb_calc_num_done://commit
                doCommit();
                break;
            case R.id.tb_calc_num_1:
                calcMoney(1);
                break;
            case R.id.tb_calc_num_2:
                calcMoney(2);
                break;
            case R.id.tb_calc_num_3:
                calcMoney(3);
                break;
            case R.id.tb_calc_num_4:
                calcMoney(4);
                break;
            case R.id.tb_calc_num_5:
                calcMoney(5);
                break;
            case R.id.tb_calc_num_6:
                calcMoney(6);
                break;
            case R.id.tb_calc_num_7:
                calcMoney(7);
                break;
            case R.id.tb_calc_num_8:
                calcMoney(8);
                break;
            case R.id.tb_calc_num_9:
                calcMoney(9);
                break;
            case R.id.tb_calc_num_0:
                calcMoney(0);
                break;
            case R.id.tb_calc_num_dot:
                if (dotNum.equals(".00")) {
                    isDot = true;
                    dotNum = ".";
                }
                moneyTv.setText(num + dotNum);
                break;
            case R.id.tb_note_clear:
                doClear();
                break;
            case R.id.tb_calc_num_del:
                doDelete();
                break;
        }
    }
    /**
     * show date choicer
     */
    public void showTimeSelector() {
        Calendar ca = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mYear = i;
                mMonth = i1;
                mDay = i2;
                if (mMonth + 1 < 10) {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("-").append("0").
                                append(mMonth + 1).append("-").append("0").append(mDay).toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("-").append("0").
                                append(mMonth + 1).append("-").append(mDay).toString();
                    }

                } else {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("-").
                                append(mMonth + 1).append("-").append("0").append(mDay).toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("-").
                                append(mMonth + 1).append("-").append(mDay).toString();
                    }

                }
                dateTv.setText(days);
                Date date = new Date(System.currentTimeMillis());
                if (simpleDateFormat.format(date).equals(days)) {
                    dateTv.setText("今天");
                } else {
                    dateTv.setText(days);

                }
            }
        }, ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 显示备注内容输入框
     */
    public void showContentDialog() {
        final EditText editText = new EditText(BillAddActivity.this);

        editText.setText(remarkInput);
        //将光标移至文字末尾
        editText.setSelection(remarkInput.length());

        //弹出输入框
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("备注")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = editText.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "内容不能为空！" + input,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            remarkInput = input;
                        }
                        TextView textView= BillAddActivity.this.findViewById(R.id.beizhu);
                        textView.setText(input);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                //调用系统输入法
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    /**
     * 提交账单
     */
    public void doCommit() {
        final SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss");
        final String crDate = days + sdf.format(new Date());
        Log.e(TAG, "doCommit: "+crDate );
        if ((num + dotNum).equals("0.00")) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "正在提交...", Toast.LENGTH_SHORT).show();
        //ProgressUtils.show(mContext, "正在提交...");
        TotalBill bBill=new TotalBill(null,null, Float.valueOf(num + dotNum),remarkInput,currentUser.getObjectId(),
               "no",
                "no",
                lastBean.getSortName(),lastBean.getSortImg(),
                DateUtils.getMillis(crDate),!isOutcome,0);
        presenter.add(bBill);
       /* ShareBill coBill=new ShareBill(bBill);
        coBill.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null)
                    ToastUtils.show(mContext,s);
                else
                    ToastUtils.show(mContext,e.getMessage());
            }
        });*/
    }

    /**
     * 清空金额
     */
    public void doClear() {
        num = "0";
        count = 0;
        dotNum = ".00";
        isDot = false;
        moneyTv.setText("0.00");
    }

    /**
     * 删除上次输入
     */
    public void doDelete() {
        if (isDot) {
            if (count > 0) {
                dotNum = dotNum.substring(0, dotNum.length() - 1);
                count--;
            }
            if (count == 0) {
                isDot = false;
                dotNum = ".00";
            }
            moneyTv.setText(num + dotNum);
        } else {
            if (num.length() > 0)
                num = num.substring(0, num.length() - 1);
            if (num.length() == 0)
                num = "0";
            moneyTv.setText(num + dotNum);
        }
    }



    /**
     * 计算金额
     *
     * @param money
     */
    protected void calcMoney(int money) {
        if (num.equals("0") && money == 0)
            return;
        if (isDot) {
            if (count < DOT_NUM) {
                count++;
                dotNum += money;
                moneyTv.setText(num + dotNum);
            }
        } else if (Integer.parseInt(num) < MAX_NUM) {
            if (num.equals("0"))
                num = "";
            num += money;
            moneyTv.setText(num + dotNum);
        }
    }

    /**
     * 监听Activity返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    initSortView();
                    break;
            }
        }
    }
}
